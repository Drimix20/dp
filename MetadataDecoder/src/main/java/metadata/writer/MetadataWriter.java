package metadata.writer;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import metadata.decoder.ChannelMetadata;
import metadata.decoder.MetadataProperties;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MetadataWriter implements Writer {

    Logger logger = Logger.getLogger(MetadataWriter.class);
    private static final String NEW_LINE = "\n";
    private String delimeter = "\t";
    private File outputFile;
    private Set<Integer> tagHeader;
    private Set<Integer> tagExclusion;

    public MetadataWriter(File outputFile) {
        this.outputFile = outputFile;
        tagHeader = new TreeSet<Integer>();
        tagExclusion = new TreeSet<Integer>();
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

    @Override
    public void setTagExclusion(Set<Integer> tagExclusion) {
        logger.info("Setting tag exclusion: " + tagExclusion);
        this.tagExclusion = tagExclusion;
    }

    @Override
    public void setTagHeader(Set<Integer> tagHeader) {
        logger.info("Setting tag headers: " + tagHeader);
        this.tagHeader = tagHeader;
    }

    @Override
    public void writeData(List<ChannelMetadata> metadata) {
        logger.info("Writing metadata to file " + outputFile);
        StringBuilder stringBuilder = getTagHeaders(metadata);

        stringBuilder.append(NEW_LINE);
        java.io.Writer writer = null;
        try {
            for (ChannelMetadata singleChannel : metadata) {
                stringBuilder.append(singleChannel.getFilePath());

                for (Integer tagKey : tagHeader) {
                    if (tagExclusion.contains(tagKey)) {
                        stringBuilder.append(delimeter).append("");
                        continue;
                    }
                    Object tagValue = singleChannel.getTagValue(tagKey);
                    if (tagValue instanceof String) {
                        tagValue = ((String) tagValue).replace("\n", ";");
                    }
                    stringBuilder.append(delimeter).append(tagValue);
                }
                stringBuilder.append(NEW_LINE);
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
            writer.write(stringBuilder.toString());
        } catch (Exception ex) {
            logger.error("Error while writing to file", ex);
            throw new IllegalStateException("Error while writing to file");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                logger.warn("Errow while closing writer", ex);
            }
        }

    }

    protected StringBuilder getTagHeaders(List<ChannelMetadata> metadata) {
        StringBuilder sb = new StringBuilder("general/channel").append(delimeter);

        for (Integer tagKey : tagHeader) {
            String generalName = MetadataProperties.getGeneralTagsName().get("" + tagKey);
            generalName = generalName == null ? "--" : generalName;
            String channelName = MetadataProperties.getChannelTagsName().get("" + tagKey);
            channelName = channelName == null ? "--" : channelName;
            sb.append(generalName).append("/").append(channelName).append(delimeter);
        }

        sb.append("\n").append("file");
        Set<Integer> headers = getAllTagKeys(metadata);
        for (Iterator<Integer> it = headers.iterator(); it.hasNext();) {
            sb.append(delimeter).append(it.next());
        }
        return sb;
    }

    private Set<Integer> getAllTagKeys(List<ChannelMetadata> metadata) {
        if (isTagHeaderSet()) {
            return tagHeader;
        }

        Set<Integer> header = new TreeSet<Integer>();
        for (ChannelMetadata data : metadata) {
            header.addAll(data.getTags());
        }
        return header;
    }

    private boolean isTagHeaderSet() {
        return !tagHeader.isEmpty();
    }
}
