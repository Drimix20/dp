package metadata.writer;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import metadata.decoder.ChannelMetadata;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MetadataWriter implements Writer {

    Logger logger = Logger.getLogger(MetadataWriter.class);
    private static final String NEW_LINE = "\n";
    private static final String DELIMETR = "\t";
    private File outputFile;
    private Set<Integer> tagHeader;
    private Set<Integer> tagExclusion;

    public MetadataWriter(File outputFile) {
        this.outputFile = outputFile;
        tagHeader = new TreeSet<Integer>();
        tagExclusion = new TreeSet<Integer>();
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getTagHeaders(metadata));

        stringBuilder.append(NEW_LINE);
        java.io.Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));

            for (ChannelMetadata singleChannel : metadata) {
                stringBuilder.append(singleChannel.getFilePath());

                for (Integer tagKey : tagHeader) {
                    if (tagExclusion.contains(tagKey)) {
                        stringBuilder.append(DELIMETR).append("");
                        continue;
                    }
                    Object tagValue = singleChannel.getTagValue(tagKey);
                    if (tagValue instanceof String) {
                        tagValue = ((String) tagValue).replace("\n", ";");
                    }
                    stringBuilder.append(DELIMETR).append(tagValue);
                }
                stringBuilder.append(NEW_LINE);
            }

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

    private String getTagHeaders(List<ChannelMetadata> metadata) {
        String output = "file";
        Set<Integer> headers = getAllTagKeys(metadata);
        for (Iterator<Integer> it = headers.iterator(); it.hasNext();) {
            output += DELIMETR + it.next();
        }
        return output;
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
