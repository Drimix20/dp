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
    private ImageDataScaler dataScaler;

    public ScalerModule(ChannelMetadata generalMetadata,
            ChannelMetadata channelMetadata) {
        //retrieve slot index based
        String defaultSlotValue = (String) channelMetadata.getTagValue(PluginConfiguration.getDefaultSlotTag());
        int slotIndex = getSlotIndexBasedOnDefaultSlotValue(channelMetadata, defaultSlotValue);

        //get scaling parameters from metadata
        int multiplierTagId = PluginConfiguration.getScalingMultiplierTag() + slotIndex * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        multiplierValueInMeter = (Double) channelMetadata.getTagValue(multiplierTagId);
        int offsetTagId = PluginConfiguration.getScalingOffsetTag() + slotIndex * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        offsetValueInMeter = (Double) channelMetadata.getTagValue(offsetTagId);

        int scalingMethodTagId = PluginConfiguration.getScalingTypeTag() + slotIndex * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
        String scalingTypeValue = (String) channelMetadata.getTagValue(scalingMethodTagId);
        configureImageDataScalerInstance(scalingTypeValue.trim(), multiplierValueInMeter, offsetValueInMeter);
    }

    public ScalerModule(ChannelMetadata metadata, long multiplierInMeter,
            long offsetInMeter, String scalingType) throws UnsupportedScalingType {
        multiplierValueInMeter = multiplierInMeter;
        offsetValueInMeter = offsetInMeter;
        configureImageDataScalerInstance(scalingType, multiplierInMeter, offsetInMeter);
    }

    /**
     Retrieve index of SlotName tag which contains same value as
     @param channelMetadata structure which contains metadata for channel image
     @param defaultSlotValue value saved in DefaultSlot tag
     @return slot index
     */
    private int getSlotIndexBasedOnDefaultSlotValue(
            ChannelMetadata channelMetadata, String defaultSlotValue) {
        Integer tagValue = (Integer) channelMetadata.getTagValue(PluginConfiguration.getNumberOfSlotsTag());
        int slotNameTag = PluginConfiguration.getSlotNameTag();
        for (int slotIndex = 0; slotIndex < tagValue; slotIndex++) {
            int tag = slotNameTag + slotIndex * PluginConfiguration.TAG_OFFSET_DECIMAL_VALUE;
            String slotNameVal = (String) channelMetadata.getTagValue(tag);
            if (defaultSlotValue.trim().equals(slotNameVal.trim())) {
                return slotIndex;
            }
        }

        throw new IllegalArgumentException("Missing SlotName with DefaultSlot " + defaultSlotValue + " value.");
    }

    private void configureImageDataScalerInstance(String scalingType,
            double multiplierInMeter,
            double offsetInMeter) {

        ScalingType type = ScalingType.parse(scalingType);

        try {
            dataScaler = ScalerFactory.getImageDataScalerInstance(type, multiplierInMeter, offsetInMeter);
        } catch (UnsupportedScalingType ex) {
            logger.error("Configuring dataScaler to IdentityScaler", ex);
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
        return dataScaler.scaleValue(pixelIntensityValue) * unit.getMultiplier();
    }

    /**
     Scale pixel intensity value to obtain real physical value of pixel intensity in specified unit
     @param pixelIntensityValue
     @param unit
     @return scaled pixel value in specified unit
     */
    public double scalePixelIntensityToObtainRealHeight(
            double pixelIntensityValue, LengthUnit unit) {
        return dataScaler.scaleValue(pixelIntensityValue) * unit.getMultiplier();
    }
}
