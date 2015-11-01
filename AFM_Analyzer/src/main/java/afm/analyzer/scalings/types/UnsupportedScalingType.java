package afm.analyzer.scalings.types;

/**
 *
 * @author Drimal
 */
public class UnsupportedScalingType extends Exception {

    public UnsupportedScalingType(String message) {
        super(message);
    }

    public UnsupportedScalingType(String message, Throwable cause) {
        super(message, cause);
    }
}
