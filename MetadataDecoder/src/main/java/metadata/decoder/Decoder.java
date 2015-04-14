package metadata.decoder;

import java.io.File;
import java.util.List;

/**
 *
 * @author Drimal
 */
public interface Decoder {

    List<ChannelMetadata> decodeMetadata(File file) throws Exception;

    List<ChannelMetadata> decodeMetadata(List<File> file) throws Exception;
}
