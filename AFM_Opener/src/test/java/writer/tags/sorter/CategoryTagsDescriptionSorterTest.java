package writer.tags.sorter;

import afm.opener.writer.tags.sorter.CategoryTagsDescriptionSorter;
import afm.opener.writer.tags.sorter.TagsDescriptionSorter;
import configuration.module.xml.elements.TagConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Drimal
 */
public class CategoryTagsDescriptionSorterTest {

    private TagConfiguration tag1;
    private TagConfiguration tag2;
    private TagConfiguration tag3;

    @Test
    public void testSortingByCategory() {
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
        tag3.setHexadecimalValue("0x7C05");
        tag3.setDecimalValue(31749);

        TagsDescriptionSorter instance = new CategoryTagsDescriptionSorter();
        List<TagConfiguration> tags = new ArrayList<TagConfiguration>();
        tags.add(tag3);
        tags.add(tag2);
        tags.add(tag1);
        Collections.sort(tags, instance);

        assertEquals("general", tags.get(0).getCategory());
        assertEquals("general", tags.get(1).getCategory());
        assertEquals("channel", tags.get(2).getCategory());
    }

}
