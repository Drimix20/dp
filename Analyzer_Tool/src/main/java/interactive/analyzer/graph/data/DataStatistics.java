package interactive.analyzer.graph.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class DataStatistics {

    /**
     Compute statistics from data. Computed statistics are min, max, median and mean values.
     @param columnData
     @return
     */
    public static HistogramDataSet computeDataSetFromTable(Object[] columnData) {
        if (columnData == null) {
            throw new IllegalArgumentException("Column data are null");
        }
        if (columnData.length == 0) {
            throw new IllegalArgumentException("Column data ase empty");
        }

        HistogramDataSet dataSet = new HistogramDataSet();

        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;
        double sum = 0;
        for (Object cd : columnData) {
            double value = (Double) cd;
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
            sum += value;
        }

        dataSet.setMinValue(minValue);
        dataSet.setMaxValue(maxValue);
        dataSet.setMedianValue(StatisticsTool.computeMedian(columnData));
        dataSet.setMeanValue(sum / columnData.length);

        return dataSet;
    }

    public static HistogramBin createNewInstanceOfHistPair(HistogramBin pair) {
        return new HistogramBin(pair.getID(), pair.getLowerBound(), pair.getLowerBound(), pair.getOccurence());
    }

    public static HistogramDataSet createNewInstanceOfData(HistogramDataSet data) {
        HistogramDataSet newInstance = new HistogramDataSet();
        newInstance.setMeanValue(data.getMeanValue());
        newInstance.setMedianValue(data.getMedianValue());
        newInstance.setMaxValue(data.getMaxValue());
        newInstance.setMinValue(data.getMinValue());

        newInstance.setMeanOccurence(data.getMeanOccurence());
        newInstance.setMedianOccurence(data.getMedianOccurence());
        newInstance.setMaxOccurence(data.getMaxOccurence());
        newInstance.setMinOccurence(data.getMinOccurence());

        List<HistogramBin> newPairs = new ArrayList<HistogramBin>();
        for (HistogramBin pair : data.getHistogramPairs()) {
            newPairs.add(createNewInstanceOfHistPair(pair));
        }
        newInstance.setPairs(newPairs);

        return newInstance;
    }

    //TODO delete this method
    public static void PairsToString(List<HistogramBin> pairs) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (HistogramBin pair : pairs) {
//            sb.append("dataset.addValue(")
//                    .append(pair.getOccurence())
//                    .append(", series1,\"").append(i).append("\");");
            sb.append(pair.getOccurence()).append(' ');
            i++;
        }
        System.out.println(sb.toString());
    }

    public static void printData(Object[] data) {
        String msg = "";
        for (int i = 0; i < data.length; i++) {
            msg += data[i] + ",";
        }
        System.out.println(msg);
    }

}
