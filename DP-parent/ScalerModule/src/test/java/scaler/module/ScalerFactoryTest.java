package scaler.module;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import scaler.module.types.IdentityImageDataScaler;
import scaler.module.types.ImageDataScaler;
import scaler.module.types.LinearImageDataScaler;
import scaler.module.types.ScalingType;

/**
 *
 * @author Drimal
 */
public class ScalerFactoryTest {

    public ScalerFactoryTest() {
    }

    @Test
    public void testGetIdentityInstance() throws Exception {
        double multiplier = 1.0;
        double offset = 3.0;
        ImageDataScaler scaler = new IdentityImageDataScaler();
        ImageDataScaler result = ScalerFactory.getImageDataScalerInstance(ScalingType.IDENTITY, multiplier, offset);
        assertEquals(scaler, result);
    }

    @Test
    public void testGetLinearInstance() throws Exception {
        double multiplier = 1.0;
        double offset = 3.0;
        ImageDataScaler scaler = new LinearImageDataScaler(offset, multiplier);
        ImageDataScaler result = ScalerFactory.getImageDataScalerInstance(ScalingType.LINEAR, multiplier, offset);
        assertEquals(scaler, result);
    }
}
