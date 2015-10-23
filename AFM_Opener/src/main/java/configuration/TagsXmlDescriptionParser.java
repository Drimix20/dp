package configuration;

import configuration.xml.elements.Tags;
import configuration.xml.elements.Tag;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Drimal
 */
public class TagsXmlDescriptionParser implements TagsDescriptionParser {

    @Override
    public List<Tag> parseTagsDescriptions(String filePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{Tags.class, Tag.class});
            Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();
            Tags tags = (Tags) jaxbUnmarshal.unmarshal(new File(filePath));
            return tags.getTags();
        } catch (Exception ex) {
            Logger.getLogger(TagsXmlDescriptionParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
}
