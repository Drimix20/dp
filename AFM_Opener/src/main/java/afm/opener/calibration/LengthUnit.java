package afm.opener.calibration;

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
        this.value = value;
        this.abbreviation = abbreviation;
    }

    private double value;
    private String abbreviation;

    public double getValue() {
        return value;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

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
        return "SizeUnit{" + "value=" + value + ", abbreviation=" + abbreviation + '}';
    }

}
