package importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.decoder.*;

/**
 *
 * @author Drimal
 */
public class MetadataLoader {

    List<ChannelMetadata> imagesMetadata;

    public List<ChannelMetadata> parseMetadata(Map<File, List<Integer>> channelMap) {
        imagesMetadata = new ArrayList<>();
        Decoder decoder = new MetadataDecoder();

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            try {
                File file = entry.getKey();

                imagesMetadata.addAll(decoder.decodeMetadata(file));
            } catch (Exception ex) {
                Logger.getLogger(MetadataLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return imagesMetadata;
    }

    public List<ChannelMetadata> parseMetadata(File file) {
        try {
            Decoder decoder = new MetadataDecoder();
            return decoder.decodeMetadata(file);
        } catch (Exception ex) {
            Logger.getLogger(MetadataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<ChannelMetadata> getImagesMetadata() {
        return imagesMetadata;
    }
}
