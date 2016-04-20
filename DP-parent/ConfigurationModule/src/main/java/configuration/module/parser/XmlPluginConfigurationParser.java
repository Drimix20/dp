package configuration.module.parser;

import configuration.module.xml.elements.ConfigurationXmlRootElement;
import configuration.module.xml.elements.DimensionTagsConfiguration;
import configuration.module.xml.elements.TagConfiguration;
import configuration.module.xml.elements.TagListConfiguration;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class XmlPluginConfigurationParser implements PluginConfigurationParser {

    private static Logger logger = Logger.getLogger(XmlPluginConfigurationParser.class);

    @Override
    public ConfigurationXmlRootElement parseConfigurationFile(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ConfigurationXmlRootElement.class, DimensionTagsConfiguration.class, TagListConfiguration.class, TagConfiguration.class});
            Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();
            return (ConfigurationXmlRootElement) jaxbUnmarshal.unmarshal(new File(filePath));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new ConfigurationXmlRootElement();
    }
}
