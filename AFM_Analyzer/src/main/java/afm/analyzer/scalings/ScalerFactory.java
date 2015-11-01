package afm.analyzer.scalings;

import afm.analyzer.scalings.types.IdentityImageDataScaler;
import afm.analyzer.scalings.types.ImageDataScaler;
import afm.analyzer.scalings.types.LinearImageDataScaler;
import afm.analyzer.scalings.types.UnsupportedScalingType;

/**
 *
 * @author Drimal
 */
public class ScalerFactory {

    public static ImageDataScaler getImageDataScalerInstance(String scalingType,
            double multiplier, double offset) throws UnsupportedScalingType {
        switch (scalingType) {
            case "NullScaling":
                return new IdentityImageDataScaler();
            case "LinearScaling":
                return new LinearImageDataScaler(offset, multiplier);
            default:
                throw new UnsupportedScalingType("Scaling type " + scalingType + " is not supported");
        }
    }
}
