package configuration.xml.elements;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drimal
 */
@XmlRootElement(name = "tags")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TagListConfiguration {

    private List<TagConfiguration> tagList;

    public TagListConfiguration() {
        tagList = new ArrayList<>();
    }

    /**

     @return Return unmodifiable collection
     */
    @XmlElement(name = "tag")
    public List<TagConfiguration> getTags() {
        return tagList;
    }

    public void setTags(List<TagConfiguration> tags) {
        this.tagList = tags;
    }

}
