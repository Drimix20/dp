package afm.analyzer.scalings;

import afm.analyzer.scalings.types.IdentityImageDataScaler;
import afm.analyzer.scalings.types.ImageDataScaler;
import afm.analyzer.scalings.types.UnsupportedScalingType;
import configuration.PluginConfiguration;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.decoder.ChannelMetadata;

/**
 *
 * @author Drimal
 */
public class ScalerModule {

    private double multiplierValueInMeter;
    private double offsetValueInMeter;
    private ChannelMetadata currentChannelMetadata;
    private ImageDataScaler dataScaler;

    public ScalerModule(ChannelMetadata metadata) {
        currentChannelMetadata = metadata;

        //get scaling parameters from metadata
        int multiplierTagId = PluginConfiguration.getScalingMultiplierTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        multiplierValueInMeter = (double) metadata.getTagValue(multiplierTagId);
        int offsetTagId = PluginConfiguration.getScalingOffsetTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        offsetValueInMeter = (double) metadata.getTagValue(offsetTagId);

        int scalingMethodTagId = PluginConfiguration.getScalingTypeTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        String scalingTypeValue = (String) metadata.getTagValue(scalingMethodTagId);
        setImageDataScalerInstance(scalingTypeValue.trim(), multiplierValueInMeter, offsetValueInMeter);
    }

    public ScalerModule(ChannelMetadata metadata, long multiplierInMeter,
            long offsetInMeter, String scalingType) {
        this.currentChannelMetadata = metadata;
        multiplierValueInMeter = multiplierInMeter;
        offsetValueInMeter = offsetInMeter;

        setImageDataScalerInstance(scalingType, multiplierInMeter, offsetInMeter);
    }

    private void setImageDataScalerInstance(String scalingType,
            double multiplierInMeter,
            double offsetInMeter) {
        try {
            dataScaler = ScalerFactory.getImageDataScalerInstance(scalingType, multiplierInMeter, offsetInMeter);
        } catch (UnsupportedScalingType ex) {
            Logger.getLogger(ScalerModule.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(ScalerModule.class.getName()).log(Level.WARNING, "Setting imageDataScaler to IdentityScaler.");
            dataScaler = new IdentityImageDataScaler();
        }
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity
     @param pixelIntensityValue
     @return scaled pixel value in meter
     */
    public double scaleToObtainPhysicalValue(long pixelIntensityValue) {
        return dataScaler.scaleValue(pixelIntensityValue);
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity
     @param pixelIntensityValue
     @return scaled pixel value in meter
     */
    public double scaleToObtainPhysicalValue(double pixelIntensityValue) {
        return dataScaler.scaleValue(pixelIntensityValue);
    }

//    public double scaleToObtainPhysicalValue(long pixelValue, Unit.NANOMETER) {
//        return dataScaler.scaleValue(pixelValue);
//    }
}
