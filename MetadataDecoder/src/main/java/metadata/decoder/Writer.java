package metadata.decoder;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public interface Writer {

    void setTagExclusion(Set<String> tagExclusion);

    void setTagHeader(Set<String> tagHeader);

    void writeData(List<ChannelMetadata> metadata);

}
