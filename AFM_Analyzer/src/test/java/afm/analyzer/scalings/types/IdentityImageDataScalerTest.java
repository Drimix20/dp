package afm.analyzer.scalings.types;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class IdentityImageDataScalerTest {

    @Test
    public void testIdentityScaler() {
        ImageDataScaler dataScaler = new IdentityImageDataScaler();
        double scaledValue = dataScaler.scaleValue(27);
        assertEquals(27, scaledValue, 0);
    }

    @Test
    public void testIdentityScalerWithZeroValue() {
        ImageDataScaler dataScaler = new IdentityImageDataScaler();
        double scaledValue = dataScaler.scaleValue(0);
        assertEquals(0, scaledValue, 0);
    }

    @Test
    public void testIdentityScalerWithNegativeValue() {
        ImageDataScaler dataScaler = new IdentityImageDataScaler();
        double scaledValue = dataScaler.scaleValue(-10);
        assertEquals(-10, scaledValue, 0);
    }

}
