package interactive.analyzer.histogram;

import interactive.analyzer.graph.data.HistogramBin;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class HistogramImproved {

    private static Logger logger = Logger.getLogger(HistogramImproved.class);

    private static double binSize;
    private static int binsNumber;
    private static double minValue;
    private static double maxValue;
    private static int minOccurence;
    private static int maxOccurence;

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

    public static int getMinOccurence() {
        return minOccurence;
    }

    public static int getMaxOccurence() {
        return maxOccurence;
    }

    public static List<HistogramBin> calculateHistogram(Object[] data,
            double min, double max, int numBins) {
        validate(data, numBins);
        List<HistogramBin> binList = new ArrayList<>();
        binsNumber = numBins;
        minValue = min;
        maxValue = max;

        double tmpBinSize = (max - min) / numBins;
        binSize = tmpBinSize < 1 ? 1 : tmpBinSize;

        for (int i = 0; i < binsNumber; i++) {
            binList.add(new HistogramBin(i, i * binSize + min, (i + 1) * binSize + min));
        }

        int row = 1;
        for (Object d : data) {
            int binIndex = getBinIndexBasedOnValue(d);
            HistogramBin bin = binList.get(binIndex);
            if (bin != null) {
                if (bin.isValueInRange((double) d)) {
                    bin.incrementOccurence();
                }
                int occurence = bin.getOccurence();
                if (occurence < minOccurence) {
                    minOccurence = occurence;
                }
                if (occurence > maxOccurence) {
                    maxOccurence = occurence;
                }
            }
            row++;
        }
        logger.trace("BinSize: " + binSize + ", numberBins: " + binsNumber
                + ", minVal: " + min + ", maxVal: " + max + ", minOccurence: "
                + minOccurence + ", maxOccurence: " + maxOccurence);
        return binList;
    }

    public static List<HistogramBin> calculateCumulatedHistogram(Object[] data,
            double min, double max, int numBins) {
        validate(data, numBins);

        List<HistogramBin> calculateHistogram = calculateHistogram(data, min, max, numBins);
        List<HistogramBin> cumulatedHist = new ArrayList<>();

        int occurencePrevious = calculateHistogram.get(0).getOccurence();
        cumulatedHist.add(calculateHistogram.get(0));
        for (int i = 1; i < calculateHistogram.size(); i++) {
            calculateHistogram.get(i).addToOccurence(occurencePrevious);
            occurencePrevious = calculateHistogram.get(i).getOccurence();
        }

        return cumulatedHist;
    }

    private static int getBinIndexBasedOnValue(Object d) {
        int binIndex = (int) (((double) d - minValue) / binSize);
        binIndex = (binIndex > (binsNumber - 1)) ? (binsNumber - 1) : binIndex;
        return binIndex;
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

}
