package writer;

import configuration.Tag;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import metadata.decoder.ChannelMetadata;
import metadata.writer.MetadataWriter;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class CsvImageTagsWriter implements ImageTagsWriter {

    private Logger logger = Logger.getLogger(CsvImageTagsWriter.class);
    private static final String NEW_LINE = "\n";
    private String DELIMETR = ",";
    private List<Tag> tagsDescription = new ArrayList<>();

    public void setTagsDescription(List<Tag> tagsDescription) {
        this.tagsDescription = tagsDescription;
    }

    @Override
    public void dumpTagsIntoFile(List<ChannelContainer> channels,
            String filePath, String filename) {

        if (!isCsvFile(filename)) {
            filename += ".csv";
        }
        File outputFile = new File(filePath + File.separator + filename);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException ex) {
                logger.debug("Cannot create output file " + outputFile.getAbsolutePath());
            }
        }

        if (tagsDescription.isEmpty()) {
            logger.debug("No tags description is available. Writing base header");
            //TODO check needs of generic or channel properties
            Set<Integer> tagsHeader = new TreeSet<Integer>(Arrays.asList(new Integer[]{
                256, 257, 258, 259, 262, 273, 277, 278, 279, 320, 32768, 32769, 32770, 32771, 32772, 32773, 32774, 32775, 32776, 32777,
                32784, 32785, 32786, 32787, 32788, 32789, 32790, 32791, 32816, 32817, 32818, 32819, 32820, 32821, 32822, 32823, 32824, 32825,
                32826, 32827, 32828, 32829, 32830, 32832, 32833, 32834, 32835, 32836, 32837, 32838, 32839, 32840, 32841, 32842, 32843, 32844,
                32845, 32846, 32848, 32849, 32850, 32851, 32852, 32853, 32854, 32855, 32856, 32857, 32858, 32859, 32860, 32861, 32862, 32864,
                32865, 32866, 32867, 32868, 32896, 32897, 32912, 32913, 32928, 32929, 32931, 32960, 32961, 32962, 32976, 32977, 32978, 32979,
                32980, 32981, 33008, 33009, 33010, 33024, 33025, 33026, 33027, 33028, 33029, 33056, 33057, 33077, 256, 257, 258, 259, 262, 273, 277, 278, 279, 339, 32848, 32849, 32850,
                32851, 32864, 32865, 32896, 32897, 32912, 32913, 32914, 32928, 32929, 32930, 32931, 32932, 32933, 32937, 32938, 32960, 32961,
                32962, 32976, 32977, 32978, 32979, 32980, 32981, 33008, 33009, 33010, 33024, 33025, 33026, 33027, 33028, 33029, 33056, 33057,
                33058, 33072, 33073, 33074, 33075, 33076, 33077}));

            List<ChannelMetadata> channelMetadata = new ArrayList<>();
            for (ChannelContainer channelContainer : channels) {
                channelMetadata.add(channelContainer.getMetadata());
            }

            MetadataWriter metadataWriter = new MetadataWriter(outputFile);
            metadataWriter.setDelimeter(DELIMETR);
            metadataWriter.setTagHeader(tagsHeader);
            metadataWriter.writeData(channelMetadata);
        } else {
            logger.debug("Write extended header");
            StringBuilder stringBuilder = new StringBuilder("");
            prepareHeaderFromTagsDescription(stringBuilder);

            for (ChannelContainer container : channels) {
                ChannelMetadata metadata = container.getMetadata();
                stringBuilder.append("\"").append(metadata.getFilePath()).append("\"").append(DELIMETR);
                for (Tag tag : tagsDescription) {
                    Object tagValue = metadata.getTagValue(tag.getDecimalValue());
                    stringBuilder.append("\"").append(tagValue).append("\"").append(DELIMETR);
                }
                stringBuilder.append(NEW_LINE);
            }

            try (java.io.Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))) {
                writer.write(stringBuilder.toString());
            } catch (Exception ex) {
                logger.error("Error while writing tags into file " + outputFile.getPath());
            }
        }
    }

    private void prepareHeaderFromTagsDescription(StringBuilder stringBuilder) {
        appendTagNameLine(stringBuilder, tagsDescription).append(NEW_LINE);
        apppendTagDecimalValueLine(stringBuilder, tagsDescription).append(NEW_LINE);
        appendTagHexadecimalValueLine(stringBuilder, tagsDescription).append(NEW_LINE);
        appendTagCategoryLine(stringBuilder, tagsDescription).append(NEW_LINE);
        appendTagDescriptionLine(stringBuilder, tagsDescription).append(NEW_LINE);
        appendFilenameLine(stringBuilder, tagsDescription).append(NEW_LINE);
    }

    private boolean isCsvFile(String fileName) {
        return fileName.contains(".csv") || fileName.contains(".CSV");
    }

    private StringBuilder appendTagNameLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("\"").append("name").append("\"");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getName()).append("\"");
        }
        return sb;
    }

    private StringBuilder apppendTagDecimalValueLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("tag");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append(tag.getDecimalValue());
        }
        return sb;
    }

    private StringBuilder appendTagHexadecimalValueLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("\"").append("hexadecimal").append("\"");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getHexadecimalValue()).append("\"");
        }
        return sb;
    }

    private StringBuilder appendTagDescriptionLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("\"").append("description").append("\"");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getDescription()).append("\"");
        }
        return sb;
    }

    private StringBuilder appendTagCategoryLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("\"").append("category").append("\"");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getCategory()).append("\"");
        }
        return sb;
    }

    private StringBuilder appendFilenameLine(StringBuilder sb,
            List<Tag> tagsDescription) {
        sb.append("\"").append("filename").append("\"");
        for (Tag tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append("#").append("\"");
        }
        return sb;
    }
}
