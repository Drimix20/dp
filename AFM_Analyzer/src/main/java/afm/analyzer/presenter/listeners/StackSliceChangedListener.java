package afm.analyzer.presenter.listeners;

/**
 *
 * @author Drimal
 */
public interface StackSliceChangedListener {

    //TODO zvazit posilani obrazku kvuli ROIs

    public boolean movingSliceAboutAmount(int amount);

    public boolean moveToSpecificPosition(int currentPosition);

    public boolean sliceAtPositionDeleted(int position);
}
