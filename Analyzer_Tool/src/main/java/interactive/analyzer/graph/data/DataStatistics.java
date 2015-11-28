package interactive.analyzer.graph.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class DataStatistics {

    public static DataSet computeDataSetFromTable(Object[] columnData) {
        if (columnData == null) {
            throw new IllegalArgumentException("Column data are null");
        }
        if (columnData.length == 0) {
            throw new IllegalArgumentException("Column data ase empty");
        }
        Map<Double, Integer> hist = new HashMap<Double, Integer>();
        DataSet dataSet = new DataSet();

        double minValue = Integer.MAX_VALUE;
        double maxValue = Integer.MIN_VALUE;
        double sum = 0;
        for (int i = 0; i < columnData.length; i++) {
            double value = (double) columnData[i];
            if (value < minValue) {
                minValue = value;
            }
            if (value > maxValue) {
                maxValue = value;
            }
            Integer mapVal = hist.get(value);
            if (mapVal == null) {
                hist.put(value, 1);
            } else {
                mapVal++;
                hist.put(value, mapVal);
            }
            sum += value;
        }

        int id = 1;
        for (Map.Entry<Double, Integer> entrySet : hist.entrySet()) {
            Double key = entrySet.getKey();
            Integer value = entrySet.getValue();
            Pair pair = new Pair(value, key, id);
            dataSet.addPair(pair);

            id++;
        }

        dataSet.setMinValue(minValue);
        dataSet.setMaxValue(maxValue);
        dataSet.setMedian(computeMedian(columnData));
        dataSet.setAverage(sum / columnData.length);

        return dataSet;
    }

    public static double computeMedian(Object[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Column data are null");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Column data ase empty");
        }
        Object[] tmp = Arrays.copyOf(data, data.length);
        System.out.println(tmp);
        Arrays.sort(tmp);
        int middleLength = tmp.length / 2;

        if (tmp.length % 2 == 0) {
            //even length
            double numb1 = (double) tmp[(middleLength - 1)];
            double numb2 = (double) tmp[middleLength];
            return (numb1 + numb2) / 2;
        } else {
            // odd length
            return (double) tmp[(middleLength - 1)];
        }
    }

    public static DataSet createNewInstanceOfData(DataSet data) {
        DataSet newInstance = new DataSet();
        newInstance.setAverage(data.getAverage());
        newInstance.setMaxValue(data.getMaxValue());
        newInstance.setMinValue(data.getMinValue());
        newInstance.setMedian(data.getMedian());

        List<Pair> newPairs = new ArrayList<>();
        for (Pair pair : data.getPairs()) {
            newPairs.add(new Pair(pair.getCount(), pair.getValue(), pair.getID()));
        }
        newInstance.setPairs(newPairs);

        return newInstance;
    }

    //TODO delete this method
    public static void PairsToString(List<Pair> pairs) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Pair pair : pairs) {
            sb.append("dataset.addValue(")
                    .append(pair.getValue())
                    .append(", series1,\"").append(i).append("\");");
            i++;
        }
        System.out.println(sb.toString());
    }

}
