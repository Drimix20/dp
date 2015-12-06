package interactive.analyzer.graph.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class DataStatistics {

    public static HistogramDataSet computeDataSetFromTable(Object[] columnData) {
        if (columnData == null) {
            throw new IllegalArgumentException("Column data are null");
        }
        if (columnData.length == 0) {
            throw new IllegalArgumentException("Column data ase empty");
        }

//        Map<Double, Integer> hist = new HashMap<>();
        HistogramDataSet dataSet = new HistogramDataSet();

        double minValue = Integer.MAX_VALUE;
        double maxValue = Integer.MIN_VALUE;
        double sum = 0;
        for (Object cd : columnData) {
            double value = (double) cd;
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
//            Integer mapVal = hist.get(value);
//            if (mapVal == null) {
//                hist.put(value, 1);
//            } else {
//                mapVal++;
//                hist.put(value, mapVal);
//            }
            sum += value;
        }

//        int id = 1;
//        for (Map.Entry<Double, Integer> entrySet : hist.entrySet()) {
//            Double key = entrySet.getKey();
//            Integer value = entrySet.getValue();
//            HistogramPair pair = new HistogramPair(id, key, value);
//            dataSet.addPair(pair);
//
//            id++;
//        }
        dataSet.setMinValue(minValue);
        dataSet.setMaxValue(maxValue);
        dataSet.setMedianValue(StatisticsTool.computeMedian(columnData));
        dataSet.setMeanValue(sum / columnData.length);

        return dataSet;
    }

//    public static HistogramDataSet computeHistogramDataSet(
//            List<HistogramPair> histPairs) {
//        if (histPairs == null) {
//            throw new IllegalArgumentException("Histogram pairs are null");
//        }
//        if (histPairs.isEmpty()) {
//            throw new IllegalArgumentException("Histogram pairs are empty");
//        }
//        HistogramDataSet dataSet = new HistogramDataSet();
//        dataSet.setPairs(new ArrayList<>(histPairs));
//
//        int minOccurence = Integer.MAX_VALUE;
//        int maxOccurence = Integer.MIN_VALUE;
//        double sumOccurence = Integer.MIN_VALUE;
//
//        double minValue = Integer.MAX_VALUE;
//        double maxValue = Integer.MIN_VALUE;
//        double sumValue = 0;
//        Object[] occurenceValues = new Object[histPairs.size()];
//        Object[] values = new Object[histPairs.size()];
//        int i = 0;
//        for (HistogramPair p : histPairs) {
//            //compute statistics for values;
//            double value = p.getValue();
//            values[i] = value;
//            if (value < minValue) {
//                minValue = value;
//            }
//            if (value > maxValue) {
//                maxValue = value;
//            }
//            sumValue += value;
//
//            //compute statistics for occurence
//            int occurence = p.getOccurence();
//            occurenceValues[i] = occurence;
//            if (occurence < minOccurence) {
//                minOccurence = occurence;
//            }
//            if (occurence > maxOccurence) {
//                maxOccurence = occurence;
//            }
//            sumOccurence += occurence;
//
//            i++;
//        }
//
//        dataSet.setMinValue(minValue);
//        dataSet.setMaxValue(maxValue);
//        dataSet.setMeanValue(sumValue / histPairs.size());
//        dataSet.setMedianValue(computeMedian(values));
//
//        dataSet.setMaxOccurence(maxOccurence);
//        dataSet.setMinOccurence(minOccurence);
//        dataSet.setMeanOccurence(sumOccurence / histPairs.size());
//        dataSet.setMedianValue(computeMedian(occurenceValues));
//
//        return dataSet;
//    }
    public static HistogramPair createNewInstanceOfHistPair(HistogramPair pair) {
        return new HistogramPair(pair.getID(), pair.getValue(), pair.getOccurence());
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

        List<HistogramPair> newPairs = new ArrayList<>();
        for (HistogramPair pair : data.getHistogramPairs()) {
            newPairs.add(createNewInstanceOfHistPair(pair));
        }
        newInstance.setPairs(newPairs);

        return newInstance;
    }

    //TODO delete this method
    public static void PairsToString(List<HistogramPair> pairs) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (HistogramPair pair : pairs) {
            sb.append("dataset.addValue(")
                    .append(pair.getOccurence())
                    .append(", series1,\"").append(i).append("\");");
            i++;
        }
        System.out.println(sb.toString());
    }

}
