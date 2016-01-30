package scaler.module;

import configuration.module.PluginConfiguration;
import metadata.decoder.ChannelMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scaler.module.types.IdentityImageDataScaler;
import scaler.module.types.ImageDataScaler;
import scaler.module.types.ScalingType;
import scaler.module.types.UnsupportedScalingType;

/**
 *
 * @author Drimal
 */
public class ScalerModule {

    private static Logger logger = LoggerFactory.getLogger(ScalerModule.class);

    private double multiplierValueInMeter;
    private double offsetValueInMeter;
    private double pixelXSize;
    private double pixelYSize;
    private ImageDataScaler dataScaler;

    public ScalerModule(ChannelMetadata generalMetadata,
            ChannelMetadata channelMetadata) {
        //get scaling parameters from metadata
        int multiplierTagId = PluginConfiguration.getScalingMultiplierTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        multiplierValueInMeter = (Double) channelMetadata.getTagValue(multiplierTagId);
        int offsetTagId = PluginConfiguration.getScalingOffsetTag() + PluginConfiguration.SLOT_INDEX * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        offsetValueInMeter = (Double) channelMetadata.getTagValue(offsetTagId);

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
        double physicalImageWidthInMeter = (Double) metadata.getTagValue(PluginConfiguration.getImagePhysicalWidthTag());
        double physicalImageHeightInMeter = (Double) metadata.getTagValue(PluginConfiguration.getImagePhysicalHeightTag());
        int imageWidth = (Integer) metadata.getTagValue(PluginConfiguration.getImageWidthTag());
        int imageHeight = (Integer) metadata.getTagValue(PluginConfiguration.getImageHeightTag());
        pixelXSize = physicalImageWidthInMeter / imageWidth;
        pixelYSize = physicalImageHeightInMeter / imageHeight;
    }

    private void configureImageDataScalerInstance(String scalingType,
            double multiplierInMeter,
            double offsetInMeter) {
        try {
            dataScaler = ScalerFactory.getImageDataScalerInstance(ScalingType.parse(scalingType), multiplierInMeter, offsetInMeter);
        } catch (UnsupportedScalingType ex) {
            logger.warn("UnsuppordetScalingType - Setting imageDataScaler to IdentityScaler.");
            dataScaler = new IdentityImageDataScaler();
        }
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity in nanometer unit
     @param pixelIntensityValue
     @return scaled pixel value in meter
     */
    public double scalePixelIntensityToObtainRealHeight(
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
