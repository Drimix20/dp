package afm.opener.writer.tags.sorter;

import configuration.module.xml.elements.TagConfiguration;

/**
 * Sort TagsConfiguration by tag's category
 *
 * @author Drimal
 */
public class CategoryTagsDescriptionSorter implements TagsDescriptionSorter {

    @Override
    public int compare(TagConfiguration tag1, TagConfiguration tag2) {
        if (tag1.getCategory().equals("general") && tag2.getCategory().equals("channel")) {
            return -1;
        }
        if (tag1.getCategory().equals("channel") && tag2.getCategory().equals("general")) {
            return 1;
        }
        return 0;
    }

}
