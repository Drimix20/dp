package writer.tags.sorter;

import configuration.xml.elements.Tag;
import static writer.tags.sorter.DefaultTagsCategorySorter.MultipleOptions.CATEGORY_SORT;
import static writer.tags.sorter.DefaultTagsCategorySorter.MultipleOptions.DECIMAL_TAG_SORT;

/**
 *
 * @author Drimal
 */
public class DefaultTagsCategorySorter implements TagsDescriptionSorter {

    @Override
    public int compare(Tag tag1, Tag tag2) {
        MultipleOptions[] options = new MultipleOptions[]{CATEGORY_SORT, DECIMAL_TAG_SORT};
        for (MultipleOptions option : options) {
            int result = option.compare(tag1, tag2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    enum MultipleOptions implements TagsDescriptionSorter {

        CATEGORY_SORT {
                    public int compare(Tag tag1, Tag tag2) {
                        if (tag1.getCategory().equals("general") && tag2.getCategory().equals("channel")) {
                            return -1;
                        }
                        if (tag1.getCategory().equals("channel") && tag2.getCategory().equals("general")) {
                            return 1;
                        }
                        return 0;
                    }
                },
        DECIMAL_TAG_SORT {
                    public int compare(Tag tag1, Tag tag2) {
                        return Integer.valueOf(tag1.getDecimalValue()).compareTo(tag2.getDecimalValue());
                    }
                };
    }
}
