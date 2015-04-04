package common;

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
public class JpkTagFields {

    private static Map<String, TagFieldType> typedGeneralTags;
    private static Map<String, TagFieldType> typedChannelTags;
    private final static String $ = File.separator;
    private static String RESOURCE_DIR = JpkTagFields.class.getResource("/").getFile();
    private static final String GENERAL_PROPERTIES_FILE_NAME = "general_tags.properties";
    private static final String CHANNEL_PROPERTIES_FILE_NAME = "channel_tags.properties";
    private static final File DEF_PROPERTIES_DIR = new File("rsc");

    private static Map<String, String> loadProperties(String fileName) {
        Map<String, String> props = new HashMap<>();
        File propFile = new File("src" + $ + DEF_PROPERTIES_DIR + $ + fileName);

        try {
            if (!propFile.exists()) {
                throw new IllegalArgumentException(String.format("Error loading properties from file %s.", propFile.getAbsolutePath()));
            }
            if (propFile.canRead()) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(propFile));
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    props.put((String) entry.getKey(), (String) entry.getValue());
                }
            } else {
                throw new IllegalArgumentException(String.format("Error loading properties from file %s.", propFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Error loading properties from file %s.", propFile.getAbsolutePath()), e);
        }
        return props;
    }

    static {
        Map<String, String> generalTagProperties = loadProperties(GENERAL_PROPERTIES_FILE_NAME);
        Map<String, String> channelTagProperties = loadProperties(CHANNEL_PROPERTIES_FILE_NAME);
        typedGeneralTags = makeTypedTags(generalTagProperties);
        typedChannelTags = makeTypedTags(channelTagProperties);
    }

    private static Map<String, TagFieldType> makeTypedTags(Map<String, String> tagProperties) {
        Map<String, TagFieldType> typedTags = new HashMap<>();
        for (Map.Entry<String, String> entry : tagProperties.entrySet()) {
            String[] entryValueArray = entry.getValue().split(",");
            String type = entryValueArray[0];
            String name = entryValueArray[1];
            TagFieldType convertedType = convertToTagType(type);
            typedTags.put(entry.getKey(), convertedType);
        }

        return typedTags;
    }

    public static TagFieldType retrieveTypeOfChannelTag(int tagDecimal) {
        TagFieldType type = typedChannelTags.get(tagDecimal + "");
        if (type == null) {
            return TagFieldType.UNDEFINED;
        }
        return type;
    }

    public static TagFieldType retrieveTypeOfGeneralTag(int tagDecimal) {
        TagFieldType type = typedGeneralTags.get(tagDecimal + "");
        if (type == null) {
            return TagFieldType.UNDEFINED;
        }
        return type;
    }

    private static TagFieldType convertToTagType(String type) {
        TagFieldType convertedType;
        switch (type) {
            case "d":
                convertedType = TagFieldType.DOUBLE;
                break;
            case "i":
                convertedType = TagFieldType.INT;
                break;
            case "bool":
                convertedType = TagFieldType.BOOL;
                break;
            case "str":
                convertedType = TagFieldType.STRING;
                break;
            case "date":
                convertedType = TagFieldType.DATE;
                break;
            default:
                convertedType = TagFieldType.UNDEFINED;
                break;
        }
        return convertedType;
    }

    public static Set<String> getGeneralTags() {
        return typedGeneralTags.keySet();
    }

    public static Set<String> getChanelTags() {
        return typedChannelTags.keySet();
    }
}
