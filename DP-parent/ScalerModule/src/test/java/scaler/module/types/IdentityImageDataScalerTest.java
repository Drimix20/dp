package scaler.module.types;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class IdentityImageDataScalerTest {

    private ImageDataScaler scaler;

    @Test
    public void testIdentityScaler() {
        scaler = new IdentityImageDataScaler();
        double scaledValue = scaler.scaleValue(2.0);
        assertEquals(2.0, scaledValue, 0.0);
    }

}
