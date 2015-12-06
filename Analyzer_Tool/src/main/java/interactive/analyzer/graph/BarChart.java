package interactive.analyzer.graph;

import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.data.HistogramPair;
import interactive.analyzer.graph.shape.Bar;
import interactive.analyzer.graph.shape.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class BarChart implements Chart {

    private Logger logger = Logger.getLogger(BarChart.class);
    private static final int TEXT_MARGIN = 3;
    private static final int GRAPH_MARGIN = 3;

    private String columnName = "";
    private int margin = 35;
    private int chartHeight;
    private int chartWidth;
    private int barWidth;

    private List<Shape> shapes;
    private HistogramDataSet data;

    public BarChart() {
        shapes = new ArrayList<>();
    }

    @Override
    public void loadData(HistogramDataSet dataSet) {
        validateDataSet(dataSet);
        this.data = dataSet;
        prepareShapes(!shapes.isEmpty());
    }

    private void validateDataSet(HistogramDataSet dataSet) {// <editor-fold defaultstate="collapsed" desc="body">
        //validate values need to draw y-axe
        if (dataSet.getMinOccurence() == -1) {
            throw new IllegalArgumentException("Minimal occurence value must be set");
        }
        if (dataSet.getMaxOccurence() == -1) {
            throw new IllegalArgumentException("Maximal occurence value must be set");
        }
//        if (dataSet.getMeanOccurence() == -1) {
//            throw new IllegalArgumentException("Mean occurence value must be set");
//        }
        if (dataSet.getMinOccurence() < 0) {
            throw new IllegalArgumentException("Minimal occurence value can't be negative");
        }
        if (dataSet.getMaxOccurence() < 0) {
            throw new IllegalArgumentException("Maximal occurence value can't be negative");
        }
//        if (dataSet.getMeanOccurence() < 0) {
//            throw new IllegalArgumentException("Mean occurence value can't be negative");
//        }
    }// </editor-fold>

    @Override
    public void loadData(Object[] columnData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public HistogramDataSet getData() {
        return data;
    }

    /**
     * Prepare shapes representing bars in graph. If sorting was performed after
     * selection then selection will be visible after sorting
     * @param selectPreviousSelected
     */
    private void prepareShapes(boolean selectPreviousSelected) {
        if (data == null || this.data.getHistogramPairs().isEmpty()) {
            logger.debug("No data for prepare shapes");
            return;
        }
        List<Shape> oldShape = Collections.unmodifiableList(shapes);
        shapes = new ArrayList<>();

        List<HistogramPair> pairs = this.data.getHistogramPairs();
        int dataSize = pairs.size();
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / dataSize;
//        logger.trace("barWidth=" + barWidth);
        for (int i = 0; i < dataSize; i++) {
            HistogramPair p = pairs.get(i);
            double value = p.getValue();
            logger.trace(value + ": " + p.getOccurence());
            Bar bar = new Bar(p.getID(), value, p.getOccurence());

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

        g.setColor(Color.BLACK);

        //draw title "<column> column"
        g.drawString(columnName + " column", area.width / 2 - metrics.stringWidth(columnName + " column") / 2, metrics.getHeight() + TEXT_MARGIN);

        //translate to begining of x axe
        g.translate(margin, area.height - margin);

        // draw axes
        g.drawLine(0, 0, 0, -chartHeight);//y axe
        g.drawLine(0, 0, chartWidth, 0);//x axe

        //draw x "Values" axe description
        g.drawString("Values", chartWidth / 2 - metrics.stringWidth("Values") / 2, 2 * metrics.getHeight() + 2 * TEXT_MARGIN);

        //draw x "Occurence" axe description
        drawStringRotated(g, (double) -2 * metrics.getHeight() + TEXT_MARGIN, (double) -chartHeight / 2 + metrics.stringWidth("Occurence") / 2, -90, "Occurence");

        int countOfBars = shapes.size();
        if (countOfBars == 0) {
            g.drawString("NO DATA", chartWidth / 2 - metrics.stringWidth("NO DATA"), -chartHeight / 2);
            logger.trace("Count of bars is zero");
            return;
        }
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / countOfBars;

        //draw x axis enumeration
        int enumerationMargin = chartWidth / countOfBars;
        //draw 0-min value on x axe
        int yPos = metrics.getHeight() + TEXT_MARGIN;
        int xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * 0 - metrics.stringWidth(data.getMinValue() + "") / 2;
        g.drawString(data.getMinValue() + "", xPos, yPos);
        //draw max value on x axe
        xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * (countOfBars - 1) - metrics.stringWidth(data.getMaxValue() + "") / 2;
        g.drawString(data.getMaxValue() + "", xPos, yPos);
//        for (int i = 0; i < countOfBars; i++) {
//            double value = shapes.get(i).getValue();
//            int xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * i - metrics.stringWidth(value + "") / 2;
//            int yPos = metrics.getHeight() + TEXT_MARGIN;
//            if (i % 2 == 0) {
//                g.drawString(value + "", xPos, yPos);
//                g.drawLine(xPos + metrics.stringWidth(value + "") / 2, 0, xPos + metrics.stringWidth(value + "") / 2, TEXT_MARGIN);
//            } else {
//                g.drawString(value + "", xPos, yPos + metrics.getHeight());
//                g.drawLine(xPos + metrics.stringWidth(value + "") / 2, 0, xPos + metrics.stringWidth(value + "") / 2, yPos - TEXT_MARGIN);
//            }
//        }

        int numberOfYAxisLabels = data.getMaxOccurence() + 1;
        //draw y axis enumeration
        int yAxisEnumerationSize = numberOfYAxisLabels - 1;
        enumerationMargin = (chartHeight - 2 * GRAPH_MARGIN) / yAxisEnumerationSize;
        for (int i = 0; i < numberOfYAxisLabels; i++) {
            xPos = -TEXT_MARGIN;
            yPos = -(GRAPH_MARGIN + enumerationMargin * i);

            g.drawString(i + "", xPos - metrics.stringWidth(i + ""), (yPos + (metrics.getHeight() / 4)));
            g.drawLine(xPos, yPos, xPos + 8, yPos);
        }

        drawShapes(g);
    }

    private void drawShapes(Graphics2D g) {
        int shapeSize = shapes.size();
        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / shapeSize;

        List<HistogramPair> pairs = this.data.getHistogramPairs();
        for (int i = 0; i < shapeSize; i++) {
            double value = pairs.get(i).getOccurence();
            double barHeight = linearStretch(chartHeight - 2 * GRAPH_MARGIN, GRAPH_MARGIN, data.getMaxOccurence(), 0, value);
            shapes.get(i).setLocationAndSize(0 + GRAPH_MARGIN + barWidth * i, -barHeight - GRAPH_MARGIN, barWidth, barHeight);
            logger.trace(shapes.get(i));
            shapes.get(i).draw(g);
        }
    }

    private double linearStretch(double toDimensionMax, double toDimensionMin,
            double currentDimensionMax, double currentDimensionMin, double val) {

        return (val - currentDimensionMin) * ((toDimensionMax - toDimensionMin) / (currentDimensionMax - currentDimensionMin)) + toDimensionMin;
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
