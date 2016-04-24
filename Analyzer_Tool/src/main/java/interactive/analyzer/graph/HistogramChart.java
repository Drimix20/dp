package interactive.analyzer.graph;

import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.data.HistogramBin;
import interactive.analyzer.graph.shape.Bar;
import interactive.analyzer.graph.shape.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class HistogramChart implements Chart {

    //TODO do clean up
    private Logger logger = Logger.getLogger(HistogramChart.class);
    private static final int TEXT_MARGIN = 3;
    private static final int GRAPH_MARGIN = 3;

    private String columnName = "";
    private int margin = 35;
    private int chartHeight;
    private int chartWidth;
    private double barWidth;

    private List<Shape> shapes;
    private HistogramDataSet data;

    public HistogramChart() {
        shapes = new ArrayList<Shape>();
    }

    @Override
    public void loadData(HistogramDataSet dataSet) {
        validateDataSet(dataSet);
        clearAllSelections();
        this.data = dataSet;
        prepareShapes(data);
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

    @Override
    public boolean isEmpty() {
        return shapes.isEmpty();
    }

    /**
     * Prepare shapes representing bars in graph. If sorting was performed after
     * selection then selection will be visible after sorting
     * @param selectPreviousSelected
     */
    private void prepareShapes(HistogramDataSet data) {
        shapes = new ArrayList<Shape>();
        if (data == null || data.getHistogramPairs().isEmpty()) {
            logger.debug("No data for prepare shapes");
            return;
        }

        List<HistogramBin> pairs = this.data.getHistogramPairs();
        int dataSize = pairs.size();
        for (int i = 0; i < dataSize; i++) {
            HistogramBin bin = pairs.get(i);
            logger.trace(bin.toString());
            Bar bar = new Bar(bin.getID(), bin.getLowerBound(), bin.getUpperBound(), bin.getOccurence());
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
        g.drawString(columnName + " histogram", area.width / 2 - metrics.stringWidth(columnName + " histogram") / 2, metrics.getHeight() + TEXT_MARGIN);

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
        barWidth = (double) (chartWidth - 2 * GRAPH_MARGIN) / countOfBars;

        //draw x axis enumeration
        double enumerationMargin = (double) chartWidth / countOfBars;
        //draw 0-min value on x axe
        double yPos = metrics.getHeight() + TEXT_MARGIN;
        double xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * 0 - metrics.stringWidth(data.getMinValue() + "") / 2;
        g.drawString(data.getMinValue() + "", (int) xPos, (int) yPos);
        //draw max value on x axe
        xPos = GRAPH_MARGIN + barWidth / 2 + enumerationMargin * (countOfBars - 1) - metrics.stringWidth(data.getMaxValue() + "") / 2;
//        logger.trace("barWidth: " + barWidth);
//        logger.trace("enumeration*(countsOfBars-1): " + (enumerationMargin * (countOfBars - 1)));
//        logger.trace("max-val-half-width: " + (metrics.stringWidth(data.getMaxValue() + "") / 2));
//        logger.trace("Position for x-max-val: xPos=" + xPos + ", yPos=" + yPos);
        g.drawString(data.getMaxValue() + "", (int) xPos, (int) yPos);
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
        int tupleNumber = decreaseDrawableYAxeLables(chartHeight, metrics, numberOfYAxisLabels);
        enumerationMargin = (double) (chartHeight - 2 * GRAPH_MARGIN) / (numberOfYAxisLabels - 1);
        for (int i = 0; i < (numberOfYAxisLabels - 1); i++) {
            xPos = -TEXT_MARGIN;
            yPos = -(GRAPH_MARGIN + enumerationMargin * i);

            g.drawLine((int) xPos, (int) yPos, (int) (xPos + 8), (int) yPos);
            if (isMultiple(i, tupleNumber)) {
                logger.trace("Label: " + i + "-> yPos= " + yPos);
                g.drawString(i + "", (int) (xPos - metrics.stringWidth(i + "")), (int) ((yPos + (metrics.getHeight() / 4))));
            }

        }

        xPos = -TEXT_MARGIN;
        yPos = -(GRAPH_MARGIN + enumerationMargin * (numberOfYAxisLabels - 1));
        g.drawLine((int) xPos, (int) yPos, (int) (xPos + 8), (int) yPos);
        g.drawString((numberOfYAxisLabels - 1) + "", (int) (xPos - metrics.stringWidth((numberOfYAxisLabels - 1) + "")), (int) ((yPos + (metrics.getHeight() / 4))));

        drawShapes(g);
    }

    private int decreaseDrawableYAxeLables(double chartHeight,
            FontMetrics metrics, int numberOfYAxeLabels) {
        int textHeight = metrics.getHeight() * numberOfYAxeLabels;

        return (int) Math.ceil(textHeight / chartHeight);
    }

    //Ex.: isMultiple(10, 5) is 10 % 5 equal to zero?
    private boolean isMultiple(int multiple, int numb) {
        return (multiple % numb) == 0;
    }

    private void drawShapes(Graphics2D g) {
        int shapeSize = shapes.size();
//        barWidth = (chartWidth - 2 * GRAPH_MARGIN) / shapeSize;

        List<HistogramBin> pairs = this.data.getHistogramPairs();
        for (int i = 0; i < shapeSize; i++) {
            double value = pairs.get(i).getOccurence();
            double barHeight = linearStretch((double) chartHeight - 2 * GRAPH_MARGIN, (double) GRAPH_MARGIN, (double) data.getMaxOccurence(), 0, value) - GRAPH_MARGIN;
            Shape shape = shapes.get(i);
//            if (shape.getOccurence() == 0) {
//                continue;
//            }
            shape.setLocationAndSize(0 + GRAPH_MARGIN + barWidth * i, -barHeight - GRAPH_MARGIN, barWidth, barHeight);
            logger.trace(shape.toString());
            shape.draw(g);
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
        return new Point(x - margin, y - margin - chartHeight);
    }

    @Override
    public void clearAllSelections() {
        for (Shape shape : shapes) {
            if (shape.getOccurence() == 0) {
                continue;
            }
            shape.setSelected(false);
        }
    }

}
