package configuration.module.parser;

import configuration.module.PluginConfiguration;
import configuration.module.xml.elements.ConfigurationXmlRootElement;
import configuration.module.xml.elements.DimensionTagsConfiguration;
import ij.IJ;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for parse plugin configuratin from xml file
 * @author Drimal
 */
public class TagsXmlDescriptionParserTest {

    private static String filePath;

    @BeforeClass
    public static void startUp() {
        String directory = IJ.getDirectory("plugins");
        if (directory == null) {
            directory = "c:\\Users\\Drimal\\Skola\\dp\\AFM_Opener\\plugins";
        }
        filePath = directory + File.separator + PluginConfiguration.PLUGIN_CONFIGURATION_XML_NAME;
    }

    @Test
    public void testParseTagsDescriptions() {
        XmlPluginConfigurationParser instance = new XmlPluginConfigurationParser();

        ConfigurationXmlRootElement element = instance.parseConfigurationFile(filePath);
        DimensionTagsConfiguration dimensionTagsConfiguration = element.getDimensionTagsConfiguration();
        assertEquals(32834, dimensionTagsConfiguration.getImagePhysicalSizeXTag());
        assertEquals(32835, dimensionTagsConfiguration.getImagePhysicalSizeYTag());
        assertEquals(32838, dimensionTagsConfiguration.getImageWidthTag());
        assertEquals(32839, dimensionTagsConfiguration.getImageHeightTag());
    }

}
