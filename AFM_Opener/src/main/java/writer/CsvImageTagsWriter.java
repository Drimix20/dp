package writer;

import configuration.xml.elements.TagConfiguration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import metadata.decoder.ChannelMetadata;
import metadata.writer.MetadataWriter;
import org.apache.log4j.Logger;
import selector.ChannelContainer;
import writer.tags.sorter.TagsDescriptionSorter;

/**
 *
 * @author Drimal
 */
public class CsvImageTagsWriter implements ImageTagsWriter {

    private Logger logger = Logger.getLogger(CsvImageTagsWriter.class);
    private static final String NEW_LINE = "\n";
    private String DELIMETR = ",";
    private Set<Integer> tagsInImage;
    private List<TagConfiguration> tagsDescription = new ArrayList<>();
    //TODO implement possile to add more sorters
    //TODO addDescriptionSorter, listDescriptionSorter, removeDescriptionSorter, removeAllDescriptionSorter
    //TODO export tags due to tags in image. If possible add more info from tagsDescription file
    private TagsDescriptionSorter tagsDescriptionComparator;

    public CsvImageTagsWriter() {
        tagsInImage = new TreeSet<Integer>(Arrays.asList(new Integer[]{
            256, 257, 258, 259, 262, 273, 277, 278, 279, 320, 32768, 32769, 32770, 32771, 32772, 32773, 32774, 32775, 32776, 32777,
            32784, 32785, 32786, 32787, 32788, 32789, 32790, 32791, 32816, 32817, 32818, 32819, 32820, 32821, 32822, 32823, 32824, 32825,
            32826, 32827, 32828, 32829, 32830, 32832, 32833, 32834, 32835, 32836, 32837, 32838, 32839, 32840, 32841, 32842, 32843, 32844,
            32845, 32846, 32848, 32849, 32850, 32851, 32852, 32853, 32854, 32855, 32856, 32857, 32858, 32859, 32860, 32861, 32862, 32864,
            32865, 32866, 32867, 32868, 32896, 32897, 32912, 32913, 32928, 32929, 32931, 32960, 32961, 32962, 32976, 32977, 32978, 32979,
            32980, 32981, 33008, 33009, 33010, 33024, 33025, 33026, 33027, 33028, 33029, 33056, 33057, 33077, 256, 257, 258, 259, 262, 273, 277, 278, 279, 339, 32848, 32849, 32850,
            32851, 32864, 32865, 32896, 32897, 32912, 32913, 32914, 32928, 32929, 32930, 32931, 32932, 32933, 32937, 32938, 32960, 32961,
            32962, 32976, 32977, 32978, 32979, 32980, 32981, 33008, 33009, 33010, 33024, 33025, 33026, 33027, 33028, 33029, 33056, 33057,
            33058, 33072, 33073, 33074, 33075, 33076, 33077}));
    }

    public void setTagsDescription(List<TagConfiguration> tagsDescription) {
        this.tagsDescription = tagsDescription;
    }

    @Override
    public void setTagsDescriptionSorter(TagsDescriptionSorter comparator) {
        this.tagsDescriptionComparator = comparator;
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

        sortTagsDescriptionByConfiguredSorter();

        if (tagsDescription.isEmpty()) {
            exportTagsInBasicFormat(channels, outputFile);
        } else {
            exportTagsInExtendedFormat(channels, outputFile);
        }
    }

    private void sortTagsDescriptionByConfiguredSorter() {
        if (tagsDescriptionComparator != null && !tagsDescription.isEmpty()) {
            Collections.sort(tagsDescription, tagsDescriptionComparator);
        }
    }

    private void exportTagsInBasicFormat(List<ChannelContainer> channels,
            File outputFile) {
        logger.debug("No tags description is available. Writing base header");

        List<ChannelMetadata> channelMetadata = new ArrayList<>();
        for (ChannelContainer channelContainer : channels) {
            channelMetadata.add(channelContainer.getMetadata());
        }
        MetadataWriter metadataWriter = new MetadataWriter(outputFile);
        metadataWriter.setDelimeter(DELIMETR);
        metadataWriter.setTagHeader(tagsInImage);
        metadataWriter.writeData(channelMetadata);
    }

    private void exportTagsInExtendedFormat(List<ChannelContainer> channels,
            File outputFile) {
        logger.debug("Write extended header");
        StringBuilder stringBuilder = new StringBuilder("");//TODO cannot take index 0. Completely change tagsWriter
        if (channels.size() > 0) {
            Set<Integer> tagsHeader = channels.get(0).getMetadata().getTags();
        }
        prepareHeaderFromTagsDescription(stringBuilder, channels.get(0).getMetadata(), tagsInImage);

        for (ChannelContainer container : channels) {
            ChannelMetadata metadata = container.getMetadata();
            stringBuilder.append("\"").append(metadata.getFilePath()).append("\"").append(DELIMETR);
            for (TagConfiguration tag : tagsDescription) {
                Object tagValue = metadata.getTagValue(tag.getDecimalValue());
                stringBuilder.append("\"").append(substituteNewLineBySemicolon(tagValue)).append("\"").append(DELIMETR);
                if (!tag.getOffsetHexadecimal().isEmpty()) {
                    List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                    for (Integer tagDecimal : lookingForOffsetTags) {
                        stringBuilder.append("\"").append(substituteNewLineBySemicolon(metadata.getTagValue(tagDecimal))).append("\"").append(DELIMETR);
                    }
                }
            }
            stringBuilder.append(NEW_LINE);
        }

        try (java.io.Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))) {
            writer.write(stringBuilder.toString());
        } catch (Exception ex) {
            logger.error("Error while writing tags into file " + outputFile.getPath());
        }
    }

    private Object substituteNewLineBySemicolon(Object value) {
        if (value instanceof String) {
            return ((String) value).replaceAll(NEW_LINE, ";");
        }
        return value;
    }

    private List<Integer> lookingForOffsetTags(ChannelMetadata metadata,
            int decimalTag,
            int offset) {
        List<Integer> tags = new ArrayList<>();
        int n = 1;
        logger.debug("OriginalTag: " + decimalTag);
        int nextDecimalTag = decimalTag + n * offset;
        while (metadata.getTagValue(nextDecimalTag) != null) {
            logger.debug("NextDecimalTag found: " + nextDecimalTag);
            Object tagValue = metadata.getTagValue(nextDecimalTag);
            logger.debug("NextDecimalTag value: " + tagValue);
            tags.add(nextDecimalTag);
            n++;
            nextDecimalTag = decimalTag + n * offset;
        }
        return tags;
    }

    private void prepareHeaderFromTagsDescription(StringBuilder stringBuilder,
            ChannelMetadata metadata, Set<Integer> tagsInImage) {
        appendTagNameLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
        apppendTagDecimalValueLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
        appendTagHexadecimalValueLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
        appendTagCategoryLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
        appendTagDescriptionLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
        appendFilenameLine(stringBuilder, tagsDescription, metadata).append(NEW_LINE);
    }

    private boolean isCsvFile(String fileName) {
        return fileName.contains(".csv") || fileName.contains(".CSV");
    }

    private StringBuilder appendTagNameLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("\"").append("name").append("\"");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getName()).append("\"");
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append(tag.getName()).append("\"");
                }
            }
        }
        return sb;
    }

    private StringBuilder apppendTagDecimalValueLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("tag");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append(tag.getDecimalValue());
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append(tagDecimal).append("\"");
                }
            }
        }
        return sb;
    }

    private StringBuilder appendTagHexadecimalValueLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("\"").append("hexadecimal").append("\"");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getHexadecimalValue()).append("\"");
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append("0x" + Integer.toHexString(tagDecimal)).append("\"");
                }
            }
        }
        return sb;
    }

    private StringBuilder appendTagDescriptionLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("\"").append("description").append("\"");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getDescription()).append("\"");
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append(tag.getDescription()).append("\"");
                }
            }
        }
        return sb;
    }

    private StringBuilder appendTagCategoryLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("\"").append("category").append("\"");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append(tag.getCategory()).append("\"");
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append(tag.getCategory()).append("\"");
                }
            }
        }
        return sb;
    }

    private StringBuilder appendFilenameLine(StringBuilder sb,
            List<TagConfiguration> tagsDescription, ChannelMetadata metadata) {
        sb.append("\"").append("filename").append("\"");
        for (TagConfiguration tag : tagsDescription) {
            sb.append(DELIMETR).append("\"").append("#").append("\"");
            if (!tag.getOffsetHexadecimal().isEmpty()) {
                List<Integer> lookingForOffsetTags = lookingForOffsetTags(metadata, tag.getDecimalValue(), Integer.decode(tag.getOffsetHexadecimal()));
                for (Integer tagDecimal : lookingForOffsetTags) {
                    sb.append(DELIMETR).append("\"").append("#").append("\"");
                }
            }
        }
        return sb;
    }
}