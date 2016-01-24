package interactive.analyzer.selection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class TagManager {

    private static TagManager instance = null;
    private List<Tag> tags;
    private int idCounter;

    private TagManager() {
        idCounter = 0;
        tags = new ArrayList<>();
    }

    public static TagManager getInstance() {
        if (instance == null) {
            instance = new TagManager();
        }
        return instance;
    }

    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Clear container holds all saved tags
     */
    public void clearAllTags() {
        tags.clear();
    }

    /**
     * Get tag saved on specified index
     * @param id
     * @return tag on specific id or null if id is out of range or no tag is on index
     */
    public Tag getTagById(int id) {
        for (Tag t : tags) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Get tag with specific color
     * @param color
     * @return tag with specified color or null if no tag has this color
     */
    public Tag getTagByColor(Color color) {
        for (Tag t : tags) {
            if (t.getColor().equals(color)) {
                return t;
            }
        }
        return null;
    }

    public Color getTagColor(int id) {
        for (Tag t : tags) {
            if (t.getId() == id) {
                return t.getColor();
            }
        }
        return null;
    }

    public int addTag(Color color, String name, String description) {
        validateColor(color);
        validateName(name);
        validateDescription(description);

        idCounter++;

        Tag tag = new Tag(color, name, description);
        tag.setId(idCounter);
        tags.add(tag);

        return tag.getId();
    }

    public void editTag(Tag tag) {
        validateTag(tag);

        Tag t = tags.get(tag.getId());
        t.setColor(tag.getColor());
        t.setName(tag.getName());
        t.setDescription(tag.getDescription());
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    private void validateTag(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag is null");
        }
        validateName(tag.getName());
        validateColor(tag.getColor());
        validateDescription(tag.getDescription());
    }

    private void validateDescription(String desc) {
        if (desc == null) {
            throw new IllegalArgumentException("Description is null");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty");
        }
    }

    private void validateColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Color is null");
        }
    }

}
