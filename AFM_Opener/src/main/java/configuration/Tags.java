package configuration;

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
@XmlAccessorType(XmlAccessType.FIELD)
public class Tags {

    @XmlElement(name = "tag")
    private List<Tag> tagList = null;

    public List<Tag> getTags() {
        return tagList;
    }

    public void setTags(List<Tag> tags) {
        this.tagList = tags;
    }

}
