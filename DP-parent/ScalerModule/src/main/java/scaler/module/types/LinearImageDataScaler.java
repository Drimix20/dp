package scaler.module.types;

/**
 *
 * @author Drimal
 */
public class LinearImageDataScaler extends ImageDataScaler {

    public LinearImageDataScaler(double offset, double multiplier) {
        super.offset = offset;
        super.multiplier = multiplier;
    }

}
