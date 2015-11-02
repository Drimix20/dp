package selector;

import ij.ImagePlus;
import java.io.File;
import java.util.Objects;
import metadata.decoder.ChannelMetadata;

/**
 *
 * @author Drimal
 */
public class ChannelContainer {

    private static final int CHANNEL_NAME_TAG = 32848;
    private ImagePlus imagePlus;
    private final File file;
    private final int channelIndex;
    private String IFD;
    private final String channelName;
//    private double gridULength;
//    private double gridVLength;
//    private double scalingMultiplier;
//    private double scalingOffset;
    private ChannelMetadata generalMetadata;
    private ChannelMetadata channelmetadata;

    public ChannelContainer(File file, int channelIndex,
            ChannelMetadata generalMetadata,
            ChannelMetadata channelMetadata) {
        this.file = file;
        this.generalMetadata = generalMetadata;
        this.channelIndex = channelIndex;
        IFD = channelMetadata.getIFD();
        this.channelmetadata = channelMetadata;
        channelName = (String) channelMetadata.getTagValue(CHANNEL_NAME_TAG);
    }

    public int getChannelIndex() {
        return channelIndex;
    }

    public ImagePlus getImagePlus() {
        return imagePlus;
    }

    public void setImagePlus(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    public ChannelMetadata getChannelMetadata() {
        return channelmetadata;
    }

    public ChannelMetadata getGeneralMetadata() {
        return generalMetadata;
    }

    public void setMetadata(ChannelMetadata metadata) {
        IFD = metadata.getIFD();
        this.channelmetadata = metadata;
    }

    public File getFile() {
        return file;
    }

    public String getIFFD() {
        return IFD;
    }

    public void setIFFD(String IFFD) {
        this.IFD = IFFD;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.file);
        hash = 89 * hash + Objects.hashCode(this.IFD);
        hash = 89 * hash + Objects.hashCode(this.channelName);
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
        final ChannelContainer other = (ChannelContainer) obj;
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        if (!Objects.equals(this.IFD, other.IFD)) {
            return false;
        }
        if (!Objects.equals(this.channelName, other.channelName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ChannelContainer{"
                + "file=" + file
                + ", IFFD=" + IFD
                + ", channelName="
                + channelName
                + '}';
    }
}
