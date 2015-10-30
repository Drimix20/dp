package results.table;

import ij.text.TextPanel;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ExtendedTextPanel extends TextPanel {

    private static final Logger logger = Logger.getLogger(ExtendedTextPanel.class);

    public ExtendedTextPanel() {
        super();
    }

    public ExtendedTextPanel(String title) {
        super(title);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logger.info("mousePressed from ExtendedTextPanel");
        logger.info("Method: " + ExtendedTextPanel.gedDeclaredMethod());

        int x = e.getX(), y = e.getY();
        if (e.isPopupTrigger() || e.isMetaDown()) {
            logger.debug("e.isPopupTriger or isMetaDown");
            //pm.show(e.getComponent(), x, y);
        } else if (e.isShiftDown()) {
            logger.debug("extendsSelection");
            //extendSelection(x, y);
        } else if (e.isControlDown()) {
            logger.debug("Control is down, <" + x + "," + y + ">");
        } else {
            logger.debug("normal left click");
//            select(x, y);
//            handleDoubleClick();
        }

    }

    public static String gedDeclaredMethod() {
        try {
            Class[] cArg = new Class[2];
            cArg[0] = int.class;
            cArg[1] = int.class;
            Method declaredMethod = TextPanel.class.getDeclaredMethod("extendSelection", cArg);

            return declaredMethod.getName();
        } catch (Exception ex) {
            logger.error(ex);
        }

        return "";
    }

}
