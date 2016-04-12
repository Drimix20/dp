package afm.analyzer.measurements;

import scaler.module.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public abstract class AbstractMeasurement {

    public enum MeasurementWithOption {

        YES, NO;
    }

    protected String label;
    protected String unitRegulation;

    public AbstractMeasurement(String label, String unitRegulation) {
        this.label = label;
        this.unitRegulation = unitRegulation;
    }

    /**
     Label is used for show text in list of available measurements and in results column
     @return label
     */
    public String getLabel() {
        return label;
    }

    public String getUnitRegulation() {
        return unitRegulation;
    }

    public abstract double compute(Roi roi, ImagePlus origImage,
            ImageProcessor binary, ScalerModule scalerModule);

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
