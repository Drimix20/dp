package afm.opener.calibration;

import afm.opener.selector.ChannelContainer;
import ij.ImagePlus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Drimal
 */
public class SizeCalibrator {

    private static Logger logger = LoggerFactory.getLogger(SizeCalibrator.class);

    public double calibrateImageWidth(ChannelContainer container,
            LengthUnit unit) {
        logger.trace("Calibrate image width to {} unit", unit.getAbbreviation());
        double uLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(32834);

        ImagePlus img = container.getImagePlus();
        return calibrateImageWidth(img.getWidth(), uLengthInMeter, unit);
    }

    public double calibrateImageWidth(int imageWidth, double uLengthInMeter,
            LengthUnit unit) {
        double convertedImageWidth = uLengthInMeter / imageWidth * unit.getValue();

        logger.trace("Calibrated image width {} in m to {} {}", imageWidth, convertedImageWidth, unit.getAbbreviation());
        return convertedImageWidth;
    }

    public double calibrateImageHeight(ChannelContainer container,
            LengthUnit unit) {
        double vLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(32835);

        ImagePlus img = container.getImagePlus();
        return calibrateImageHeight(img.getHeight(), vLengthInMeter, unit);
    }

    public double calibrateImageHeight(int imageHeight, double vLengthInMeter,
            LengthUnit unit) {
        double convertedImageWidth = vLengthInMeter / imageHeight * unit.getValue();

        logger.trace("Calibrated image height {} in m to {} {}", imageHeight, convertedImageWidth, unit.getAbbreviation());
        return convertedImageWidth;
    }
}