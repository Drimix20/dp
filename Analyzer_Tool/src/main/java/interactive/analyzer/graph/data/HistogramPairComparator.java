package interactive.analyzer.graph.data;

import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public class HistogramPairComparator implements Comparator<HistogramPair> {

    @Override
    public int compare(HistogramPair o1, HistogramPair o2) {
        return Double.compare(o1.getValue(), o2.getValue());
    }

}
