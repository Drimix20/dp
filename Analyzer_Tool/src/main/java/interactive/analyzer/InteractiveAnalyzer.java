package interactive.analyzer;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.frame.RoiManager;
import interactive.analyzer.gui.InteractiveAnalyzerResultFrame;
import interactive.analyzer.listeners.RoiSelectedListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.presenter.InteractiveImageWindow;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.result.table.TableUtils;
import java.util.Collections;

/**
 *
 * @author Drimal
 */
public class InteractiveAnalyzer {

    private RoiManager roiManager;
    private AfmAnalyzerResultTable resultTable;
    private ImageWindowI imageWindow;
    private InteractiveAnalyzerResultFrame resultFrame;

    public InteractiveAnalyzer(ResultsTable ijResultTable, RoiManager roiManager,
            ImagePlus img) {
        if (ijResultTable == null) {
            throw new IllegalArgumentException("Results table can't be null");
        }
        if (roiManager == null) {
            throw new IllegalArgumentException("RoiManager can't be null");
        }
        if (img == null) {
            throw new IllegalArgumentException("Image can't be null");
        }
        AfmAnalyzerTableModel tableModel = TableUtils.convertResultTableToInteractiveResultTable(ijResultTable);
        resultFrame = new InteractiveAnalyzerResultFrame(Collections.EMPTY_LIST, tableModel);

        imageWindow = new InteractiveImageWindow();
        imageWindow.setImagesToShow(img);
        resultFrame.addTableSelectionListener((TableSelectionListener) imageWindow);
        imageWindow.addRoiSelectedListener((RoiSelectedListener) resultFrame);
        //TODO bind roiManager with imageWindow

        this.roiManager = roiManager;
    }

    public InteractiveAnalyzer(InteractiveAnalyzerResultFrame resultFrame,
            ImageWindowI imageWindow) {
        if (resultFrame == null) {
            throw new IllegalArgumentException("Result frame is null");
        }
        if (imageWindow == null) {
            throw new IllegalArgumentException("ImageWindow is null");
        }
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
        //TODO show rois with label in created imageWindow
    }

}
