package scaler.module.types;

/**
 *
 * @author Drimal
 */
public enum LengthUnit {

    METER(1, "m"),
    DECIMETER(Math.pow(10, 1), "dm"),
    CENTIMETER(Math.pow(10, 2), "cm"),
    MILIMETER(Math.pow(10, 3), "mm"),
    MICROMETER(Math.pow(10, 6), "\u00B5m"),
    NANOMETER(Math.pow(10, 9), "nm");

    private LengthUnit(double value, String abbreviation) {
        this.multiplier = value;
        this.abbreviation = abbreviation;
    }

    private double multiplier;
    private String abbreviation;

    /**
     Get multiplier needed to convert from meter to this unit
     @return multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     Parse enum object from its abbreviation
     @param abbreviation
     @return object of enum LengthUnit
     */
    public static LengthUnit parse(String abbreviation) {
        if (abbreviation.equals(METER.getAbbreviation())) {
            return METER;
        } else if (abbreviation.equals(DECIMETER.getAbbreviation())) {
            return DECIMETER;
        } else if (abbreviation.equals(CENTIMETER.getAbbreviation())) {
            return CENTIMETER;
        } else if (abbreviation.equals(MILIMETER.getAbbreviation())) {
            return MILIMETER;
        } else if (abbreviation.equals(MICROMETER.getAbbreviation())) {
            return MICROMETER;
        } else if (abbreviation.equals(NANOMETER.getAbbreviation())) {
            return NANOMETER;
        } else {
            throw new IllegalArgumentException("Unsuppored abbreviation " + abbreviation);
        }
    }

    /**
     Get array of all abbreviations
     @return abbreviations
     */
    public static String[] retrieveAbbreviations() {
        LengthUnit[] values = LengthUnit.values();
        String[] abbreviations = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            abbreviations[i] = values[i].getAbbreviation();
        }

        return abbreviations;
    }

    @Override
    public String toString() {
        return "SizeUnit{" + "value=" + multiplier + ", abbreviation=" + abbreviation + '}';
    }

}
