package configuration.parser;

import configuration.xml.elements.ConfigurationXmlRootElement;

/**
 *
 * @author Drimal
 */
public interface TagsDescriptionParser {

    ConfigurationXmlRootElement parseConfigurationFile(String filePath);

}
