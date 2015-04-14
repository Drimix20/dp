package metadata.writer;

import java.util.List;
import java.util.Set;
import metadata.decoder.ChannelMetadata;

/**
 *
 * @author Drimal
 */
public interface Writer {

    void setTagExclusion(Set<String> tagExclusion);

    void setTagHeader(Set<String> tagHeader);

    void writeData(List<ChannelMetadata> metadata);

}
