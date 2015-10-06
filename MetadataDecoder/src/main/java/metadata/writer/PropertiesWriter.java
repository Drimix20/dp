package metadata.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import metadata.decoder.ChannelMetadata;
import metadata.decoder.MetadataProperties;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class PropertiesWriter implements Writer {

    Logger logger = Logger.getLogger(PropertiesWriter.class);
    private static final String NEW_LINE = "\n";
    private String delimetr = "\t";
    private File outputFile;
    private Set<Integer> tagHeader;
    private Set<Integer> tagExclusion;

    public PropertiesWriter(File outputFile) {
        this.outputFile = outputFile;
        tagHeader = new TreeSet<Integer>();
        tagExclusion = new TreeSet<Integer>();
    }

    @Override
    public void setDelimeter(String delimeter) {
        this.delimetr = delimeter;
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
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));

            for (ChannelMetadata singleChannel : metadata) {
                stringBuilder.append(singleChannel.getFilePath());

                for (Integer tagKey : tagHeader) {
                    if (tagExclusion.contains(tagKey)) {
                        stringBuilder.append(delimetr).append("");
                        continue;
                    }
                    Object tagValue = singleChannel.getTagValue(tagKey);
                    if (tagValue instanceof String) {
                        tagValue = ((String) tagValue).replace("\n", ";");
                        Map<String, String> map = new TreeMap<String, String>();
                        String[] split = ((String) tagValue).split(";");
                        for (int i = 0; i < split.length; i++) {
                            String[] split1 = split[i].split(":");
                            if (split1.length == 2) {
                                map.put(split1[0].trim(), split1[1].trim());
                            }
                        }

                        for (Iterator<String> it = getTagHeader().iterator(); it.hasNext();) {
                            stringBuilder.append(delimetr).append(map.get(it.next()));
                        }
                    }
                    //stringBuilder.append(DELIMETR).append(tagValue);
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

    protected StringBuilder getTagHeaders(List<ChannelMetadata> metadata) {
        StringBuilder sb = new StringBuilder("general/channel").append(delimetr);

        for (Integer tagKey : tagHeader) {
            String generalName = MetadataProperties.getGeneralTagsName().get("" + tagKey);
            generalName = generalName == null ? "--" : generalName;
            String channelName = MetadataProperties.getChannelTagsName().get("" + tagKey);
            channelName = channelName == null ? "--" : channelName;
            sb.append(generalName).append("/").append(channelName).append(delimetr);
        }

        sb.append("\n").append("file");
        for (Iterator<String> it = getTagHeader().iterator(); it.hasNext();) {
            sb.append(delimetr).append(it.next());
        }
        return sb;
    }

    private Set<String> getTagHeader() {
        Set<String> tags = new HashSet<String>();
        tags.add("xy-scanner-position-map.defined");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.start-position.x");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.start-position.y");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.xy-scanner-mode.name");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.xy-scanner.description");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.xy-scanner.fancy-name");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.xy-scanner.name");
        tags.add("xy-scanner-position-map.xy-scanner.tip-scanner.xy-scanner.scanner");
        tags.add("xy-scanner-position-map.xy-scanners.active-xy-scanner.name");
        tags.add("xy-scanner-position-map.xy-scanners.list");
        tags.add("xy-scanner-position-map.xy-scanners.position-index");

        tags.add("adjust-reference-amplitude-feedback-settings.approach-adjust-reference-amplitude");
        tags.add("adjust-reference-amplitude-feedback-settings.reference-amplitude");
        tags.add("approach-feedback-settings.channel.name");
        tags.add("approach-feedback-settings.channel.type");
        tags.add("approach-feedback-settings.delay-after-motor-motion");
        tags.add("approach-feedback-settings.type");
        tags.add("approach-feedback-settings.velocity");
        tags.add("lockin-feedback-settings.lockin-lowpass-setting.fancy-name");
        tags.add("lockin-feedback-settings.lockin-lowpass-setting.name");
        tags.add("setpoint-feedback-settings.i-gain");
        tags.add("setpoint-feedback-settings.inverted-sense");
        tags.add("setpoint-feedback-settings.p-gain");
        tags.add("setpoint-feedback-settings.relative-setpoint");
        tags.add("setpoint-feedback-settings.setpoint");
        tags.add("setpoint-feedback-settings.setpoint-slot");
        tags.add("sine-generator-feedback-settings.amplitude");
        tags.add("sine-generator-feedback-settings.frequency");
        tags.add("sine-generator-feedback-settings.phaseshift");
        tags.add("type");

        return tags;
    }
}
