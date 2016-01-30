package configuration.module.parser;

import configuration.module.xml.elements.ConfigurationXmlRootElement;

/**
 *
 * @author Drimal
 */
public interface PluginConfigurationParser {

    ConfigurationXmlRootElement parseConfigurationFile(String filePath);

}
