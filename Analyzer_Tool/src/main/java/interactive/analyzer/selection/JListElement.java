package interactive.analyzer.selection;

import javax.swing.Icon;

/**
 *
 * @author Drimal
 */
public class JListElement {

    private int id;
    private String name;
    private Icon icon;

    public JListElement(int id, String name, Icon icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
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
        final JListElement other = (JListElement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JListElement{" + "id=" + id + ", name=" + name + ", icon=" + icon + '}';
    }

}
