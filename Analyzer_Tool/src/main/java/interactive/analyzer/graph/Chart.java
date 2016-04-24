package interactive.analyzer.graph;

import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.shape.Shape;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Drimal
 */
public interface Chart {

    /**
     * Draws chart using given graphics with all its data
     * @param g graphics
     * @param area area where the chart is drawn
     */
    public void draw(Graphics2D g, Dimension area);

    /**
     * Method for loading data to draw from dataset
     * @param dataSet
     */
    public void loadData(HistogramDataSet dataSet);

    /**
     * Method for loading column data to draw as chart.
     * @param columnData
     */
    public void loadData(Object[] columnData);

    /**
     * Set column name of drawing data
     * @param columnName column name
     */
    public void setColumnName(String columnName);

    /**
     * Retrieve data from chart
     * @return data
     */
    public HistogramDataSet getData();

    /**
     * Return shapes draw on canvas
     * @return shapes
     */
    public List<Shape> getDrawShapes();

    /**
     Return if chart contains any data
     @return
     */
    public boolean isEmpty();

    /**
     * Scale graph panel clicked point to point of
     * @param x
     * @param y
     * @return scaled point
     */
    public Point scaleToGraphCanvasCoords(int x, int y);

    /**
     * Scale graph panel clicked point to point of
     * @param x
     * @param y
     * @return scaled point
     */
    public Point scaleToGraphCanvasCoords(double x, double y);

    /**
     * Method clear all shape selection in chart
     */
    public abstract void clearAllSelections();
}
