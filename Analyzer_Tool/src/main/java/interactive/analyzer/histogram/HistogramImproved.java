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

    public static void main(String[] arg) {
        Object[] data = new Object[]{179.169858815509, 174.01193377789875, 176.44665035776836, 179.05354351675564, 177.62511394443078, 0.0, 174.89862332240355, 178.09221421348806,
            168.02184865460197, 171.13688561671495, 167.22285580600993, 161.7844864210583, 167.75455704591764, 1.9655929857006056, 169.3327742290425, 170.1364656040954,
            167.27102382002306, 160.7558040130808, 154.61412259977192, 103.33549864469806, 0.0, 134.10031408418516, 136.10601041923348, 160.72636118584612, 135.64126529871191,
            158.15158443033815, 71.392350205678, 28.047456603941505, 158.94840469768744, 6.404071954532145, 161.9212500271687, 111.56763570287006, 175.72680231386536,
            172.47308853014275, 92.00450360657206, 0.0, 103.92313053416164, 0.0, 0.0, 135.4390137497368, 0.0, 165.91180080500152, 178.55264237169973, 89.83164359114033,
            139.77489195409453, 119.4033089733253, 155.48726597956102, 143.81465367251153, 173.5736702861376, 0.0, 0.29159247472855454, 0.0, 142.33184839424217, 22.122773987891392,
            99.90889801397239, 35.894645017537705, 175.96647150876893, 41.984140955333004, 86.88778131984095, 0.0, 119.40285433837872, 102.79608742913912, 179.77792778344445,
            90.11590547971515, 174.63232388595927, 0.0, 0.0, 11.64728162694716, 154.6679917500058, 157.5384740042575, 149.8976873162719, 112.89494241302438, 153.66280407822052,
            174.11095685836506, 142.76197338073354, 0.0, 90.67236219435136, 160.91898409866144, 42.87248899857924, 172.46939946000148, 159.39201227626071, 127.34238346676442,
            143.78563937281103, 98.01526737360786, 126.27966436212436, 131.78795571961416, 140.54339156291272, 140.1725648757428, 106.0215429684052, 88.69482139540396,
            177.0860053952132, 75.30338199369376, 54.72996043599864, 0.0, 170.47645241628382, 157.8166931867423, 93.24815609786384, 160.55192189718946, 134.8066265604643,
            125.1699274091222, 0.0, 167.24258632993627, 0.0, 3.170095872954948, 157.98972080695617, 61.89508576519277, 0.0, 87.08289232880337, 130.33210161279666, 139.81847352109807,
            150.7461650528985, 143.46556804571452, 117.64362296149429, 154.82612609339657, 158.4262746818911, 179.6939752433155, 0.0, 0.0};

        List<HistogramBin> calculateHistogram = calculateHistogram(data, 0, 179.7779278, 256);

        for (HistogramBin bin : calculateHistogram) {
            logger.trace(bin);
        }
    }

    public static List<HistogramBin> calculateHistogram(Object[] data,
            double min, double max, int numBins) {
        validate(data, numBins);
        maxOccurence = Integer.MIN_VALUE;
        minOccurence = Integer.MAX_VALUE;
        List<HistogramBin> binList = new ArrayList<>();
        binsNumber = numBins;
        minValue = min;
        maxValue = max + 1;//TODO Zkontrolovat, jestli je to tak v poradku - max hodnoda +1 , aby maximalni hodnota spadla do binu s indexem (numbBins - 1)

        binSize = (maxValue - minValue) / numBins;
        logger.trace("binSize: " + binSize);

        for (int i = 0; i < binsNumber; i++) {
            binList.add(new HistogramBin(i, i * binSize + minValue, (i + 1) * binSize + minValue));
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
                + ", minVal: " + minValue + ", maxVal: " + maxValue + ", minOccurence: "
                + minOccurence + ", maxOccurence: " + maxOccurence);

        for (HistogramBin bin : binList) {
            logger.trace(bin);
        }

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
