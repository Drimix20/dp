package interactive.analyzer.graph.shape;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Drimal
 */
public abstract class Shape {

    private int ID;
    private double value;
    private int occurence;
    private String textTooltip;

    private boolean isSelected;
    private Color bgColor = new Color(0, 0, 139);
    private Color borderColor = Color.WHITE;
    private Color selectionColor = Color.RED;

    public Shape(int ID, double value, int occurence) {
        this.ID = ID;
        this.value = value;
        this.occurence = occurence;
        this.textTooltip = "<html>value: " + value + "<br>count: " + occurence
                + "</html>";
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    public String getTooltipText() {
        return textTooltip;
    }

    public void setTooltipText(String tooltip) {
        this.textTooltip = tooltip;
    }

    public double getValue() {
        return value;
    }

    public int getOccurence() {
        return occurence;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Change position and size of shape
     * @param x
     * @param y
     * @param widht
     * @param height
     */
    public abstract void setLocationAndSize(int x, int y, int widht, int height);

    /**
     * Change position and size of shape
     * @param x
     * @param y
     * @param widht
     * @param height
     */
    public abstract void setLocationAndSize(double x, double y, double width,
            double height);

    /**
     * Draw shape on canvas
     * @param g graphics
     */
    public abstract void draw(Graphics2D g);

    /**
     * Draws chart using given graphics
     * @param g graphics
     * @param bg color of shape
     * @param border color of border
     */
    public abstract void draw(Graphics2D g, Color bg, Color border);

    /**
     * Check if point [x,y] is in shape
     * @param x
     * @param y
     @return true if point is in shape otherwise false
     */
    public abstract boolean contains(double x, double y);

    /**
     * Check if point x coordinate is in shape
     * @param x
     @return true if x coordinate is in shape otherwise false
     */
    public abstract boolean cross(double x);
}
