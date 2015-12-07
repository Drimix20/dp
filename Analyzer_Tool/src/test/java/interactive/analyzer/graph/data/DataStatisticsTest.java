package interactive.analyzer.graph.data;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class DataStatisticsTest {

    private Object[] testData = new Object[]{10.0, 5.0, 15.0, 10.0, 5.0, 5.0, 10.0,
        15.0, 30.0, 45.0, 50.0, 5.0, 5.0, 10.0, 10.0, 50.0, 50.0, 5.0, 10.0};

    @Test
    public void testComputeDataSetFromTable() {
        HistogramDataSet dataset = DataStatistics.computeDataSetFromTable(testData);
        dataset.sortHistogramPairs();
        assertEquals((double) 5, dataset.getMinValue(), 0);
        assertEquals((double) 50, dataset.getMaxValue(), 0);
        List<HistogramBin> pairs = dataset.getHistogramPairs();
//        assertEquals(6, pairs.size());
//
//        assertEquals(5.0, pairs.get(0).getValue(), 0);
//        assertEquals(6, pairs.get(0).getOccurence());
//
//        assertEquals(10.0, pairs.get(1).getValue(), 0);
//        assertEquals(6, pairs.get(1).getOccurence());
//
//        assertEquals(15.0, pairs.get(2).getValue(), 0);
//        assertEquals(2, pairs.get(2).getOccurence());
//
//        assertEquals(30.0, pairs.get(3).getValue(), 0);
//        assertEquals(1, pairs.get(3).getOccurence());
//
//        assertEquals(45.0, pairs.get(4).getValue(), 0);
//        assertEquals(1, pairs.get(4).getOccurence());
//
//        assertEquals(50.0, pairs.get(5).getValue(), 0);
//        assertEquals(3, pairs.get(5).getOccurence());
    }

}
