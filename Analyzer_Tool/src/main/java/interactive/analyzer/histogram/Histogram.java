package interactive.analyzer.histogram;

import interactive.analyzer.graph.data.HistogramBin;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class Histogram {

    private static Logger logger = Logger.getLogger(Histogram.class);

    private static double binSize;
    private static int binsNumber;
    private static double minValue;
    private static double maxValue;
    private static double minOccurence;
    private static double maxOccurence;

    public static double getBinSize() {
        return binSize;
    }

    public static int getBinsNumber() {
        return binsNumber;
    }

    public static double getMinValue() {
        return minValue;
    }

    public static double getMaxValue() {
        return maxValue;
    }

    public static double getMinOccurence() {
        return minOccurence;
    }

    public static double getMaxOccurence() {
        return maxOccurence;
    }

    public static int[] calculateHistogram(Object[] data,
            double min, double max, int numBins) {
        validate(data, numBins);
        binsNumber = numBins;
        minValue = min;

        final int[] result = new int[numBins];
        double tmpBinSize = (max - min) / numBins;
        binSize = tmpBinSize < 1 ? 1 : tmpBinSize;

        for (Object d : data) {
            int bin = getBinIndexBasedOnValue(d);
            if (isBinInRange(bin, 0, numBins - 1)) {
                result[bin] += 1;
            }
        }

        logger.trace("BinSize: " + binSize + ", numberBins: " + binsNumber + ", minVal: " + min + ", maxVal: " + max);
        return result;
    }

    //TODO improved version of histogram
//    public static List<HistogramBin> computeHistogram(Object[] data,
//            double min, double max, int numBins) {
//        validate(data, numBins);
//        List<HistogramBin> binList = new ArrayList<>();
//        binsNumber = numBins;
//        minValue = min;
//
//        final int[] result = new int[numBins];
//        double tmpBinSize = (max - min) / numBins;
//        binSize = tmpBinSize < 1 ? 1 : tmpBinSize;
//
//        for (Object d : data) {
//
//            int bin = (int) (((double) d - min) / binSize);
//            if (isBinInRange(bin, 0, numBins - 1)) {
//                result[bin] += 1;
//            }
//        }
//
//        //i + " <" + (i * binSize + low) + ", " + ((i + 1) * binSize + low)
//        for (int i = 0; i < binsNumber; i++) {
//            HistogramBin bin = new HistogramBin(i, (i * binSize + min), ((i + 1) * binSize + min), result[i]);
//            binList.add(bin);
//        }
//
//        logger.trace("BinSize: " + binSize + ", numberBins: " + binsNumber + ", minVal: " + min + ", maxVal: " + max);
//        return binList;
//    }
//    public static Map<Integer, HistogramBin> calculateHistogram2(Object[] data,
//            double min, double max, int numBins) {
//        validate(data, numBins);
//        binsNumber = numBins;
//        minValue = min;
//
//        Map<Integer, HistogramBin> map = new TreeMap<>();
//        double tmpBinSize = (max - min) / numBins;
//        binSize = tmpBinSize < 1 ? 1 : tmpBinSize;
//
//        for (int i = 0; i < binsNumber; i++) {
//            map.put(i, new HistogramBin(i, (i * binSize + min), ((i + 1) * binSize + min)));
//        }
//
//        for (Object d : data) {
//            int binIndex = getBinIndexBasedOnValue(d);
//            HistogramBin bin = map.get(binIndex);
//            if (bin != null && bin.isValueInRange((double) d)) {
//                bin.incrementOccurence();
//                int occurence = bin.getOccurence();
//                if (occurence < minOccurence) {
//                    minOccurence = occurence;
//                }
//                if (occurence > maxOccurence) {
//                    maxOccurence = occurence;
//                }
//            }
//        }
//
//        System.out.println("BinSize: " + binSize + ", numberBins: " + binsNumber + ", minVal: " + min + ", maxVal: " + max);
//        return map;
//    }
    public static int[] calculateCumulatedHistogram(Object[] data, double min,
            double max, int numBins) {
        validate(data, numBins);
        int[] intensHist = calculateHistogram(data, min, max, numBins);
        int[] cumulHist = new int[intensHist.length];

        int occurencePrevious = intensHist[0];
        cumulHist[0] = intensHist[0];
        for (int i = 1; i < intensHist.length; i++) {
            cumulHist[i] = occurencePrevious + intensHist[i];
            occurencePrevious = cumulHist[i];
        }

        return cumulHist;
    }

    private static int getBinIndexBasedOnValue(Object d) {
        int binIndex = (int) (((double) d - minValue) / binSize);
        binIndex = (binIndex > (binsNumber - 1)) ? (binsNumber - 1) : binIndex;
        return binIndex;
    }

    public static List<HistogramBin> createHistogramPairsFromHistogram(
            int[] hist) {
        if (hist == null) {
            throw new IllegalArgumentException("Null data");
        }
        if (hist.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        List<HistogramBin> pairs = new ArrayList<>();
        for (int i = 0; i < hist.length; i++) {
            double low = minValue + i * binSize;
            double upper = minValue + (i + 1) * binSize;
            HistogramBin p = new HistogramBin(i, (int) low, (int) upper, hist[i]);
            pairs.add(p);
        }

        return pairs;
    }

    private static boolean isBinInRange(int bin, int low, int max) {
        return bin >= low && bin <= max;
    }

    private static void validate(Object[] data, int numBins) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Null data");
        }
        if (data.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        if (numBins < 1) {
            throw new IllegalArgumentException("Number of bins must be greater then zero");
        }
    }

    public static void printHistogram(int[] data) {
        for (int i = 0; i < data.length; i++) {
            double val = Math.round(minValue + i * binSize);
//            System.out.println(i + " (" + val + ") ->" + data[i]);
            System.out.println("dataSet.addPair(new HistogramPair(" + (i + 1) + "," + val + "," + data[i] + "));");
        }
    }

}
