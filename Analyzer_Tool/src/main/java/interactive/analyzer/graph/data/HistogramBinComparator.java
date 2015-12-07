package interactive.analyzer.graph.data;

import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public class HistogramBinComparator implements Comparator<HistogramBin> {

    @Override
    public int compare(HistogramBin o1, HistogramBin o2) {
        return Double.compare(o1.getLowerBound(), o2.getUpperBound());
    }

}
