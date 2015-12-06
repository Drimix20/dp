package interactive.analyzer.graph.data;

import java.util.Arrays;

/**
 *
 * @author Drimal
 */
public class StatisticsTool {

    public static double computeMinValue(int[] data) {
        Object[] obj = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            obj[i] = data[i];
        }
        return computeMinValue(obj);
    }

    public static double computeMinValue(Object[] data) {
        validate(data);
        double min = Double.MAX_VALUE;
        for (Object e : data) {
            if (e instanceof Number) {
                Number number = (Number) e;
                if (number.doubleValue() < min) {
                    min = number.doubleValue();
                }
            }
        }
        return min;
    }

    public static double computeMaxValue(int[] data) {
        Object[] obj = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            obj[i] = data[i];
        }
        return computeMaxValue(obj);
    }

    public static double computeMaxValue(Object[] data) {
        validate(data);
        double max = Double.MIN_VALUE;
        for (Object e : data) {
            if (e instanceof Number) {
                Number number = (Number) e;
                if (number.doubleValue() > max) {
                    max = number.doubleValue();
                }
            }
        }
        return max;
    }

    public static double computeMedian(int[] data) {
        Object[] obj = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            obj[i] = data[i];
        }
        return computeMedian(obj);
    }

    public static double computeMedian(Object[] data) {
        validate(data);
        Object[] tmp = Arrays.copyOf(data, data.length);
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

    public static double computeMean(int[] data) {
        Object[] obj = new Object[data.length];

        for (int i = 0; i < data.length; i++) {
            obj[i] = data[i];
        }
        return computeMean(obj);
    }

    public static double computeMean(Object[] data) {
        validate(data);
        double sum = 0;
        for (Object e : data) {
            if (e instanceof Number) {
                sum += ((Number) e).doubleValue();
            }
        }
        return sum / data.length;
    }

    private static void validate(Object[] data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
    }

}
