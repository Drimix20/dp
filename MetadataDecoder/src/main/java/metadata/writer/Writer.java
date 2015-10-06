package metadata.writer;

import java.util.List;
import java.util.Set;
import metadata.decoder.ChannelMetadata;

/**
 *
 * @author Drimal
 */
public interface Writer {

    void setTagExclusion(Set<Integer> tagExclusion);

    void setTagHeader(Set<Integer> tagHeader);

    void writeData(List<ChannelMetadata> metadata);

    void setDelimeter(String delimeter);

}
