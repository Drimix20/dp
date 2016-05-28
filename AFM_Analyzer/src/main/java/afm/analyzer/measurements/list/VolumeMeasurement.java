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
public class VolumeMeasurement extends AbstractMeasurement {

    private static Logger logger = Logger.getLogger(VolumeMeasurement.class);

    public VolumeMeasurement() {
        super("Volume measure", 3);
    }

    @Override
    public double compute(Roi roi, ImagePlus origImage, ImageProcessor binary,
            Double backgroundHeightInUnit, ScalerModule scalerModule,
            LengthUnit resultUnit) {
        double proteinHeightSum = 0;
        double backgroundHeightSum = 0;
        double count = 0;

        Rectangle bounds = roi.getBounds();
        ImageProcessor ip = origImage.getProcessor();
        for (int i = (int) bounds.getX(); i < (int) (bounds.getX() + bounds.getWidth()); i++) {
            for (int j = (int) bounds.getY(); j < (int) (bounds.getY() + bounds.getHeight()); j++) {
                if (binary.get(i, j) == 255) {
                    float heightValue = ip.getPixelValue(i, j);
                    proteinHeightSum += heightValue;

                    if (backgroundHeightInUnit != null) {
                        if (heightValue <= backgroundHeightInUnit) {
                            backgroundHeightSum += heightValue;
                        } else {
                            backgroundHeightSum += backgroundHeightInUnit;
                        }
                    }

                    count++;
                }
            }
        }

        Calibration calibration = origImage.getCalibration();
        LengthUnit heightUnit = LengthUnit.parseFromAbbreviation(calibration.getValueUnit().trim());
        LengthUnit dimensionUnit = LengthUnit.parseFromAbbreviation(calibration.getUnit().trim());

        double averageProteinHeight = proteinHeightSum / count;
        double convertedAverageHeight = UnitConvertor.convertValueFromUnitToUnit(averageProteinHeight, heightUnit, resultUnit);

        double averageBackgroundHeight = backgroundHeightSum / count;
        double convertedAverageBackgroundHeight = UnitConvertor.convertValueFromUnitToUnit(averageBackgroundHeight, heightUnit, resultUnit);

        double area = count * calibration.pixelWidth * calibration.pixelHeight;
        double convertedArea = UnitConvertor.convertValueWithPowerOfExponent(area, dimensionUnit, resultUnit, 2);

        double volumeInUnit = convertedArea * convertedAverageHeight - (convertedAverageBackgroundHeight * convertedArea);
        logger.trace("RoiName=" + roi.getName() + ", unit = " + resultUnit.getAbbreviation() + "saceldAverageInensity: " + convertedAverageHeight + ", area: " + convertedArea + ", volume: " + volumeInUnit);
        return volumeInUnit;
    }

}
