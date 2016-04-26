package scaler.module.types;

/**
 *
 * @author Drimal
 */
public class UnitConvertor {

    /**
     * Convert value from unit to another unit
     * @param value
     * @param fromUnit
     * @param toUnit
     * @return
     */
    public static double convertValueFromUnitToUnit(double value,
            LengthUnit fromUnit, LengthUnit toUnit) {
        return value * toUnit.getMultiplier() / fromUnit.getMultiplier();
    }

    /**
     * Convert value from unit to another unit with defined exponent.
     * Converted value is raised to power of the exponent value.
     * @param value
     * @param fromUnit
     * @param toUnit
     * @param exponent
     * @return
     */
    public static double convertValueWithPowerOfExponent(double value,
            LengthUnit fromUnit,
            LengthUnit toUnit, int exponent) {
        double baseValue = toUnit.getMultiplier() / fromUnit.getMultiplier();

        if (fromUnit.equals(toUnit)) {
            return value;
        } else {
            return value * Math.pow(baseValue, exponent);
        }
    }
}
