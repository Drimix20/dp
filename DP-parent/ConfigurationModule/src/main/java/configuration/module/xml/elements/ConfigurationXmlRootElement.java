package configuration.module.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drimal
 */
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ConfigurationXmlRootElement {

    private int numberOfSlotsTag;
    private int channelNameTag;
    //@XmlElement(name = "dimensionTags")
    private DimensionTagsConfiguration dimensionTagsConfiguration;
    private TagListConfiguration tags;

    public ConfigurationXmlRootElement() {
        dimensionTagsConfiguration = new DimensionTagsConfiguration();
        tags = new TagListConfiguration();
    }

    @XmlElement(name = "tags")
    public TagListConfiguration getTagsList() {
        return tags;
    }

    public void setTagsList(TagListConfiguration tags) {
        this.tags = tags;
    }

    @XmlElement(name = "numberOfSlotsTag")
    public int getNumberOfSlotsTag() {
        return numberOfSlotsTag;
    }

    public void setNumberOfSlotsTag(int numberOfSlotsTag) {
        this.numberOfSlotsTag = numberOfSlotsTag;
    }

    @XmlElement(name = "channelNameTag")
    public int getChannelNameTag() {
        return channelNameTag;
    }

    public void setChannelNameTag(int channelNameTag) {
        this.channelNameTag = channelNameTag;
    }

    public TagListConfiguration getTags() {
        return tags;
    }

    public void setTags(TagListConfiguration tags) {
        this.tags = tags;
    }

    @XmlElement(name = "dimensionTags")
    public DimensionTagsConfiguration getDimensionTagsConfiguration() {
        return dimensionTagsConfiguration;
    }

    public void setDimensionTagsConfiguration(
            DimensionTagsConfiguration dimensionTags) {
        this.dimensionTagsConfiguration = dimensionTags;
    }

}
