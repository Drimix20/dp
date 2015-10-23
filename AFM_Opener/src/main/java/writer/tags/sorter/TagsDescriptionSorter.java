package writer.tags.sorter;

import configuration.xml.elements.Tag;
import java.util.Comparator;

/**
 *
 * @author Drimal
 */
public interface TagsDescriptionSorter extends Comparator<Tag> {

    @Override
    public int compare(Tag tag1, Tag tag2);

}
