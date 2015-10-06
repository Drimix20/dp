package writer;

import configuration.Tag;
import java.util.List;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public interface ImageTagsWriter {

    public void dumpTagsIntoFile(List<ChannelContainer> channels,
            String filePath, String filename);

    public void setTagsDescription(List<Tag> tagsDescription);
}
