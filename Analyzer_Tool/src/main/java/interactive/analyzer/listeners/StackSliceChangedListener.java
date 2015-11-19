package interactive.analyzer.listeners;

/**
 * Listener notified that stack slice was changed
 *
 * @author Drimal
 */
public interface StackSliceChangedListener {

    //TODO zvazit posilani obrazku kvuli ROIs
    public void movingSliceAboutAmount(int amount);

    public void moveToSpecificPosition(int currentPosition);

    public void sliceAtPositionDeleted(int position);

    public void currentStackIndex(int index);
}
