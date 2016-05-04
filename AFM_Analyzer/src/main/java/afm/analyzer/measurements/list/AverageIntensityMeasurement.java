package afm.analyzer.measurements.list;

import afm.analyzer.measurements.AbstractMeasurement;
import scaler.module.ScalerModule;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import org.apache.log4j.Logger;
import scaler.module.types.LengthUnit;
import scaler.module.types.UnitConvertor;

/**
 *
 * @author Drimal
 */
public class AverageIntensityMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(AverageIntensityMeasurement.class);

    public AverageIntensityMeasurement() {
        super("Average height measurement", 1);
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            ScalerModule scalerModule, LengthUnit resultUnit) {
        double intensitySum = 0;
        int count = 0;

        Calibration calibration = origImage.getCalibration();
        Rectangle bounds = roi.getBounds();
        ImageProcessor ip = origImage.getProcessor();
        for (int i = (int) bounds.getX(); i < (int) (bounds.getX() + bounds.getWidth()); i++) {
            for (int j = (int) bounds.getY(); j < (int) (bounds.getY() + bounds.getHeight()); j++) {
                if (binary.get(i, j) == 255) {
                    //Method getPixelValue return calibrated value if calibration table is used
                    intensitySum += ip.getPixelValue(i, j);
                    count++;
                }
            }
        }

        LengthUnit heightUnit = LengthUnit.parseFromAbbreviation(calibration.getValueUnit().trim());

        double averageIntensity = intensitySum / count;
        double convertedAverageIntensity = UnitConvertor.convertValueFromUnitToUnit(averageIntensity, heightUnit, resultUnit);
        return convertedAverageIntensity;
    }
}
