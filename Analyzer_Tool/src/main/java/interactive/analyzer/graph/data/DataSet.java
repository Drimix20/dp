package interactive.analyzer.graph.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class DataSet {

    private double minValue = -1;
    private double maxValue = -1;
    private double average = -1;
    private double median = -1;
    private List<Pair> pairs;

    public DataSet() {
        pairs = new ArrayList<>();
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

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public List<Pair> getPairs() {
//        TODO no sorting of results
//        Collections.sort(pairs, new PairComparator());
        return pairs;
    }

    public void clearPairs() {
        pairs.clear();
    }

    public void addPair(Pair pair) {
        pairs.add(pair);
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    public void sortPairsData() {
        Collections.sort(pairs, new PairComparator());
    }

    public void sortPairsData(Comparator comparator) {
        Collections.sort(pairs, comparator);
    }

}
