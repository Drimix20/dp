package writer.tags.sorter;

import configuration.xml.elements.Tag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Drimal
 */
public class DefaultTagsCategoryComparatorTest {

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;

    @Test
    public void testCorrectTagsSorting() {
        tag1 = new Tag();
        tag1.setCategory("general");
        tag1.setHexadecimalValue("0x7C5E");
        tag1.setDecimalValue(31838);
        tag2 = new Tag();
        tag2.setCategory("general");
        tag1.setHexadecimalValue("0x7C69");
        tag2.setDecimalValue(31849);
        tag3 = new Tag();
        tag3.setCategory("channel");
        tag1.setHexadecimalValue("0x7C05");
        tag3.setDecimalValue(31749);

        DefaultTagsCategorySorter instance = new DefaultTagsCategorySorter();
        List<Tag> tags = new ArrayList<>();
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag1);
        Collections.sort(tags, instance);

        assertEquals(tag1, tags.get(0));
        assertEquals(tag2, tags.get(1));
        assertEquals(tag3, tags.get(2));
    }

    @Test
    public void testCorrectSortingOfTagsWithEqualsDecimalValue() {
        tag1 = new Tag();
        tag1.setCategory("general");
        tag1.setDecimalValue(31838);
        tag1.setHexadecimalValue("0x7C5E");
        tag2 = new Tag();
        tag2.setCategory("channel");
        tag2.setDecimalValue(31838);
        tag2.setHexadecimalValue("0x7C5E");

        DefaultTagsCategorySorter instance = new DefaultTagsCategorySorter();
        List<Tag> tags = new ArrayList<>();
        tags.add(tag2);
        tags.add(tag1);
        Collections.sort(tags, instance);

        assertEquals(tag1, tags.get(0));
        assertEquals(tag2, tags.get(1));
    }
}
