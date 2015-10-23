package configuration.xml.elements;

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
public class Tags {

    private List<Tag> tagList = null;

    @XmlElement(name = "tag")
    public List<Tag> getTags() {
        return tagList;
    }

    public void setTags(List<Tag> tags) {
        this.tagList = tags;
    }

}
