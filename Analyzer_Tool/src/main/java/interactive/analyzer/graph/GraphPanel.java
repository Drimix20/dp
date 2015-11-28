package interactive.analyzer.graph;

import interactive.analyzer.graph.shape.Shape;
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
import javax.swing.filechooser.FileFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class GraphPanel extends JPanel {

    private static Logger logger = Logger.getLogger(GraphPanel.class);
    private Graphics2D graphics2D;
    private BufferedImage paintImage;
    private boolean mousePressed = false;
    private Chart chart;

    private ImageFileFilter[] filefilters = new ImageFileFilter[]{
        new ImageFileFilter("png"),
        new ImageFileFilter("jpg"),
        new ImageFileFilter("gif"),};

    public GraphPanel() {
        paintImage = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                logger.trace("");
                updatePaint();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                logger.trace("");
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
                boolean deselectionModeIsOn = false;
                int onMask = MouseEvent.ALT_DOWN_MASK | MouseEvent.BUTTON1_DOWN_MASK;
                if (e.getModifiersEx() == onMask) {
                    deselectionModeIsOn = true;
                }

                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    if (mousePressed) {
                        shape.setSelected(!deselectionModeIsOn);
                        //needed to repaint canvas
                        updatePaint();
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

                Shape shape = getShapeAtPoint(e.getPoint());
                if (shape != null) {
                    logger.trace(shape.getID() + ", " + shape.getTooltipText());
                    shape.setSelected(!shape.isSelected());
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
            }
        });
    }

    /**
     * Set chart which will be displayed in graph panel
     * @param chart
     */
    public void setChart(Chart chart) {
        this.chart = chart;
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
            if (shape.contains(p2.getX(), p2.getY())) {
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

    public static class ImageFileFilter extends FileFilter {

        private String extension;

        private ImageFileFilter(String extension) {
            this.extension = extension;
        }

        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith("." + extension);
        }

        public String getExtension() {
            return extension;
        }

        @Override
        public String getDescription() {
            return extension.toUpperCase() + " file";
        }
    }

}
