package interactive.analyzer.graph;

import interactive.analyzer.file.tools.ImageFileFilter;
import interactive.analyzer.graph.shape.Shape;
import interactive.analyzer.gui.InformativePanel;
import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.options.ObjectFilteringConfiguration;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
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

    protected enum GraphSelection {

        /**
         * Single mode selection by left mouse click
         */
        SINGLE_MODE,
        /**
         * Initiation of multiple mode selection
         */
        INIT_MULTI_MODE_SELECTION,
        /**
         * Multi mode selection by left mouse dragged click
         */
        MULTI_MODE_SELECTION,
        /**
         * Multi mode deselection by left mouse dragged click
         */
        MULTI_MODE_DESELECTION;
    }
    private volatile Graphics2D graphics2D;
    private volatile BufferedImage paintImage;
    private Chart chart;
    private Color selectionColor;
    private List<Shape> selectionByDragged;
    private List<ChartSelectionListener> selectionListeners;

    private static InformativePanel informativePanel;
    private int sumOfOccurence;
    private double minLowerBound;
    private double maxUpperBound;

    private boolean initMultipleSelection = false;

    private ImageFileFilter[] filefilters = new ImageFileFilter[]{
        new ImageFileFilter("png"),
        new ImageFileFilter("jpg"),
        new ImageFileFilter("gif")};

    public GraphPanel() {
        selectionListeners = new ArrayList<ChartSelectionListener>();
        selectionByDragged = new ArrayList<Shape>();
        reinitializeSelectionInformation();
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
                //show tooltip of shape on which is mouse located
                final MouseEvent event = e;
                if (chart == null) {
                    return;
                }
                Shape shape = getShapeAtPoint(event.getPoint());
                if (shape != null) {
                    setToolTipText(shape.getTooltipText());
                    ToolTipManager.sharedInstance().mouseMoved(event);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                final MouseEvent event = e;
                if (chart == null || chart.isEmpty()) {
                    return;
                }

                if (initMultipleSelection) {
                    selectionAction(GraphSelection.INIT_MULTI_MODE_SELECTION, event);
                }

                boolean draggedSelection = event.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK;
                boolean deselectionModeIsOn = event.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK;

                if (draggedSelection) {
                    selectionAction(GraphSelection.MULTI_MODE_SELECTION, event);
                } else if (deselectionModeIsOn) {
                    selectionAction(GraphSelection.MULTI_MODE_DESELECTION, event);
                }
                initMultipleSelection = false;
            }
        }
        );

        addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e
                    ) {
                        selectionAction(GraphSelection.SINGLE_MODE, e);
                    }

                    @Override
                    public void mousePressed(MouseEvent e
                    ) {
                        logger.info("Mouse button " + e.getButton());
                        if (chart == null || chart.isEmpty()) {
                            return;
                        }

                        if (e.getButton() == MouseEvent.BUTTON1) {

                            initMultipleSelection = true;
                        }

                    }
                }
        );
    }

    /**
     Method do selection which depends on selectionMode. Mouse event is needed to determine which shape is selected
     @param selectionMode
     @param event
     */
    public void selectionAction(GraphSelection selectionMode, MouseEvent event) {
        switch (selectionMode) {
            case SINGLE_MODE:
                singleBarSelectionAction(event);
                break;
            case INIT_MULTI_MODE_SELECTION:
                clearAllSelectionsEvent();
                break;
            case MULTI_MODE_SELECTION:
                multipleBarSelectionAction(event);
                break;
            case MULTI_MODE_DESELECTION:
                mulptipleBarDeselectionAction(event);
                break;
        }
    }

    /**
     Just single bar was selected or deselected by mouse click. Single selection at time.
     If clicked shape is selected then method deselects it.
     @param event
     */
    private synchronized void singleBarSelectionAction(final MouseEvent event) {
        if (chart == null || chart.isEmpty()) {
            return;
        }
        Shape shape = getShapeAtPoint(event.getPoint());
        if (shape == null) {
            return;
        }

        logger.trace(shape.getID() + ", " + shape.getTooltipText());
        //Select shape clicked on
        boolean select = !shape.isSelected();

        clearAllSelectionsEvent();

        shape.setSelected(select);
        shape.setSelectionColor(selectionColor);

        //needed to repaint canvas
        updatePaint();
        if (select) {
            //Show information of selected object
            showInformationAboutSelection(shape.getOccurence(), shape.getLowerBound(), shape.getUpperBound());
            //chart shape was selected
            notifySingleBarSelected(shape, selectionColor);
        } else {
            //No information to show
            clearInformationAboutSelection();
            //chart shape was deselected
            notifyBarDeselected(shape);
        }
    }

    /**
     Multiple bars were selected by mouse dragged with left button clicked.
     @param event
     */
    private synchronized void multipleBarSelectionAction(final MouseEvent event) {
        Shape shape = getShapeAtPoint(event.getPoint());
        if (shape == null) {
            return;
        }
        if (selectionByDragged.contains(shape)) {
            return;
        }
        logger.info("Selecting " + shape.getID());
        shape.setSelected(true);
        shape.setSelectionColor(selectionColor);
        selectionByDragged.add(shape);
        updatePaint();
        sumOfOccurence += shape.getOccurence();
        double sMin = shape.getLowerBound();
        double sMax = shape.getUpperBound();
        minLowerBound = (sMin < minLowerBound) ? sMin : minLowerBound;
        maxUpperBound = (sMax > maxUpperBound) ? sMax : maxUpperBound;
        showInformationAboutSelection(sumOfOccurence, minLowerBound, maxUpperBound);
        notifyBarSelected(shape, selectionColor);
    }

    private synchronized void mulptipleBarDeselectionAction(
            final MouseEvent event) {
        Shape shape = getShapeAtPoint(event.getPoint());
        if (shape == null) {
            return;
        }
        if (!selectionByDragged.contains(shape)) {
            return;
        }
        logger.info("Disabling shape: " + shape.getID());
        shape.setSelected(false);
        selectionByDragged.remove(shape);
        sumOfOccurence -= shape.getOccurence();
        updatePaint();
        Collections.sort(selectionByDragged);
        if (selectionByDragged.isEmpty()) {
            //if is selection empty then show no info
            clearInformationAboutSelection();
        } else {
            //show info about current selection
            minLowerBound = selectionByDragged.get(0).getLowerBound();
            maxUpperBound = selectionByDragged.get(selectionByDragged.size() - 1).getUpperBound();
            showInformationAboutSelection(sumOfOccurence, minLowerBound, maxUpperBound);
        }
        notifyBarDeselected(shape);
        logger.info("Deleted shape, selection size:" + selectionByDragged.size());
    }

    /**
     Reinitialize collection with dragged shape, sum of occurrences, minimal lower bound and maximal lower bound
     */
    private void reinitializeSelectionInformation() {
        logger.info("Reinit of dragged selections");
        sumOfOccurence = 0;
        minLowerBound = Integer.MAX_VALUE;
        maxUpperBound = Integer.MIN_VALUE;
    }

    public void setInformativePanel(InformativePanel panel) {
        GraphPanel.informativePanel = panel;
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    public void changeSelectionColor(final Color oldC, final Color newC) {
        if (chart == null || chart.isEmpty()) {
            return;
        }
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.getSelectionColor().equals(oldC)) {
                shape.setSelectionColor(newC);
                notifyBarSelected(shape, newC);
            }
        }
    }

    //TODO make it repaint after change decimal places
    private void showInformationAboutSelection(int occurence, double lowerBound,
            double upperBound) {
        logger.info("Show info about selection");
        int decimalPlaces = ObjectFilteringConfiguration.getDecimalPlacesForInfoPanel();
        DecimalFormat df = new DecimalFormat(getDecimalFormat(decimalPlaces));
        informativePanel.setCountFieldValue(df.format(occurence));
        informativePanel.setLowerBoundField(df.format(lowerBound));
        informativePanel.setUpperBoundField(df.format(upperBound));
    }

    private String getDecimalFormat(int decimalPlaces) {
        if (decimalPlaces == 0) {
            return "#";
        }
        String format = "#.";
        for (int i = 0; i < decimalPlaces; i++) {
            format = format.concat("#");
        }
        return format;
    }

    /**
     Method delete all information about selection on Object Filtering frame.
     */
    private void clearInformationAboutSelection() {
        logger.info("Clearing information about selection");
        informativePanel.setCountFieldValue("");
        informativePanel.setLowerBoundField("");
        informativePanel.setUpperBoundField("");
    }

    public void deselectShapeWithColor(final Color color) {
        if (chart == null || chart.isEmpty()) {
            return;
        }
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.getSelectionColor().equals(color)) {
                shape.setSelected(false);
                notifyBarDeselected(shape);
            }
        }

        updatePaint();
    }

    /**
     Notify subscribers that single bar is selected. Subscribers should take care of selections.
     It means only one selection at time.
     @param shape selected shape on graph
     @param selectionColor selected color
     */
    public void notifySingleBarSelected(final Shape shape,
            final Color selectionColor) {
        new Runnable() {

            @Override
            public void run() {
                if (shape == null || selectionColor == null) {
                    throw new IllegalArgumentException("Shape or color is null");
                }
                if (chart == null || chart.isEmpty()) {
                    return;
                }
                logger.trace("Single bar selected: " + shape);
                for (ChartSelectionListener listener : selectionListeners) {
                    listener.singleBarSelectedEvent(shape.getLowerBound(), shape.getUpperBound(), selectionColor);
                }
            }
        }.run();

    }

    public void notifyBarSelected(final Shape shape, final Color selectionColor) {
        new Runnable() {
            @Override
            public void run() {
                if (shape == null || selectionColor == null) {
                    throw new IllegalArgumentException("Shape or color is null");
                }
                if (chart == null || chart.isEmpty()) {
                    return;
                }
                logger.trace("Multiple bar selected: " + shape);
                for (ChartSelectionListener listener : selectionListeners) {
                    listener.multipleBarSelectedEvent(shape.getLowerBound(), shape.getUpperBound(), selectionColor);
                }
            }
        }.run();
    }

    public void notifyBarDeselected(final Shape shape) {
        new Runnable() {

            @Override
            public void run() {
                if (shape == null) {
                    throw new IllegalArgumentException("Shape is null");
                }
                if (chart == null || chart.isEmpty()) {
                    return;
                }
                logger.trace("Bar deselected: " + shape);
                for (ChartSelectionListener listener : selectionListeners) {
                    listener.barDeselectedEvent(shape.getLowerBound(), shape.getUpperBound());
                }
            }
        }.run();
    }

    public void setBarBackgroundColor(Color color) {
        if (chart != null || chart.isEmpty()) {
            for (Shape shape : chart.getDrawShapes()) {
                shape.setBgColor(color);
            }
        }
    }

    public void setBarBorderColor(Color color) {
        if (chart != null || chart.isEmpty()) {
            for (Shape shape : chart.getDrawShapes()) {
                shape.setBorderColor(color);
            }
        }
    }

    /**
     * Set chart which will be displayed in graph panel
     * @param chart
     */
    public void setChart(Chart chart) {
        this.chart = chart;
    }

    // <editor-fold defaultstate="collapsed" desc="Manage ChartSelectionListener...">
    public boolean addChartSelectionListener(ChartSelectionListener listener) {
        return selectionListeners.add(listener);
    }

    public boolean removeChartSelectionListener(ChartSelectionListener listener) {
        return selectionListeners.remove(listener);
    }

    public void removeAllChartSelectionListeners() {
        selectionListeners.clear();
    }
    // </editor-fold>

    /**
     * Identify shape on which was clicked performed
     * @param p clicked point
     * @return shape or null if no shape
     */
    private Shape getShapeAtPoint(Point p) {
        if (chart == null || chart.isEmpty()) {
            logger.trace("No chart is painted");
            return null;
        }
        Point p2 = chart.scaleToGraphCanvasCoords(p.getX(), p.getY());
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.getOccurence() == 0) {
                continue;
            }
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
    public synchronized void updatePaint() {
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

    // <editor-fold defaultstate="collapsed" desc="TableSelectionListener...">
    @Override
    public void singleRowSelectedEvent(int roiId, double value,
            Color color) {
        logger.trace("roiId: " + roiId);
        if (chart == null) {
            return;
        }

        for (Shape shape : chart.getDrawShapes()) {
            if (shape.isValueInRange(value)) {
                shape.setSelected(true);
                shape.setSelectionColor(selectionColor);
            } else {
                shape.setSelected(false);
            }
        }
        updatePaint();
    }

    @Override
    public synchronized void multipleRowsSelectedEvent(int roiId, double value,
            Color color) {
        logger.trace("roiId: " + roiId);
        if (chart == null) {
            return;
        }
        for (Shape shape : chart.getDrawShapes()) {
            if (shape.isValueInRange(value)) {
                logger.trace("Shape selected: " + shape.getID());
                shape.setSelected(true);
                shape.setSelectionColor(selectionColor);
                break;
            }
        }
        updatePaint();
    }

    @Override
    public void rowDeselectedEvent(int roiId) {
        logger.trace("roiId: " + roiId);
        //not needed
    }

    @Override
    public void redrawAllEvent() {
        updatePaint();
    }

    /**
     Method clear all selections in graph and notify subscribers to clean all selections to.
     */
    @Override
    public void clearAllSelectionsEvent() {
        logger.trace("");
        clearSelectionsInGraph();

        for (ChartSelectionListener listener : selectionListeners) {
            listener.clearBarSelectionsEvent();
        }
    }
    // </editor-fold>

    /**
     Method clear all selections in graph, remove selections created by mouse dragged,
     clear informations and repaint graph
     */
    private void clearSelectionsInGraph() {
        logger.info("clear selections");
        if (chart != null) {
            chart.clearAllSelections();
        }
        selectionByDragged.clear();
        clearInformationAboutSelection();
        updatePaint();
    }

}
