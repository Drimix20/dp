package scaler.module.types;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class ScalingTypeTest {

    @Test
    public void testValueOfLinearScaling() {
        ScalingType result = ScalingType.valueOf("LINEAR");
        assertEquals(ScalingType.LINEAR, result);
    }

    @Test
    public void testValueOfNullScaling() {
        ScalingType result = ScalingType.valueOf("IDENTITY");
        assertEquals(ScalingType.IDENTITY, result);
    }

    @Test
    public void testParseLinearScaling() {
        ScalingType result = ScalingType.parse("LinearScaling");
        assertEquals(ScalingType.LINEAR, result);
    }

    @Test
    public void testParseNullScaling() {
        ScalingType result = ScalingType.parse("NullScaling");
        assertEquals(ScalingType.IDENTITY, result);
    }

}
