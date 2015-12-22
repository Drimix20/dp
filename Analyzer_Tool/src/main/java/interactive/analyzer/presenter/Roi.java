package interactive.analyzer.presenter;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Objects;

/**
 *
 * @author Drimal
 */
public class Roi {

    private int name = -1;
    private Polygon polygon;
    private Color strokeColor;
    private boolean isSelected;
    private boolean stateChanged = true;

    public Roi(int name, Polygon polygon, Color strokeColor, boolean isSelected) {
        this.name = name;
        this.polygon = polygon;
        this.strokeColor = strokeColor;
        this.isSelected = isSelected;
    }

    public int getName() {
        return name;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setSelected(boolean setSelected) {
        stateChanged = stateChanged(setSelected);
        this.isSelected = setSelected;
    }

    private boolean stateChanged(boolean setSelected) {
        if (setSelected && this.isSelected == true) {
            return false;
        } else if (setSelected == false && this.isSelected == false) {
            return true;
        }
        return true;
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public void setStateChanged(boolean stateChanged) {
        this.stateChanged = stateChanged;
    }

    public boolean stateChanged() {
        return stateChanged;
    }

    public boolean contains(Point p) {
        return polygon.contains(p);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Roi other = (Roi) obj;
        if (this.name != other.name) {
            return false;
        }
        if (!Objects.equals(this.polygon, other.polygon)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Roi{" + "name=" + name + ", strokeColor=" + strokeColor + ", isSelected=" + isSelected + '}';
    }

}
