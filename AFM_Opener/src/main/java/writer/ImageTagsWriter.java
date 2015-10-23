package writer;

import configuration.xml.elements.Tag;
import java.util.List;
import selector.ChannelContainer;
import writer.tags.sorter.TagsDescriptionSorter;

/**
 *
 * @author Drimal
 */
public interface ImageTagsWriter {

    public void dumpTagsIntoFile(List<ChannelContainer> channels,
            String filePath, String filename);

    public void setTagsDescription(List<Tag> tagsDescription);

    public void setTagsDescriptionSorter(TagsDescriptionSorter comparator);
}
