package scaler.module;

import scaler.module.types.IdentityImageDataScaler;
import scaler.module.types.ImageDataScaler;
import scaler.module.types.LinearImageDataScaler;
import scaler.module.types.ScalingType;
import scaler.module.types.UnsupportedScalingType;
import static scaler.module.types.ScalingType.IDENTITY;
import static scaler.module.types.ScalingType.LINEAR;

/**
 *
 * @author Drimal
 */
public class ScalerFactory {

    public static ImageDataScaler getImageDataScalerInstance(
            ScalingType scalingType, double multiplier, double offset) throws UnsupportedScalingType {

        if (scalingType == IDENTITY) {
            return new IdentityImageDataScaler();
        } else if (scalingType == LINEAR) {
            return new LinearImageDataScaler(offset, multiplier);
        } else {
            throw new UnsupportedScalingType("Scaling type " + scalingType + " is not supported");
        }
    }
}
