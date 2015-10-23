package configuration;

import configuration.xml.elements.Tag;
import java.util.List;

/**
 *
 * @author Drimal
 */
public interface TagsDescriptionParser {

    List<Tag> parseTagsDescriptions(String filePath);

}
