package afm.opener.writer.tags.sorter;

import afm.opener.configuration.xml.elements.TagConfiguration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class MultipleComparator implements TagsDescriptionSorter {

    private final List<TagsDescriptionSorter> comparators;

    public MultipleComparator(List<TagsDescriptionSorter> comparators) {
        this.comparators = comparators;
    }

    @Override
    public int compare(TagConfiguration o1, TagConfiguration o2) {
        for (Comparator<TagConfiguration> c : comparators) {
            int result = c.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public void sort(List<TagConfiguration> list,
            List<TagsDescriptionSorter> sorters) {
        Collections.sort(list, new MultipleComparator(sorters));
    }
}
