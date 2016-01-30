package scaler.module.types;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class LinearImageDataScalerTest {

    private ImageDataScaler scaler;

    @Test
    public void testScaleValuePositiveOffset() {
        scaler = new LinearImageDataScaler(3.0, 1.5);
        double scaledValue = scaler.scaleValue(3.0);
        assertEquals(7.5, scaledValue, 0.0);
    }

    @Test
    public void testScaleValueNegativeOffset() {
        scaler = new LinearImageDataScaler(-3.0, 1.5);
        double scaledValue = scaler.scaleValue(3.0);
        assertEquals(1.5, scaledValue, 0.0);
    }

}
