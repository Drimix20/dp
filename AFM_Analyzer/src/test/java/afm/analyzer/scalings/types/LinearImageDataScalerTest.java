package afm.analyzer.scalings.types;

import scaler.module.types.ImageDataScaler;
import scaler.module.types.LinearImageDataScaler;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class LinearImageDataScalerTest {

    @Test
    public void testLinearScale() {
        ImageDataScaler dataScaler = new LinearImageDataScaler(10, 2);
        double scaledValue = dataScaler.scaleValue(13);
        assertEquals(36, scaledValue, 0);
    }

    @Test
    public void testLinearScaleWithZeroValue() {
        ImageDataScaler dataScaler = new LinearImageDataScaler(10, 2);
        double scaledValue = dataScaler.scaleValue(0);
        assertEquals(10, scaledValue, 0);
    }

    @Test
    public void testLinearScaleWithNegativeValue() {
        ImageDataScaler dataScaler = new LinearImageDataScaler(10, 2);
        double scaledValue = dataScaler.scaleValue(-13);
        assertEquals(-16, scaledValue, 0);
    }

}
