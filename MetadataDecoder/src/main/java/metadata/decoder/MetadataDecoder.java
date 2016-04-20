package metadata.decoder;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDirectory;
import com.sun.media.jai.codec.TIFFField;
import static com.sun.media.jai.codec.TIFFField.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MetadataDecoder implements Decoder {

    private Logger logger = Logger.getLogger(MetadataDecoder.class);

    public List<ChannelMetadata> decodeMetadata(List<File> files) throws Exception {
        logger.info("Decoda metadata for " + files.size() + " files");
        List<ChannelMetadata> channels = new ArrayList<ChannelMetadata>();
        for (File file : files) {
            try {
                channels.addAll(decodeMetadata(file));
            } catch (Exception ex) {
                logger.warn("Error while decoding metadata", ex);
            }
        }

        return channels;
    }

    @Override
    public List<ChannelMetadata> decodeMetadata(File file) throws Exception {
        logger.info("Decoding metadata for file " + file.getName());
        List<ChannelMetadata> channels = new ArrayList<ChannelMetadata>();

        SeekableStream stream = null;
        try {
            stream = new FileSeekableStream(file);

            int numDirectories = TIFFDirectory.getNumDirectories(stream);
            for (int i = 0; i < numDirectories; i++) {
                TIFFDirectory tiffDirectory = new TIFFDirectory(stream, i);
                channels.add(decodeTiffDirectory(file.getName(), tiffDirectory));
            }
            logger.trace("End of IFD");
        } catch (Exception ex) {
            throw new IllegalStateException("Errow while decoding metadadata", ex);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return channels;
    }

    private ChannelMetadata decodeTiffDirectory(String fileName,
            TIFFDirectory tiffDirectory) {
        ChannelMetadata metadata = new ChannelMetadata(fileName);
        metadata.setIFD("" + tiffDirectory.getIFDOffset());

        TIFFField[] fields = tiffDirectory.getFields();
        logger.info("File: " + fileName + " , offsetIFD: " + tiffDirectory.getIFDOffset());
        for (int j = 0; j < fields.length; j++) {
            TIFFField tIFFField = fields[j];
            Object tagValue = getTagValue(tIFFField);
            metadata.setTagValue(tIFFField.getTag(), tagValue);

            logger.trace("Tag: " + tIFFField.getTag() + " , value:" + tagValue);
        }
        return metadata;
    }

    private static Object getTagValue(TIFFField tag) {
        Object value = "UNDEFINED";
        int count = tag.getCount() - 1;
        switch (tag.getType()) {
            case TIFF_BYTE:
                value = tag.getAsInt(count);
                break;
            case TIFF_ASCII:
                value = tag.getAsString(count);
                break;
            case TIFF_SHORT:
                value = tag.getAsInt(count);
                break;
            case TIFF_LONG:
                value = tag.getAsLong(count);
                break;
            case TIFF_RATIONAL:
                value = tag.getAsSRational(count);
                break;
            case TIFF_SBYTE:
                value = tag.getAsInt(count);
                break;
            case TIFF_UNDEFINED:
                value = tag.getAsInt(count);
                break;
            case TIFF_SSHORT:
                value = tag.getAsInt(count);
                break;
            case TIFF_SLONG:
                value = tag.getAsInt(count);
                break;
            case TIFF_SRATIONAL:
                value = tag.getAsSRational(count);
                break;
            case TIFF_FLOAT:
                value = tag.getAsFloat(count);
                break;
            case TIFF_DOUBLE:
                value = tag.getAsDouble(count);
                break;
            default:
                value = "UNDEFINED";
        }

        return value;
    }
}
