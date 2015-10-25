package configuration;

import configuration.parser.PluginConfigurationValidator;
import ij.IJ;
import java.io.File;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test for configuration xml validator
 *
 * @author Drimal
 */
public class PluginConfigurationValidatorTest {

    @Test
    public void testValidateXmlBySchema() {
        String directory = IJ.getDirectory("plugins");
        if (directory == null) {
            directory = "c:\\Users\\Drimal\\Skola\\dp\\AFM_Opener\\plugins";
        }
        File tagsDescriptionFile = new File(directory + File.separator + PluginConfiguration.PLUGIN_CONFIGURATION_XML_NAME);
        PluginConfigurationValidator descriptionValidator = new PluginConfigurationValidator();
        boolean isXmlValid = descriptionValidator.validateXml(tagsDescriptionFile);

        assertTrue(isXmlValid);
    }
}
