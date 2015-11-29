package interactive.analyzer.graph;

import interactive.analyzer.graph.data.DataSet;
import interactive.analyzer.graph.data.Pair;
import interactive.analyzer.graph.shape.Bar;
import interactive.analyzer.graph.shape.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class BarChart implements Chart {

    private Logger logger = Logger.getLogger(BarChart.class);
    private static final int BOTTOM_OFFSET_UP = 10;
    private static final int TEXT_MARGIN = 3;
    private static final int GRAPH_MARGIN = 3;

    private String columnName = "";
    private int margin = 35;
    private int chartHeight;
    private int chartWidth;
    private int barWidth;

    private List<Shape> shapes;
    private DataSet data;

    public BarChart() {
        shapes = new ArrayList<>();
    }

    @Override
    public void loadData(DataSet dataSet) {
        this.data = dataSet;
        prepareShapes(!shapes.isEmpty());
    }

    @Override
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public DataSet getData() {
        return data;
    }

    /**
     * Prepare shapes representing bars in graph. If sorting was performed after
     * selection then selection will be visible after sorting
     * @param selectPreviousSelected
     */
    private void prepareShapes(boolean selectPreviousSelected) {
        if (data == null || this.data.getPairs().isEmpty()) {
            return;
        }
        List<Shape> oldShape = Collections.unmodifiableList(shapes);
        shapes = new ArrayList<>();

        List<Pair> pairs = this.data.getPairs();
        int dataSize = pairs.size();
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / dataSize;
//        logger.trace("barWidth=" + barWidth);
        for (int i = 0; i < dataSize; i++) {
            Pair p = pairs.get(i);
            double value = p.getValue();
            logger.trace(i + ", " + value);
            Bar bar = new Bar(p.getID(), value, p.getCount());
            bar.setTooltipText(value + "");

            if (selectPreviousSelected) {
                //reselecting selected shapes before sorting
                for (Shape shape : oldShape) {
                    if (bar.getID() == shape.getID()) {
                        bar.setSelected(shape.isSelected());
                    }
                }
            }

            shapes.add(bar);
        }

    }

    @Override
    public List<Shape> getDrawShapes() {
        return shapes;
    }

    @Override
    public void draw(Graphics2D g, Dimension area) {
        logger.debug("width " + area.width + ", height " + area.height);
        FontMetrics metrics = g.getFontMetrics();

        margin = 2 * metrics.getHeight() + 3 * TEXT_MARGIN;
        chartWidth = area.width - 2 * margin;
        chartHeight = area.height - 2 * margin;
        logger.trace("chart margin=" + margin + ", chart width=" + chartWidth + ", chart height=" + chartHeight);

        int countOfBars = shapes.size();
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / countOfBars;

        g.setColor(Color.BLACK);

        //translate to begining of x axe
        g.translate(margin, area.height - margin);

        // draw axes
        g.drawLine(0, 0, 0, -chartHeight);//y axe
        g.drawLine(0, 0, chartWidth, 0);//x axe

        //draw x axis enumeration
        int enumerationMargin = chartWidth / countOfBars;
        for (int i = 0; i < countOfBars; i++) {
            int count = shapes.get(i).getCount();
            int xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * i - metrics.stringWidth(count + "") / 2;
            int yPos = metrics.getHeight() + TEXT_MARGIN;
            if (i % 2 == 0) {
                g.drawString(count + "", xPos, yPos);
                g.drawLine(xPos + metrics.stringWidth(count + "") / 2, 0, xPos + metrics.stringWidth(count + "") / 2, TEXT_MARGIN);
            } else {
                g.drawString(count + "", xPos, yPos + metrics.getHeight());
                g.drawLine(xPos + metrics.stringWidth(count + "") / 2, 0, xPos + metrics.stringWidth(count + "") / 2, yPos - TEXT_MARGIN);
            }
        }

        //draw x "Counts" axe description
//        g.drawString("Counts", chartWidth / 2 - metrics.stringWidth("Values") / 2, 2 * metrics.getHeight() + 2 * TEXT_MARGIN);
        g.drawString("Counts", -37, 22);

        //draw y "Column date" axe description
//        drawStringRotated(g, (double) -2 * metrics.getHeight() + TEXT_MARGIN, (double) -chartHeight / 2 + metrics.stringWidth(columnName) / 2, -90, columnName);
        g.drawString(columnName, -37, -area.height + margin + metrics.getHeight());

        int numberOfYAxisLabels = 3;
        //draw y axis enumeration
        int yAxisEnumerationSize = numberOfYAxisLabels - 1;
        List<String> yLabels = Arrays.asList("" + data.getMinValue(), "" + data.getMedian(), "" + data.getMaxValue());
        enumerationMargin = (chartHeight - 2 * GRAPH_MARGIN) / yAxisEnumerationSize;
//        logger.trace("enumerationMargin=" + enumerationMargin);
        for (int i = 0; i < numberOfYAxisLabels; i++) {
            int xPos = -TEXT_MARGIN;
            int yPos = -(GRAPH_MARGIN + enumerationMargin * i);//-chartHeight + enumerationMargin * (numberOfYAxisLabels - i - 1) + (metrics.getHeight() / 2);
//            logger.trace("position: " + xPos + ", " + (yPos + (metrics.getHeight() / 2)) + " for " + (i + 1) + " label");
            if (i == 0) {
                yPos -= BOTTOM_OFFSET_UP;
            }

            g.drawString(yLabels.get(i) + "", xPos - metrics.stringWidth(yLabels.get(i)), (yPos + (metrics.getHeight() / 4)));
            g.drawLine(xPos, yPos, xPos + 8, yPos);
        }

        drawShapes(g);
    }

    private double linearStretch(double toDimensionMax, double toDimensionMin,
            double currentDimensionMax, double currentDimensionMin, double val) {

        return (val - currentDimensionMin) * ((toDimensionMax - toDimensionMin) / (currentDimensionMax - currentDimensionMin)) + toDimensionMin;
    }

    private void drawShapes(Graphics2D g) {
        int shapeSize = shapes.size();
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / shapeSize;

        List<Pair> pairs = this.data.getPairs();
        for (int i = 0; i < shapeSize; i++) {
            double value = pairs.get(i).getValue();
            double barHeight = 0;

            if (value <= data.getMedian()) {
                //draw data less or equal to median in down half graph
                barHeight = linearStretch((chartHeight / 2) - 2 * GRAPH_MARGIN, BOTTOM_OFFSET_UP, data.getMedian(), data.getMinValue(), value);
            } else {
                //draw data greater then median in upper half graph
                barHeight = linearStretch(chartHeight - 2 * GRAPH_MARGIN, (chartHeight / 2) - 2 * GRAPH_MARGIN, data.getMaxValue(), data.getMinValue(), value);
            }
            shapes.get(i).setLocationAndSize(0 + GRAPH_MARGIN + barWidth * i, -barHeight - GRAPH_MARGIN, barWidth, barHeight);
            shapes.get(i).draw(g);
        }
    }

    private static void drawStringRotated(Graphics2D g2d, double x, double y,
            int angle,
            String text) {
        g2d.translate((float) x, (float) y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text, 0, 0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float) x, -(float) y);
    }

    @Override
    public Point scaleToGraphCanvasCoords(double x, double y) {
        return scaleToGraphCanvasCoords((int) x, (int) y);
    }

    @Override
    public Point scaleToGraphCanvasCoords(int x, int y) {
        return new Point(x - margin - GRAPH_MARGIN, y - margin - GRAPH_MARGIN - chartHeight);
    }

    @Override
    public void clearAllSelections() {
        for (Shape shape : shapes) {
            shape.setSelected(false);
        }
    }

}
