package configuration.module.parser;

import configuration.module.PluginConfiguration;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.xml.sax.SAXException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Drimal
 */
public class PluginConfigurationValidator {

    private static Logger logger = LoggerFactory.getLogger(PluginConfigurationValidator.class);

    public boolean validateXml(File xmlPath) {
        return validateXMLBySchema(xmlPath.getPath(), PluginConfiguration.getConfigurationXmlSchemaPath());
    }

    public boolean validateXMLBySchema(String xmlPath, String xsdPath) {
        logger.debug("Starts validate {} file", xmlPath);
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            logger.error("File {} is not valid: {}", xmlPath, e.getMessage());
            return false;
        }
        logger.debug("File {} is valid ", xmlPath);
        return true;
    }

}
