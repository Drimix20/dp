/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results.table;

import ij.text.TextWindow;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Drimal
 */
public class ExtendedTextWindow extends TextWindow {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ExtendedTextWindow.class);

    public ExtendedTextWindow(String title, String text, int width, int height) {
        super(title, text, width, height);
        //instantiateTextPanelAsExtendedTextPanel();
    }

    public ExtendedTextWindow(String title, String headings, String text,
            int width, int height) {
        super(title, headings, text, width, height);
    }

    public ExtendedTextWindow(String title, String headings, ArrayList text,
            int width, int height) {
        super(title, headings, text, width, height);
        instantiateTextPanelAsExtendedTextPanel();
    }

    public ExtendedTextWindow(String path, int width, int height) {
        super(path, width, height);
        instantiateTextPanelAsExtendedTextPanel();
    }

    private Field getDeclaratedTextPanelField() {
        try {
            return TextWindow.class.getDeclaredField("textPanel");
        } catch (Exception ex) {
            Logger.getLogger(ExtendedTextWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void instantiateTextPanelAsExtendedTextPanel() {
        Field textPanelField = getDeclaratedTextPanelField();
        if (textPanelField != null) {
            try {
                textPanelField.setAccessible(true);
                textPanelField.set(this, new ExtendedTextPanel());
            } catch (Exception ex) {
                Logger.getLogger(ExtendedTextWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
