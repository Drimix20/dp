package afm.analyzer.measurements;

import ij.ImagePlus;
import ij.gui.Roi;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class VolumeMeasurement implements AbstractMeasurement {

    private static final Logger logger = Logger.getLogger(VolumeMeasurement.class);
    private String label;

    public VolumeMeasurement(String label) {
        this.label = label;
    }

    @Override
    public void compute(Roi roi, ImagePlus origImage, ImageProcessor binary) {
        double intensitySum = 0;
        double count = 0;

        Rectangle bounds = roi.getBounds();
        ImageProcessor ip = origImage.getProcessor();
        for (int i = (int) bounds.getX(); i < (int) (bounds.getX() + bounds.getWidth()); i++) {
            for (int j = (int) bounds.getY(); j < (int) (bounds.getY() + bounds.getHeight()); j++) {
                if (binary.get(i, j) == 255) {
                    intensitySum += ip.getPixelValue(i, j);
                    count++;
                }
            }
        }
        double averageIntensity = ((double) intensitySum / count);
        double volume = count * averageIntensity;
        //logger.info(segment.getLabel() + " area: <" + count + ">, mean: <" + averageIntensity + ">, volume:<" + result + ">");
        System.out.println(count + ";" + averageIntensity + ";" + volume);
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
