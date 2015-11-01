package afm.analyzer.utils;

import afm.analyzer.measurements.list.AreaMeasurement;
import afm.analyzer.measurements.list.VolumeMeasurement;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class ClassFinderTest {

    @Test
    public void testFindMeasurements() {
        List<Class<?>> result = ClassFinder.find("afm.analyzer.measurements.list");
        assertEquals(Arrays.asList(AreaMeasurement.class, VolumeMeasurement.class), result);
    }

}
