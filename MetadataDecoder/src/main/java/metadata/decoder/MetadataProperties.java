package metadata.decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public class MetadataProperties {

    private static Map<String, String> generalTagsName;
    private static Map<String, String> channelTagsName;
    private final static String $ = File.separator;
    private static String RESOURCE_DIR = MetadataProperties.class.getResource("/").getFile();
    private static final String GENERAL_PROPERTIES_FILE_NAME = "general_tags.properties";
    private static final String CHANNEL_PROPERTIES_FILE_NAME = "channel_tags.properties";

    private static Map<String, String> loadProperties(String fileName) {
        Map<String, String> props = new HashMap<String, String>();
        File propFile = new File(RESOURCE_DIR + $ + fileName);

        try {
            if (!propFile.exists()) {
                throw new IllegalArgumentException("Property file " + propFile.getAbsolutePath() + " doesn't exist");
            }
            if (propFile.canRead()) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(propFile));
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    props.put((String) entry.getKey(), ((String) entry.getValue()).trim());
                }
            } else {
                throw new IllegalArgumentException("Cannot read properties from file " + propFile.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Error loading properties from file %s.", propFile.getAbsolutePath()), e);
        }
        return props;
    }

    static {
        generalTagsName = loadProperties(GENERAL_PROPERTIES_FILE_NAME);
        channelTagsName = loadProperties(CHANNEL_PROPERTIES_FILE_NAME);
    }

    public static Map<String, String> getGeneralTagsName() {
        return generalTagsName;
    }

    public static Map<String, String> getChannelTagsName() {
        return channelTagsName;
    }

    public static Set<String> getGeneralTags() {
        return generalTagsName.keySet();
    }

    public static Set<String> getChanelTags() {
        return channelTagsName.keySet();
    }
}
