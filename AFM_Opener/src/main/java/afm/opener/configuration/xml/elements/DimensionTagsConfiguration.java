package afm.opener.configuration.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drimal
 */
@XmlRootElement(name = "dimensionTags")
@XmlAccessorType(XmlAccessType.FIELD)
public class DimensionTagsConfiguration {

    @XmlElement(name = "uLengthTag")
    private int imagePhysicalSizeXTag;

    @XmlElement(name = "vLengthTag")
    private int imagePhysicalSizeYTag;

    @XmlElement(name = "iLengthTag")
    private int imageWidthTag;

    @XmlElement(name = "jLengthTag")
    private int imageHeightTag;

    /**
     Retrieve tag which is specified for get image physical size in fast direction, i.e. y direction
     @return decimal tag
     */
    public int getImagePhysicalSizeXTag() {
        return imagePhysicalSizeXTag;
    }

    /**
     Retrieve tag which is specified for get image physical size in slow direction, i.e. y direction
     @return decimal tag
     */
    public int getImagePhysicalSizeYTag() {
        return imagePhysicalSizeYTag;
    }

    /**

     @return decimal tag
     */
    public int getImageWidthTag() {
        return imageWidthTag;
    }

    public int getImageHeightTag() {
        return imageHeightTag;
    }
}
