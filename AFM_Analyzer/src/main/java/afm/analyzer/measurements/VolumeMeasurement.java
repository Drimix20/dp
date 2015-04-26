package afm.analyzer.measurements;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.Objects;

/**
 *
 * @author Drimal
 */
public class VolumeMeasurement implements MeasurementComputationAbstract {

    private String label;

    public VolumeMeasurement(String label) {
        this.label = label;
    }

    @Override
    public void compute(ImagePlus origImage, ImageProcessor binary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLabel() {
        return label;
    }

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
        final VolumeMeasurement other = (VolumeMeasurement) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        return true;
    }

}
