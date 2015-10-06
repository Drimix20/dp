package configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drimal
 */
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag {

    @XmlAttribute
    private String category;
    private String name;
    private int decimal;
    private String hexadecimal;
    private String description;

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

    public int getDecimalValue() {
        return decimal;
    }

    public void setDecimalValue(int decimalValue) {
        this.decimal = decimalValue;
    }

    public String getHexadecimalValue() {
        return hexadecimal;
    }

    public void setHexadecimalValue(String hexadecimalValue) {
        this.hexadecimal = hexadecimalValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
