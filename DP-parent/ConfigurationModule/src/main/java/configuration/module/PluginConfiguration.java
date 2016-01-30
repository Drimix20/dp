package configuration.module;

import configuration.module.parser.PluginConfigurationParser;
import configuration.module.parser.PluginConfigurationValidator;
import configuration.module.parser.XmlPluginConfigurationParser;
import configuration.module.xml.elements.ConfigurationXmlRootElement;
import configuration.module.xml.elements.TagConfiguration;
import ij.IJ;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class PluginConfiguration {

    public static final String PLUGIN_CONFIGURATION_SCHEMA_XSD_NAME = "pluginConfigurationSchema.xsd";
    public static final String PLUGIN_CONFIGURATION_XML_NAME = "afmPluginConfiguration.xml";
    public static final int TAG_OFFSET_DECIMAL_VALUE = 48;
    //TODO just temporary solution; should be substitute by automatic logic
    public static final int SLOT_INDEX = 3;

    //image resolution
    private static int imagePhysicalWidthTag;
    private static int imagePhysicalHeightTag;
    private static int imageWidthTag;
    private static int imageHeightTag;
    private static int numberOfSlotsTag;
    private static int channelNameTag;
    private static String pluginConfigurationXmlPath;
    private static List<TagConfiguration> tagConfigList;
    private static String configurationXmlSchemaPath;

    //parameters for scalings
    private static int scalingTypeTag;
    private static int scalingMultiplierTag;
    private static int scalingOffsetTag;

    /**
     Retrieve tag id for Grid-uLength. Default value is 32834.
     @return tag id
     */
    public static int getImagePhysicalWidthTag() {
        return imagePhysicalWidthTag;
    }

    /**
     Retrieve tag id for Grid-vLength. Default value is 32835.
     @return tag id
     */
    public static int getImagePhysicalHeightTag() {
        return imagePhysicalHeightTag;
    }

    public static int getImageWidthTag() {
        return imageWidthTag;
    }

    public static int getImageHeightTag() {
        return imageHeightTag;
    }

    public static int getNumberOfSlotsTag() {
        return numberOfSlotsTag;
    }

    public static int getChannelNameTag() {
        return channelNameTag;
    }

    public static List<TagConfiguration> getTagConfigurationList() {
        return tagConfigList;
    }

    public static String getPluginConfigurationXmlPath() {
        return pluginConfigurationXmlPath;
    }

    public static String getConfigurationXmlSchemaPath() {
        return configurationXmlSchemaPath;
    }

    public static int getScalingTypeTag() {
        return scalingTypeTag;
    }

    public static int getScalingMultiplierTag() {
        return scalingMultiplierTag;
    }

    public static int getScalingOffsetTag() {
        return scalingOffsetTag;
    }

    static {
        defaultConfiguration();
        File configurtionFile = retrieveConfigurationFile();
        if (configurtionFile.exists()) {
            PluginConfigurationValidator descriptionValidator = new PluginConfigurationValidator();
            boolean isXmlValid = descriptionValidator.validateXml(configurtionFile);

            if (isXmlValid) {
                PluginConfigurationParser pluginConfiguration = new XmlPluginConfigurationParser();
                ConfigurationXmlRootElement element = pluginConfiguration.parseConfigurationFile(configurtionFile.getPath());
                imagePhysicalWidthTag = element.getDimensionTagsConfiguration().getImagePhysicalSizeXTag();
                imagePhysicalHeightTag = element.getDimensionTagsConfiguration().getImagePhysicalSizeYTag();
                imageWidthTag = element.getDimensionTagsConfiguration().getImageWidthTag();
                imageHeightTag = element.getDimensionTagsConfiguration().getImageHeightTag();
                numberOfSlotsTag = element.getNumberOfSlotsTag();
                channelNameTag = element.getChannelNameTag();
                tagConfigList = element.getTagsList().getTags();
            }
        }
    }

    private static void defaultConfiguration() {
        imagePhysicalWidthTag = 32834;
        imagePhysicalHeightTag = 32835;
        imageWidthTag = 32838;
        imageHeightTag = 32839;
        tagConfigList = new ArrayList<>();
        numberOfSlotsTag = 32896;
        channelNameTag = 32848;
        scalingTypeTag = 32931;
        scalingMultiplierTag = 32932;
        scalingOffsetTag = 32933;

        ClassLoader cl = PluginConfiguration.class.getClassLoader();
        configurationXmlSchemaPath = cl.getResource(PLUGIN_CONFIGURATION_SCHEMA_XSD_NAME).getPath();
        pluginConfigurationXmlPath = IJ.getDirectory("plugins") + File.separator + PluginConfiguration.PLUGIN_CONFIGURATION_XML_NAME;
    }

    private static File retrieveConfigurationFile() {
        String directory = IJ.getDirectory("plugins");
        return new File(directory + File.separator + PLUGIN_CONFIGURATION_XML_NAME);
    }

}
