package interactive.analyzer.graph;

import interactive.analyzer.file.tools.ImageFileFilter;
import interactive.analyzer.graph.shape.Shape;
import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class GraphPanel extends JPanel implements TableSelectionListener {

    private static Logger logger = Logger.getLogger(GraphPanel.class);

    private Graphics2D graphics2D;
    private BufferedImage paintImage;
    private boolean mousePressed = false;
    private Chart chart;
    private boolean draggedSelection = false;
    private Color selectionColor;
    private List<Shape> selectionByDragged;
    private List<ChartSelectionListener> selectionListeners;

    private ImageFileFilter[] filefilters = new ImageFileFilter[]{
        new ImageFileFilter("png"),
        new ImageFileFilter("jpg"),
        new ImageFileFilter("gif"),};

    public GraphPanel() {
        selectionListeners = new ArrayList<>();
        selectionByDragged = new ArrayList<>();
        paintImage = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                updatePaint();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                updatePaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    setToolTipText(shape.getTooltipText());
                    ToolTipManager.sharedInstance().mouseMoved(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                logger.trace("" + e.getPoint());
                if (mousePressed) {
                    draggedSelection = true;
                }
                boolean deselectionModeIsOn = false;
                if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                    deselectionModeIsOn = true;
                }

                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    if (mousePressed) {
                        logger.trace("mousePressed: " + mousePressed);
                        boolean select = !deselectionModeIsOn;
                        shape.setSelected(select);
                        shape.setSelectionColor(selectionColor);

                        //needed to repaint canvas
                        updatePaint();
                    }
                    if (draggedSelection) {
                        selectionByDragged.add(shape);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //logger.trace("" + e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logger.trace("" + e.getPoint());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.trace("" + e.getPoint());

                logger.trace("mousePressed: " + mousePressed);
                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    logger.trace(shape.getID() + ", " + shape.getTooltipText());
                    boolean select = !shape.isSelected();
                    shape.setSelected(select);
                    shape.setSelectionColor(selectionColor);

                    for (ChartSelectionListener listener : selectionListeners) {
                        if (select) {
                            //chart shape was selected
                            listener.notifyBarSelected(shape.getLowerBound(), shape.getUpperBound(), selectionColor);
                        } else {
                            //chart shape was deselected
                            listener.notifyBarDeselected(shape.getLowerBound(), shape.getUpperBound());
                        }
                    }
                    //needed to repaint canvas
                    updatePaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                logger.trace("" + e.getPoint());
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                logger.trace("" + e.getPoint());
                mousePressed = false;
                //multiple selection by mouse dragged
                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    if (draggedSelection) {
                        boolean select = shape.isSelected();

                        for (ChartSelectionListener listener : selectionListeners) {
                            for (Shape shapeByDragged : selectionByDragged) {
                                if (select) {
                                    //chart shape was selected
                                    listener.notifyBarSelected(shapeByDragged.getLowerBound(), shapeByDragged.getUpperBound(), selectionColor);
                                } else {
                                    //chart shape was deselected
                                    listener.notifyBarDeselected(shapeByDragged.getLowerBound(), shapeByDragged.getUpperBound());
                                }
                            }
                        }
                        selectionByDragged = new ArrayList<>();
                    }
                }

                draggedSelection = false;
            }
        });
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    /**
     * Set chart which will be displayed in graph panel
     * @param chart
     */
    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public boolean addChartSelectionListener(ChartSelectionListener listener) {
        return selectionListeners.add(listener);
    }

    public boolean removeChartSelectionListener(ChartSelectionListener listener) {
        return selectionListeners.remove(listener);
    }

    public void removeAllChartSelectionListeners() {
        selectionListeners.clear();
    }

    /**
     * Identify shape on which was clicked performed
     * @param p clicked point
     * @return shape or null if no shape
     */
    private Shape getShapeAtPoint(Point p) {
        if (chart == null) {
            logger.trace("No chart is painted");
            return null;
        }
        Point p2 = chart.scaleToGraphCanvasCoords(p.getX(), p.getY());
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.cross(p2.getX())) {
                return shape;
            }
        }
        return null;
    }

    public Chart getChart() {
        return chart;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(paintImage, 0, 0, null);
    }

    /**
     * Method must be called when graph panel canvas is changed for redrawing
     */
    public void updatePaint() {
        if (paintImage.getWidth() != getWidth() || paintImage.getHeight() != getHeight()) {
            paintImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        }

        graphics2D = paintImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, getWidth(), getHeight());

        if (chart != null) {
            chart.draw(graphics2D, new Dimension(paintImage.getWidth(), paintImage.getHeight()));
        }

        // draw on paintImage using Graphics
        graphics2D.dispose();
        // repaint panel with new modified paint
        repaint();
    }

    public ImageFileFilter[] getFileFilters() {
        return filefilters;
    }

    public void save(File file, ImageFileFilter filter) throws IOException {
        if (!filter.accept(file)) {
            file = new File(file.getAbsolutePath() + "." + filter.getExtension());
        }
        ImageIO.write(paintImage, filter.getExtension(), file);
    }

    @Override
    public void selectedRowIndexIsChanged(int rowIndex, double value,
            Color color) {
        if (chart == null) {
            return;
        }
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.isValueInRange(value)) {
                shape.setSelected(true);
                shape.setSelectionColor(selectionColor);
            }
        }
        updatePaint();
    }

    @Override
    public void selectedMultipleRows(int rowIndex, double value, Color color) {
        if (chart == null) {
            return;
        }
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.isValueInRange(value)) {
                shape.setSelected(true);
                shape.setSelectionColor(selectionColor);
            }
        }
        updatePaint();
    }

    @Override
    public void deselectedRow(int rowIndex) {
        //not needed
    }

    @Override
    public void clearAllSelections() {
        logger.trace("");
        if (chart != null) {
            chart.clearAllSelections();
        }
        for (ChartSelectionListener listener : selectionListeners) {
            listener.notifyClearAllSelections();
        }
        updatePaint();
    }

}
