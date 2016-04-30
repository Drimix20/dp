package interactive.analyzer.listeners;

import java.awt.Color;

/**
 *
 * @author Drimal
 */
public interface ImageWindowObjectListener {

    void multipleRoiSelected(int roiId, Color color);

    void roiDeselected(int roiId);
}
