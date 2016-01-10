package afm.opener.configuration.parser;

import afm.opener.configuration.xml.elements.ConfigurationXmlRootElement;
import afm.opener.configuration.xml.elements.DimensionTagsConfiguration;
import afm.opener.configuration.xml.elements.TagListConfiguration;
import afm.opener.configuration.xml.elements.TagConfiguration;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Drimal
 */
public class XmlPluginConfigurationParser implements PluginConfigurationParser {

    @Override
    public ConfigurationXmlRootElement parseConfigurationFile(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ConfigurationXmlRootElement.class, DimensionTagsConfiguration.class, TagListConfiguration.class, TagConfiguration.class});
            Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();
            return (ConfigurationXmlRootElement) jaxbUnmarshal.unmarshal(new File(filePath));
        } catch (Exception ex) {
            Logger.getLogger(XmlPluginConfigurationParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ConfigurationXmlRootElement();
    }
}
