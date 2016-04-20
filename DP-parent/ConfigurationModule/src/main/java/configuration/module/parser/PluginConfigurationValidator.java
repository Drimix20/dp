package configuration.module.parser;

import configuration.module.PluginConfiguration;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class PluginConfigurationValidator {

    private static Logger logger = Logger.getLogger(PluginConfigurationValidator.class);

    public boolean validateXml(File xmlPath) {
        return validateXMLBySchema(xmlPath.getPath(), PluginConfiguration.getConfigurationXmlSchemaPath());
    }

    public boolean validateXMLBySchema(String xmlPath, String xsdPath) {
        logger.debug("Starts validate " + xmlPath + " file");
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (Exception e) {
            logger.error("File " + xmlPath + " is not valid: " + e.getMessage());
            return false;
        }
        logger.debug("File " + xmlPath + " is valid");
        return true;
    }

}
