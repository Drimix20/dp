package exporter;

import java.io.File;
import java.util.List;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public interface TagsExporter {

    public void exportImageTags(List<ChannelContainer> channels,
            File currentDirectory);
}
