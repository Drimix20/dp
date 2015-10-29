package configuration.parser;

import configuration.xml.elements.ConfigurationXmlRootElement;

/**
 *
 * @author Drimal
 */
public interface PluginConfigurationParser {

    ConfigurationXmlRootElement parseConfigurationFile(String filePath);

}
