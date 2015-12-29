package interactive.analyzer.selection;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Drimal
 */
public class ListCellRendererWithColorIcon implements ListCellRenderer {

    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Icon icon = null;
        String text = "";

        if (value instanceof JListElement) {
            JListElement e = (JListElement) value;
            icon = e.getIcon();
            text = e.getName();
        }
        if (icon != null) {
            renderer.setIcon(icon);
        }
        renderer.setText(text);
        return renderer;
    }

}
