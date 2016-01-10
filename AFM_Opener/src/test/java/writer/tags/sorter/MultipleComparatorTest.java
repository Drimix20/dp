package writer.tags.sorter;

import afm.opener.writer.tags.sorter.MultipleComparator;
import afm.opener.writer.tags.sorter.CategoryTagsDescriptionSorter;
import afm.opener.writer.tags.sorter.DecimalIDTagsDescriptionSorter;
import afm.opener.writer.tags.sorter.TagsDescriptionSorter;
import afm.opener.configuration.xml.elements.TagConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class MultipleComparatorTest {

    private TagConfiguration tag1;
    private TagConfiguration tag2;
    private TagConfiguration tag3;
    private TagConfiguration tag4;
    private TagConfiguration tag5;

    @Test
    public void testCompare() {
        prepareTags();

        TagsDescriptionSorter instance
                = new MultipleComparator(Arrays.asList(
                                new CategoryTagsDescriptionSorter(),
                                new DecimalIDTagsDescriptionSorter()));
        List<TagConfiguration> tags = new ArrayList<>();
        tags.add(tag3);
        tags.add(tag2);
        tags.add(tag1);
        tags.add(tag5);
        tags.add(tag4);
        Collections.sort(tags, instance);

        assertEquals(tag5, tags.get(0));
        assertEquals(tag1, tags.get(1));
        assertEquals(tag2, tags.get(2));
        assertEquals(tag4, tags.get(3));
        assertEquals(tag3, tags.get(4));
    }

    private void prepareTags() {
        tag1 = new TagConfiguration();
        tag1.setCategory("general");
        tag1.setHexadecimalValue("0x7C5E");
        tag1.setDecimalValue(31838);
        tag2 = new TagConfiguration();
        tag2.setCategory("general");
        tag2.setHexadecimalValue("0x7C69");
        tag2.setDecimalValue(31849);
        tag3 = new TagConfiguration();
        tag3.setCategory("channel");
        tag3.setHexadecimalValue("0x805E");
        tag3.setDecimalValue(31769);
        tag4 = new TagConfiguration();
        tag4.setCategory("channel");
        tag4.setHexadecimalValue("0x805D");
        tag4.setDecimalValue(31749);
        tag5 = new TagConfiguration();
        tag5.setCategory("general");
        tag5.setHexadecimalValue("0x805F");
        tag5.setDecimalValue(31749);
    }

}
