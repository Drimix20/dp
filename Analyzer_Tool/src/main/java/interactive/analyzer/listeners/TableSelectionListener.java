package interactive.analyzer.listeners;

/**
 *
 * @author Drimal
 */
public interface TableSelectionListener {

    public void selectedRowIndexIsChanged(int rowIndex, double value);

//    public void selectedRowIndexIsChanged(int rowIndex);
    public void clearAllSelections();
}
