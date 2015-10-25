package writer.tags.sorter;

import configuration.xml.elements.TagConfiguration;
import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public interface TagsDescriptionSorter extends Comparator<TagConfiguration> {

    @Override
    public int compare(TagConfiguration tag1, TagConfiguration tag2);

}
