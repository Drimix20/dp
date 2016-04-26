package afm.analyzer.measurements;

import scaler.module.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import scaler.module.types.LengthUnit;

/**
 *
 * @author Drimal
 */
public abstract class AbstractMeasurement {

    public static final LengthUnit RESULT_NANOMETER_UNIT = LengthUnit.NANOMETER;

    public enum MeasurementWithOption {

        YES, NO;
    }

    protected String label;
    protected int unitExponent;

    public AbstractMeasurement(String label, int unitExponent) {
        this.label = label;
        this.unitExponent = unitExponent;
    }

    /**
     Label is used for show text in list of available measurements and in results column
     @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     Regulation defined which exponent use for unit convert
     @return
     */
    public int getUnitExponent() {
        return unitExponent;
    }

    public abstract double compute(Roi roi, ImagePlus origImage,
            ImageProcessor binary, ScalerModule scalerModule,
            LengthUnit resultUnit);

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractMeasurement other = (AbstractMeasurement) obj;
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        return true;
    }

}
