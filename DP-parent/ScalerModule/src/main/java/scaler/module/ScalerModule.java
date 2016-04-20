package scaler.module;

import configuration.module.PluginConfiguration;
import metadata.decoder.ChannelMetadata;
import org.apache.log4j.Logger;
import scaler.module.types.IdentityImageDataScaler;
import scaler.module.types.ImageDataScaler;
import scaler.module.types.LengthUnit;
import scaler.module.types.ScalingType;
import scaler.module.types.UnsupportedScalingType;

/**
 *
 * @author Drimal
 */
public class ScalerModule {

    private static Logger logger = Logger.getLogger(ScalerModule.class);

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
     Scale pixel intensity value to obtain real physical value of pixel intensity in specified unit
     @param pixelIntensityValue
     @param unit
     @return scaled and converted value to specified unit
     */
    public double scalePixelIntensityToObtainRealHeight(long pixelIntensityValue,
            LengthUnit unit) {
        return dataScaler.scaleValue(pixelIntensityValue) * unit.getValue();
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity in specified unit
     @param pixelIntensityValue
     @param unit
     @return scaled pixel value in specified unit
     */
    public double scalePixelIntensityToObtainRealHeight(
            double pixelIntensityValue, LengthUnit unit) {
        return dataScaler.scaleValue(pixelIntensityValue) * unit.getValue();
    }

    @Deprecated
    public double getPixelXSizeInMeter() {
        return pixelXSize;
    }

    @Deprecated
    public double getPixelYSizeInMeter() {
        return pixelYSize;
    }
}
