package org.jfree.chart.demo;

import java.awt.*;

import org.apache.log4j.Logger;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a bar chart.
 */
public class BarChart extends ApplicationFrame {

    private static Logger logger = Logger.getLogger(BarChart.class);
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChart(String title) {

        super(title);
        CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                logger.info(chartMouseEvent.getEntity().getToolTipText());
                ChartEntity entity = chartMouseEvent.getEntity();
                //drawItem(java.awt.Graphics2D graphics2D, org.jfree.chart.renderer.category.CategoryItemRendererState categoryItemRendererState, java.awt.geom.Rectangle2D rectangle2D, org.jfree.chart.plot.CategoryPlot categoryPlot, org.jfree.chart.axis.CategoryAxis categoryAxis, org.jfree.chart.axis.ValueAxis valueAxis, org.jfree.data.category.CategoryDataset categoryDataset, int i, int i1, int i2);
//                chart.getCategoryPlot().getRenderer().drawItem((Graphics2D)chartPanel.getGraphics(),);
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {

            }
        });
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String series1 = "Profit";
        dataset.addValue(583.0, series1, "1");
        dataset.addValue(479.0, series1, "2");
        dataset.addValue(339.0, series1, "3");
        dataset.addValue(444.0, series1, "4");
        dataset.addValue(281.0, series1, "5");
        dataset.addValue(23.0, series1, "6");
        dataset.addValue(243.0, series1, "7");
        dataset.addValue(70.0, series1, "8");
        dataset.addValue(34.0, series1, "9");
        dataset.addValue(486.0, series1, "10");
        dataset.addValue(331.0, series1, "11");
        dataset.addValue(109.0, series1, "12");
        dataset.addValue(227.0, series1, "13");
        dataset.addValue(379.0, series1, "14");
        dataset.addValue(437.0, series1, "15");
        dataset.addValue(107.0, series1, "16");
        dataset.addValue(551.0, series1, "17");
        dataset.addValue(72.0, series1, "18");
        dataset.addValue(487.0, series1, "19");
        dataset.addValue(140.0, series1, "20");
        dataset.addValue(74.0, series1, "21");
        dataset.addValue(753.0, series1, "22");
        dataset.addValue(275.0, series1, "23");
        dataset.addValue(552.0, series1, "24");
        dataset.addValue(859.0, series1, "25");
        dataset.addValue(2.0, series1, "26");
        dataset.addValue(469.0, series1, "27");
        dataset.addValue(149.0, series1, "28");
        dataset.addValue(9.0, series1, "29");
        dataset.addValue(438.0, series1, "30");
        dataset.addValue(777.0, series1, "31");
        dataset.addValue(117.0, series1, "32");
        dataset.addValue(259.0, series1, "33");
        dataset.addValue(80.0, series1, "34");
        dataset.addValue(223.0, series1, "35");
        dataset.addValue(759.0, series1, "36");
        dataset.addValue(18.0, series1, "37");
        dataset.addValue(1283.0, series1, "38");
        dataset.addValue(927.0, series1, "39");
        dataset.addValue(709.0, series1, "40");
        dataset.addValue(336.0, series1, "41");
        dataset.addValue(160.0, series1, "42");
        dataset.addValue(1209.0, series1, "43");
        dataset.addValue(439.0, series1, "44");
        dataset.addValue(17.0, series1, "45");
        dataset.addValue(195.0, series1, "46");
        dataset.addValue(8.0, series1, "47");
        dataset.addValue(460.0, series1, "48");
        dataset.addValue(353.0, series1, "49");
        dataset.addValue(1418.0, series1, "50");
        dataset.addValue(1005.0, series1, "51");
        dataset.addValue(90.0, series1, "52");
        dataset.addValue(390.0, series1, "53");
        dataset.addValue(1453.0, series1, "54");
        dataset.addValue(308.0, series1, "55");
        dataset.addValue(102.0, series1, "56");
        dataset.addValue(245.0, series1, "57");
        dataset.addValue(2013.0, series1, "58");
        dataset.addValue(51.0, series1, "59");
        dataset.addValue(805.0, series1, "60");
        dataset.addValue(416.0, series1, "61");
        dataset.addValue(1781.0, series1, "62");
        dataset.addValue(158.0, series1, "63");
        dataset.addValue(375.0, series1, "64");
        dataset.addValue(1016.0, series1, "65");
        dataset.addValue(229.0, series1, "66");
        dataset.addValue(383.0, series1, "67");
        dataset.addValue(729.0, series1, "68");
        dataset.addValue(204.0, series1, "69");
        dataset.addValue(54.0, series1, "70");
        dataset.addValue(3.0, series1, "71");
        dataset.addValue(719.0, series1, "72");
        dataset.addValue(57.0, series1, "73");
        dataset.addValue(610.0, series1, "74");
        dataset.addValue(147.0, series1, "75");
        dataset.addValue(1.0, series1, "76");
        dataset.addValue(56.0, series1, "77");
        dataset.addValue(769.0, series1, "78");
        dataset.addValue(751.0, series1, "79");
        dataset.addValue(186.0, series1, "80");
        dataset.addValue(6.0, series1, "81");
        dataset.addValue(26.0, series1, "82");
        dataset.addValue(1042.0, series1, "83");
        dataset.addValue(1616.0, series1, "84");
        dataset.addValue(93.0, series1, "85");
        dataset.addValue(25.0, series1, "86");
        dataset.addValue(919.0, series1, "87");
        dataset.addValue(505.0, series1, "88");
        dataset.addValue(12.0, series1, "89");
        dataset.addValue(2102.0, series1, "90");
        dataset.addValue(124.0, series1, "91");
        dataset.addValue(942.0, series1, "92");
        dataset.addValue(95.0, series1, "93");
        dataset.addValue(464.0, series1, "94");
        dataset.addValue(170.0, series1, "95");
        dataset.addValue(456.0, series1, "96");
        dataset.addValue(765.0, series1, "97");
        dataset.addValue(174.0, series1, "98");
        dataset.addValue(91.0, series1, "99");

        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart Demo", // chart title
                "Category", // domain axis label
                "Value", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                false, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64)
        );
        renderer.setSeriesPaint(0, gp0);
        //disablovani stinu
        renderer.setShadowVisible(false);
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        BarChart demo = new BarChart("Bar Chart Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
