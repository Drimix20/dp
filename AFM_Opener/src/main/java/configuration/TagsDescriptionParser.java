package configuration;

import java.util.List;

/**
 *
 * @author Drimal
 */
public interface TagsDescriptionParser {

    List<Tag> parseTagsDescriptions(String filePath);

}
