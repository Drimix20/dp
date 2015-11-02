package afm.analyzer.measurements;

import afm.analyzer.scalings.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.util.Objects;

/**
 *
 * @author Drimal
 */
public abstract class AbstractMeasurement {

    protected String label;
    protected String description;

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public abstract double compute(Roi roi, ImagePlus origImage,
            ImageProcessor binary, ScalerModule scalerModule);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.label);
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
        return this.label.equals(other.getLabel());
    }
}
