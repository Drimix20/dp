package interactive.analyzer.histogram;

import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class HistogramImprovedTest {

    @Test
    public void testCalculateHistogram() {
        //
//        Object[] data = new Object[]{75.0, 75.0, 75.0, 75.0, 82.0, 82.0, 82.0,
//            82.0, 82.0, 82.0, 85.0, 85.0, 85.0, 91.0, 91.0};
//        int[] expResult = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 6, 0,
//            0, 3, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0};
//        List<HistogramBin> calculatedHistogram = HistogramImproved.calculateHistogram(data, 0, 100, 100);
//
//        int i = 0;
//        for (HistogramBin bin : calculatedHistogram) {
//            Assert.assertEquals(bin.toString(), expResult[i], bin.getOccurence());
//            i++;
//        }
    }

    @Test
    public void testCalculateHistogram3() {
//        Object[] data = new Object[]{36.0, 25.0, 38.0, 46.0, 55.0, 68.0,
//            72.0, 55.0, 36.0, 38.0, 67.0, 45.0, 22.0, 48.0, 91.0, 46.0, 52.0,
//            61.0, 58.0, 55.0};
//        int[] expResult = new int[]{2, 4, 4, 5, 3, 1, 0, 1};
//        List<HistogramBin> calculatedHistogram = HistogramImproved.calculateHistogram(data, 20, 100, 8);
//
//        int i = 0;
//        for (HistogramBin bin : calculatedHistogram) {
//            Assert.assertEquals(bin.toString(), expResult[i], bin.getOccurence());
//            i++;
//        }
    }

    @Test
    public void testCalculateHistogram2() {
        //TODO fix test
//        Object[] data = new Object[]{2.0, 4.0, 6.0, 2.0, 7.0, 8.0, 2.0, 9.0};
//        int[] expResult = new int[]{0, 3, 0, 1, 1, 0, 1, 1, 1, 1};
//        List<HistogramBin> calculatedHistogram = HistogramImproved.calculateHistogram(data, 0, 10, 10);
//
//        int i = 0;
//        for (HistogramBin bin : calculatedHistogram) {
//            Assert.assertEquals(bin.toString(), expResult[i], bin.getOccurence());
//            i++;
//        }
    }

    @Test
    public void testCalculateCumulatedHistogram() {
//        Object[] data = new Object[]{2.0, 4.0, 6.0, 2.0, 7.0, 8.0, 2.0, 9.0};
//        int[] expResult = new int[]{0, 0, 3, 3, 4, 4, 5, 6, 7, 8};
//        List<HistogramBin> calculateCumulatedHistogram = HistogramImproved.calculateCumulatedHistogram(data, 0, 10, 10);
//
//        int i = 0;
//        for (HistogramBin bin : calculateCumulatedHistogram) {
//            Assert.assertEquals(bin.toString(), expResult[i], bin.getOccurence());
//            i++;
//        }
    }

}
