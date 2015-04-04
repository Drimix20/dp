package metadata.decoder;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MetadataWriter {

    Logger logger = Logger.getLogger(MetadataWriter.class);
    private static final String NEW_LINE = "\n";
    private static final String DELIMETR = "\t";
    private File outputFile;
    private Set<String> tagHeader;
    private Set<String> tagExclusion;

    public MetadataWriter(File directory) {
        this.outputFile = directory;
        tagHeader = new TreeSet<>();
        tagExclusion = new TreeSet<>();
    }

    public void setTagExclusion(Set<String> tagExclusion) {
        logger.info("Setting tag exclusion: " + tagExclusion);
        this.tagExclusion = tagExclusion;
    }

    public void setTagHeader(Set<String> tagHeader) {
        logger.info("Setting tag headers: " + tagHeader);
        this.tagHeader = tagHeader;
    }

    public void writeData(List<ChannelMetadata> metadata) {
        logger.info("Writing metadata to file " + outputFile);
        StringBuilder stringBuilder = new StringBuilder(printHeader());

        stringBuilder.append(NEW_LINE);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))) {

            for (ChannelMetadata singleChannel : metadata) {
                stringBuilder.append(singleChannel.getFilePath()).append(DELIMETR);

                for (String tag : tagHeader) {
                    if (tagExclusion.contains(tag)) {
                        stringBuilder.append(DELIMETR).append("");
                        continue;
                    }
                    Integer tagInt = Integer.parseInt(tag.trim());
                    Object tagValue = singleChannel.getTagValue(tagInt);
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
        }

    }

    private String printHeader() {
        String output = "file" + DELIMETR;
        for (Iterator<String> it = tagHeader.iterator(); it.hasNext();) {
            output += DELIMETR + it.next();
        }
        return output;
    }
}
