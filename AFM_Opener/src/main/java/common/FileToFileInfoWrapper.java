package common;

import ij.IJ;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class FileToFileInfoWrapper {

    List<ChannelMetadata> imagesMetadata;

    public List<ChannelMetadata> parseMetada(List<File> files) throws Exception {
        imagesMetadata = new ArrayList<>();
        MetadataDecoder decoder = new MetadataDecoder();
        decoder.setNotToDecode(Arrays.asList(32770, 32820, 32837, 32849, 32864, 32896));
        for (File file : files) {
            FileInfo fi = retrieveFiloInfos(file)[0];

            imagesMetadata.addAll(decoder.decodeMetadataForFileInfo(fi));
        }
        return imagesMetadata;
    }

    public List<ChannelMetadata> parseMetada(Map<File, List<Integer>> channelMap) throws Exception {
        imagesMetadata = new ArrayList<>();
        MetadataDecoder decoder = new MetadataDecoder();
        decoder.setNotToDecode(Arrays.asList(32770, 32820, 32837, 32849, 32864, 32896));

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            File file = entry.getKey();

            FileInfo[] retrievedFiloInfos = retrieveFiloInfos(file);

            imagesMetadata.addAll(decoder.decodeMetadataForFileInfo(retrievedFiloInfos[0]));
        }

        return imagesMetadata;
    }

    private FileInfo[] retrieveFiloInfos(File file) {
        String dir = file.getParent();
        String name = file.getName();
        TiffDecoder td = new TiffDecoder(dir, name);
        td.enableDebugging();
        FileInfo[] fi = new FileInfo[1];

        try {
            fi = td.getTiffInfo();
        } catch (IOException ex) {
            IJ.log(ex.getMessage());
            return null;
        }
        return fi;
    }
}
