package interactive.analyzer;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.frame.RoiManager;
import interactive.analyzer.gui.AfmAnalyzerResultFrame;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.result.table.TableUtils;

/**
 *
 * @author Drimal
 */
public class InteractiveAnalyzer {

    private RoiManager roiManager;
    private AfmAnalyzerResultTable resultTable;
    private ImageWindowI imageWindow;
    private AfmAnalyzerTableModel tableModel;
    private AfmAnalyzerResultFrame resultFrame;
    private ImagePlus img;

    public InteractiveAnalyzer(ResultsTable ijResultTable, RoiManager roiManager,
            ImagePlus img) {
        AfmAnalyzerTableModel tableModel = TableUtils.convertResultTableToInteractiveResultTable(ijResultTable);
        this.img = img;
    }

    public InteractiveAnalyzer(AfmAnalyzerResultFrame resultFrame,
            ImageWindowI imageWindow) {
        this.resultFrame = resultFrame;
        this.imageWindow = imageWindow;
    }

    public void run() {
        if (!imageWindow.isVisible()) {
            imageWindow.setVisible(true);
        }
        if (!resultFrame.isVisible()) {
            resultFrame.setVisible(true);
        }
    }

}
