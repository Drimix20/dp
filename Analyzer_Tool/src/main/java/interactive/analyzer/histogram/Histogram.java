package interactive.analyzer.histogram;

import interactive.analyzer.graph.data.HistogramPair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class Histogram {

    private static double binSize;
    private static int numberBins;
    private static double histMin;

    public static double getBinSize() {
        return binSize;
    }

    public static int getNumberBins() {
        return numberBins;
    }

    public static int[] calculateHistogram(Object[] data,
            double min, double max,
            int numBins) {
        validate(data, numBins);
        numberBins = numBins;
        histMin = min;

        final int[] result = new int[numBins];
        double tmpBinSize = (max - min) / numBins;
        binSize = tmpBinSize < 1 ? 1 : tmpBinSize;

        for (Object d : data) {
            int bin = (int) (((double) d - min) / binSize);
            double val = min + bin * binSize;
            if (isBinInRange(bin, 0, numBins - 1)) {
                result[bin] += 1;
            }
        }

        return result;
    }

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

    public static List<HistogramPair> createHistogramPairsFromHistogram(
            int[] hist) {
        if (hist == null) {
            throw new IllegalArgumentException("Null data");
        }
        if (hist.length == 0) {
            throw new IllegalArgumentException("Empty data");
        }
        List<HistogramPair> pairs = new ArrayList<>();
        for (int i = 0; i < hist.length; i++) {
            double val = histMin + i * binSize;
            HistogramPair p = new HistogramPair(i + 1, val, hist[i]);
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
            double val = Math.round(histMin + i * binSize);
//            System.out.println(i + " (" + val + ") ->" + data[i]);
            System.out.println("dataSet.addPair(new HistogramPair(" + (i + 1) + "," + val + "," + data[i] + "));");
        }
    }

}
