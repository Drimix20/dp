package interactive.analyzer.listeners;

/**
 *
 * @author Drimal
 */
public interface ImageSelectionListener {

    public void selectedRoiEvent(int roiLabel);

    public void selectedMultipleRoiEvent(int roiLabel);

    public void clearAllSelectionsEvent();
}
