package afm.opener.configuration.parser;

import afm.opener.configuration.xml.elements.ConfigurationXmlRootElement;

/**
 *
 * @author Drimal
 */
public interface PluginConfigurationParser {

    ConfigurationXmlRootElement parseConfigurationFile(String filePath);

}
