package metadata.decoder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Object represents container for metadata information about image channel
 *
 * @author Drimal
 */
public class ChannelMetadata {

    private String filePath;
    private String IFD;
    private Map<Integer, Object> tags;

    public ChannelMetadata(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("ChannelName must be set");
        }
        tags = new TreeMap<Integer, Object>();
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public Set<Integer> getTags() {
        return tags.keySet();
    }

    public String getIFD() {
        return IFD;
    }

    public void setIFD(String ifd) {
        if (ifd == null || ifd.isEmpty()) {
            throw new IllegalArgumentException("IFD is null or empty");
        }
        this.IFD = ifd;
    }

    public void setTagValue(int tag, Object value) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        if (value == null) {
            throw new IllegalArgumentException("Parameter value is null for tag " + tag);
        }
        tags.put(tag, value);
    }

    public int getIntegerTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        throw new NotImplementedException();
    }

    public String getStringTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        throw new NotImplementedException();
    }

    public boolean getBoolTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        throw new NotImplementedException();
    }

    public double getDoubleTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        throw new NotImplementedException();
    }

    public Object getTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        return tags.get(tag);
    }

    public double getDateTagValue(int tag) {
        if (tag <= 0) {
            throw new IllegalArgumentException("Parameter tag is negative or zero");
        }
        throw new NotImplementedException();
    }

    public String printTagValues() {
        StringBuilder sb = new StringBuilder("Tags:\n");
        for (Map.Entry<Integer, Object> entry : tags.entrySet()) {
            Integer tag = entry.getKey();
            Object object = entry.getValue();
            sb.append(tag + "=>" + object).append('\n');

        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.filePath);
        hash = 97 * hash + Objects.hashCode(this.IFD);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChannelMetadata other = (ChannelMetadata) obj;
        if (!Objects.equals(this.filePath, other.filePath)) {
            return false;
        }
        if (!Objects.equals(this.IFD, other.IFD)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChannelMetadata{" + ", filePath=" + filePath + ", IFD=" + IFD + '}';
    }
}
