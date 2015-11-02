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
    private double pixelXSize;
    private double pixelYSize;
    private ImageDataScaler dataScaler;

    public ScalerModule(ChannelMetadata generalMetadata,
            ChannelMetadata channelMetadata) {
        //get scaling parameters from metadata
        int multiplierTagId = PluginConfiguration.getScalingMultiplierTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        multiplierValueInMeter = (double) channelMetadata.getTagValue(multiplierTagId);
        int offsetTagId = PluginConfiguration.getScalingOffsetTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        offsetValueInMeter = (double) channelMetadata.getTagValue(offsetTagId);

        int scalingMethodTagId = PluginConfiguration.getScalingTypeTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        String scalingTypeValue = (String) channelMetadata.getTagValue(scalingMethodTagId);
        configurePixelSize(generalMetadata);
        configureImageDataScalerInstance(scalingTypeValue.trim(), multiplierValueInMeter, offsetValueInMeter);
    }

    public ScalerModule(ChannelMetadata metadata, long multiplierInMeter,
            long offsetInMeter, String scalingType) {
        multiplierValueInMeter = multiplierInMeter;
        offsetValueInMeter = offsetInMeter;

        configurePixelSize(metadata);
        configureImageDataScalerInstance(scalingType, multiplierInMeter, offsetInMeter);
    }

    private void configurePixelSize(ChannelMetadata metadata) {
        double physicalImageWidthInMeter = (double) metadata.getTagValue(PluginConfiguration.getImagePhysicalWidthTag());
        double physicalImageHeightInMeter = (double) metadata.getTagValue(PluginConfiguration.getImagePhysicalHeightTag());
        int imageWidth = (int) metadata.getTagValue(PluginConfiguration.getImageWidthTag());
        int imageHeight = (int) metadata.getTagValue(PluginConfiguration.getImageHeightTag());
        pixelXSize = physicalImageWidthInMeter / imageWidth;
        pixelYSize = physicalImageHeightInMeter / imageHeight;
    }

    private void configureImageDataScalerInstance(String scalingType,
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
     Scale pixel intensity value to obtain real physical value of pixel intensity in nanometer unit
     @param pixelIntensityValue
     @return scaled pixel value in meter
     */
    public double scalePixelIntensityToObtainReailHeight(
            long pixelIntensityValue) {
        return dataScaler.scaleValue(pixelIntensityValue);
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity
     @param pixelIntensityValue
     @return scaled pixel value in meter
     */
    public double scalePixelIntensityToObtainRealHeight(
            double pixelIntensityValue) {
        return dataScaler.scaleValue(pixelIntensityValue);
    }

    public double getPixelXSizeInMeter() {
        return pixelXSize;
    }

    public double getPixelYSizeInMeter() {
        return pixelYSize;
    }

    //TODO implement method with unit converter as argument
//    public double scalePixelIntensityToObtainRealHeight(long pixelValue, Unit.NANOMETER) {
//        return dataScaler.scaleValue(pixelValue);
//    }
}
