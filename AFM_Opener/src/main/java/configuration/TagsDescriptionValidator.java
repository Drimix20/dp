package configuration;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class TagsDescriptionValidator {

    private static Logger logger = Logger.getLogger(TagsDescriptionValidator.class);

    public boolean validateXmlBySchema(File xmlPath) {
        String xsdPath = getXsdFilePath();
        return validateXMLBySchema(xmlPath.getPath(), xsdPath);
    }

    public boolean validateXMLBySchema(String xmlPath, String xsdPath) {
        logger.debug("Starts validate " + xmlPath + " file");
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            logger.error("File " + xmlPath + " is not valid");
            return false;
        }
        logger.debug("File " + xmlPath + " is valid ");
        return true;
    }

    private String getXsdFilePath() {
        ClassLoader loader = getClass().getClassLoader();
        return loader.getResource("tags.xsd").getFile();
    }
}
