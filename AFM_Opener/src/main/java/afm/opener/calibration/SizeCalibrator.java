package afm.opener.calibration;

import afm.opener.selector.ChannelContainer;
import configuration.module.PluginConfiguration;
import ij.ImagePlus;
import org.apache.log4j.Logger;
import scaler.module.types.LengthUnit;

/**
 * Class represents functionality for computing size in physical units from metadata
 * @author Drimal
 */
public class SizeCalibrator {

    private static Logger logger = Logger.getLogger(SizeCalibrator.class);

    public double calibrateImageWidth(ChannelContainer container,
            LengthUnit unit) {
        logger.trace("Calibrate image width to " + unit.getAbbreviation() + " unit");
        double uLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(PluginConfiguration.getImagePhysicalWidthTag());

        ImagePlus img = container.getImagePlus();
        return calibrateImageWidth(img.getWidth(), uLengthInMeter, unit);
    }

    public double calibrateImageWidth(int imageWidth, double uLengthInMeter,
            LengthUnit unit) {

        // Convert image width to specific unit
        double convertedImageWidth = uLengthInMeter * unit.getMultiplier();

        logger.trace("Calibrated image width: origin value in metadata " + uLengthInMeter + " in m to " + convertedImageWidth + " " + unit.getAbbreviation());
        return convertedImageWidth;
    }

    public double calibrateImageHeight(ChannelContainer container,
            LengthUnit unit) {
        double vLengthInMeter = (Double) container.getGeneralMetadata().getTagValue(PluginConfiguration.getImagePhysicalHeightTag());

        ImagePlus img = container.getImagePlus();
        return calibrateImageHeight(img.getHeight(), vLengthInMeter, unit);
    }

    public double calibrateImageHeight(int imageHeight, double vLengthInMeter,
            LengthUnit unit) {

        // Convert image width to specific unit
        double convertedImageHeight = vLengthInMeter * unit.getMultiplier();

        logger.trace("Calibrated image height: origin value in metadata " + vLengthInMeter + " in m to " + convertedImageHeight + " " + unit.getAbbreviation());
        return convertedImageHeight;
    }
}
