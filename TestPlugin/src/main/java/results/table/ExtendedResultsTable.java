package results.table;

import ij.IJ;
import ij.WindowManager;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.text.TextPanel;
import ij.text.TextWindow;
import java.awt.Frame;

/**
 *
 * @author Drimal
 */
public class ExtendedResultsTable extends ResultsTable {

    //TODO needs of handler
    public ExtendedResultsTable() {
        super();
        IJ.setTextPanel(new ExtendedTextPanel());
    }

    @Override
    public void show(String windowTitle) {
        if (!windowTitle.equals("Results") && this == Analyzer.getResultsTable()) {
            IJ.log("ResultsTable.show(): the system ResultTable should only be displayed in the \"Results\" window.");
        }
        String tableHeadings = getColumnHeadings();
        TextPanel tp;
        boolean newWindow = false;
        boolean cloneNeeded = false;
        if (windowTitle.equals("Results")) {
            tp = IJ.getTextPanel();
            if (tp == null) {
                return;
            }
            newWindow = tp.getLineCount() == 0;
            if (!newWindow && tp.getLineCount() == size() - 1 && ResultsTable.getResultsTable() == this
                    && tp.getColumnHeadings().equals(tableHeadings)) {
                String s = getRowAsString(size() - 1);
                tp.append(s);
                return;
            }
            IJ.setColumnHeadings(tableHeadings);
            if (this != Analyzer.getResultsTable()) {
                Analyzer.setResultsTable(this);
            }
            if (size() > 0) {
                Analyzer.setUnsavedMeasurements(true);
            }
        } else {
            Frame frame = WindowManager.getFrame(windowTitle);
            TextWindow win;
            if (frame != null && frame instanceof TextWindow) {
                win = (TextWindow) frame;
                win.toFront();
            } else {
                int width = getLastColumn() <= 1 ? 250 : 400;
                win = new ExtendedTextWindow(windowTitle, "", width, 300);
                cloneNeeded = true;
            }
            tp = win.getTextPanel();
            tp.setColumnHeadings(tableHeadings);
            newWindow = tp.getLineCount() == 0;
        }
        tp.setResultsTable(cloneNeeded ? (ResultsTable) this.clone() : this);
        int n = size();
        if (n > 0) {
            if (tp.getLineCount() > 0) {
                tp.clear();
            }
            for (int i = 0; i < n; i++) {
                tp.appendWithoutUpdate(getRowAsString(i));
            }
            tp.updateDisplay();
        }
        if (newWindow) {
            tp.scrollToTop();
        }
    }

}
