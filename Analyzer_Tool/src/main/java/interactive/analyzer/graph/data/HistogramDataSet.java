package interactive.analyzer.graph.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class HistogramDataSet {

    private List<HistogramBin> histogramPairs;
    private int numberOfBins;
    private double binSize;
    //data used to draw y-axe
    private int minOccurence = -1;
    private int maxOccurence = -1;
    private double medianOccurence = -1;
    private double meanOccurence = -1;

    //data uset do draw x-axe
    private double minValue = -1;
    private double maxValue = -1;
    private double medianValue = -1;
    private double meanValue = -1;

    public HistogramDataSet() {
        histogramPairs = new ArrayList<HistogramBin>();
    }

    public void setNumberOfBins(int numberOfBins) {
        this.numberOfBins = numberOfBins;
    }

    public int getNumberOfBins() {
        return numberOfBins;
    }

    public void setBinSize(double binSize) {
        this.binSize = binSize;
    }

    public double getBinSize() {
        return binSize;
    }

    public int getMinOccurence() {
        return minOccurence;
    }

    public void setMinOccurence(int minOccurence) {
        this.minOccurence = minOccurence;
    }

    public int getMaxOccurence() {
        return maxOccurence;
    }

    public void setMaxOccurence(int maxOccurence) {
        this.maxOccurence = maxOccurence;
    }

    public double getMedianOccurence() {
        return medianOccurence;
    }

    public void setMedianOccurence(double medianOccurence) {
        this.medianOccurence = medianOccurence;
    }

    public double getMeanOccurence() {
        return meanOccurence;
    }

    public void setMeanOccurence(double meanOccurence) {
        this.meanOccurence = meanOccurence;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMedianValue() {
        return medianValue;
    }

    public void setMedianValue(double medianValue) {
        this.medianValue = medianValue;
    }

    public double getMeanValue() {
        return meanValue;
    }

    public void setMeanValue(double meanValue) {
        this.meanValue = meanValue;
    }

    public List<HistogramBin> getHistogramPairs() {
        return histogramPairs;
    }

    public void clearPairs() {
        histogramPairs.clear();
    }

    public void addPair(HistogramBin pair) {
        histogramPairs.add(pair);
    }

    public void setPairs(List<HistogramBin> pairs) {
        this.histogramPairs = pairs;
    }

    public void sortHistogramPairs() {
        Collections.sort(histogramPairs, new HistogramBinComparator());
    }

    public void sortPairsData(Comparator comparator) {
        Collections.sort(histogramPairs, comparator);
    }

}
