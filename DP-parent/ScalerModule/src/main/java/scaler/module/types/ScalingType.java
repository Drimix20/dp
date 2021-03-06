package scaler.module.types;

/**
 *
 * @author Drimal
 */
public enum ScalingType {

    IDENTITY("NullScaling"), LINEAR("LinearScaling");

    public static ScalingType parse(ScalingType scalingType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String type;

    private ScalingType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ScalingType parse(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Unsupported type " + type);
        }

        if (type.equals(IDENTITY.getType())) {
            return IDENTITY;
        } else if (type.equals(LINEAR.getType())) {
            return LINEAR;
        } else {
            throw new IllegalArgumentException("Unsupported type " + type);
        }
    }
}
