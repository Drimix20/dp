package configuration;

import configuration.parser.TagsDescriptionParser;
import configuration.parser.PluginConfigurationValidator;
import configuration.parser.TagsXmlDescriptionParser;
import configuration.xml.elements.ConfigurationXmlRootElement;
import configuration.xml.elements.TagConfiguration;
import ij.IJ;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class PluginConfiguration {

    public static final String PLUGIN_CONFIGURATION_SCHEMA_XSD_NAME = "pluginConfigurationSchema.xsd";
    public static final String PLUGIN_CONFIGURATION_XML_NAME = "afmPluginConfiguration.xml";

    private static int imagePhysicalWidthTag;
    private static int imagePhysicalHeightTag;
    private static int imageWidthTag;
    private static int imageHeightTag;
    private static List<TagConfiguration> tagConfigList;
    private static String configurationXmlSchemaPath;

    public static int getImagePhysicalWidthTag() {
        return imagePhysicalWidthTag;
    }

    public static int getImagePhysicalHeightTag() {
        return imagePhysicalHeightTag;
    }

    public static int getImageWidthTag() {
        return imageWidthTag;
    }

    public static int getImageHeightTag() {
        return imageHeightTag;
    }

    public static List<TagConfiguration> getTagConfigurationList() {
        return Collections.unmodifiableList(tagConfigList);
    }

    public static String getConfigurationXmlSchemaPath() {
        return configurationXmlSchemaPath;
    }

    static {
        defaultConfiguration();
        File configurtionFile = retrieveConfigurationFile();
        if (configurtionFile.exists()) {
            PluginConfigurationValidator descriptionValidator = new PluginConfigurationValidator();
            boolean isXmlValid = descriptionValidator.validateXml(configurtionFile);

            if (isXmlValid) {
                TagsDescriptionParser tagsParser = new TagsXmlDescriptionParser();
                ConfigurationXmlRootElement element = tagsParser.parseConfigurationFile(configurtionFile.getPath());
                imagePhysicalWidthTag = element.getDimensionTagsConfiguration().getImagePhysicalSizeXTag();
                imagePhysicalHeightTag = element.getDimensionTagsConfiguration().getImagePhysicalSizeYTag();
                imageWidthTag = element.getDimensionTagsConfiguration().getImageWidthTag();
                imageHeightTag = element.getDimensionTagsConfiguration().getImageHeightTag();
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

        ClassLoader cl = PluginConfiguration.class.getClassLoader();
        configurationXmlSchemaPath = cl.getResource(PLUGIN_CONFIGURATION_SCHEMA_XSD_NAME).getPath();
    }

    private static File retrieveConfigurationFile() {
        String directory = IJ.getDirectory("plugins");
        return new File(directory + File.separator + PLUGIN_CONFIGURATION_XML_NAME);
    }

}
