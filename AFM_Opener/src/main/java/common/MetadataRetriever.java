package common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.decoder.*;

/**
 *
 * @author Drimal
 */
public class MetadataRetriever {

    List<ChannelMetadata> imagesMetadata;

    public List<ChannelMetadata> parseMetada(Map<File, List<Integer>> channelMap) {
        imagesMetadata = new ArrayList<>();
        Decoder decoder = new MetadataDecoder();

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            try {
                File file = entry.getKey();

//            FileInfo[] retrievedFiloInfos = retrieveFiloInfos(file);
                imagesMetadata.addAll(decoder.decodeMetadata(file));
            } catch (Exception ex) {
                Logger.getLogger(MetadataRetriever.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return imagesMetadata;
    }

    public List<ChannelMetadata> getImagesMetadata() {
        return imagesMetadata;
    }
//
//    private FileInfo[] retrieveFiloInfos(File file) {
//        String dir = file.getParent();
//        String name = file.getName();
//        TiffDecoder td = new TiffDecoder(dir, name);
//        td.enableDebugging();
//        FileInfo[] fi = new FileInfo[1];
//
//        try {
//            fi = td.getTiffInfo();
//        } catch (IOException ex) {
//            IJ.log(ex.getMessage());
//            return null;
//        }
//        return fi;
//    }
}
