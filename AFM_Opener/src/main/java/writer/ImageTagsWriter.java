package writer;

import configuration.xml.elements.TagConfiguration;
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

    public void setTagsDescription(List<TagConfiguration> tagsDescription);

    public void setTagsDescriptionSorter(TagsDescriptionSorter comparator);
}
