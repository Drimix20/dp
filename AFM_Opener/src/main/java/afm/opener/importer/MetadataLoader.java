package afm.opener.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import metadata.decoder.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MetadataLoader {

    private static final Logger logger = Logger.getLogger(MetadataDecoder.class);
    private List<ChannelMetadata> imagesMetadata;

    public List<ChannelMetadata> parseMetadata(
            Map<File, List<Integer>> channelMap) {
        imagesMetadata = new ArrayList<ChannelMetadata>();
        Decoder decoder = new MetadataDecoder();

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            try {
                File file = entry.getKey();

                imagesMetadata.addAll(decoder.decodeMetadata(file));
            } catch (Exception ex) {
                logger.warn(ex);
            }
        }

        return imagesMetadata;
    }

    public List<ChannelMetadata> parseMetadata(File file) {
        try {
            Decoder decoder = new MetadataDecoder();
            return decoder.decodeMetadata(file);
        } catch (Exception ex) {
            logger.warn(ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<ChannelMetadata> getImagesMetadata() {
        return imagesMetadata;
    }
}
