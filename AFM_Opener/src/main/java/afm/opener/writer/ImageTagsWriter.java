package afm.opener.writer;

import afm.opener.configuration.xml.elements.TagConfiguration;
import java.util.List;
import afm.opener.selector.ChannelContainer;
import afm.opener.writer.tags.sorter.TagsDescriptionSorter;

/**
 *
 * @author Drimal
 */
public interface ImageTagsWriter {

    public void dumpTagsIntoFile(List<ChannelContainer> channels,
            String filePath, String filename);

    public void setTagsDescription(List<TagConfiguration> tagsDescription);

    public boolean addTagsDescriptionSorter(TagsDescriptionSorter sorter);

    public List<TagsDescriptionSorter> listAllTagsSorters();

    public boolean removeTagsDescriptionSorter(TagsDescriptionSorter sorter);

    public void removeaAllTagsDescriptionSorters();
}
