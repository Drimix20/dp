package writer.tags.sorter;

import configuration.xml.elements.TagConfiguration;

/**
 * Sort TagsConfiguration by tag's decimal ID
 *
 * @author Drimal
 */
public class DecimalIDTagsDescriptionSorter implements TagsDescriptionSorter {

    @Override
    public int compare(TagConfiguration tag1, TagConfiguration tag2) {
        return Integer.valueOf(tag1.getDecimalID()).compareTo(tag2.getDecimalID());
    }

}
