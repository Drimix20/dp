package configuration.module.xml.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drimal
 */
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TagConfiguration {

    private String category;
    private String name;
    private int decimal;
    private String hexadecimal;
    private String offsetHexadecimal = "";
    private String description = "";

    @XmlAttribute(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String categoryVal) {
        this.category = categoryVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDecimalID() {
        return decimal;
    }

    @XmlElement(name = "decimal")
    public void setDecimalValue(int decimalValue) {
        this.decimal = decimalValue;
    }

    public String getHexadecimalID() {
        return hexadecimal;
    }

    @XmlElement(name = "hexadecimal")
    public void setHexadecimalValue(String hexadecimalValue) {
        this.hexadecimal = hexadecimalValue;
    }

    @XmlElement(name = "offset_hexadecimal")
    public void setOffsetHexadecimal(String offsetHexadecimalValue) {
        this.offsetHexadecimal = offsetHexadecimalValue;
    }

    public String getOffsetHexadecimal() {
        return offsetHexadecimal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 79 * hash + this.decimal;
        hash = 79 * hash + (this.hexadecimal != null ? this.hexadecimal.hashCode() : 0);
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
        final TagConfiguration other = (TagConfiguration) obj;
        if ((this.category == null) ? (other.category != null) : !this.category.equals(other.category)) {
            return false;
        }
        if (this.decimal != other.decimal) {
            return false;
        }
        if ((this.hexadecimal == null) ? (other.hexadecimal != null) : !this.hexadecimal.equals(other.hexadecimal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" + "category=" + category + ", name=" + name + ", decimal=" + decimal + ", hexadecimal=" + hexadecimal + ", offsetHexadecimal=" + offsetHexadecimal + ", description=" + description + '}';
    }

    public String toCsvString() {
        return "\"" + category + "\",\"" + name + "\",\"" + decimal + "\",\"" + hexadecimal + "\",\"" + offsetHexadecimal + "\",\"" + description + "\"";
    }

}
