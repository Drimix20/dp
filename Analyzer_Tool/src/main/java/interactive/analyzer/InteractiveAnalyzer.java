package interactive.analyzer;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.frame.RoiManager;
import interactive.analyzer.gui.InteractiveAnalyzerResultFrame;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.presenter.InteractiveImageWindow;
import interactive.analyzer.presenter.Roi;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.result.table.TableUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class InteractiveAnalyzer {

    private static Logger logger = Logger.getLogger(InteractiveAnalyzer.class);
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

        List<Roi> rois = new ArrayList<Roi>();
        for (ij.gui.Roi r : roiManager.getRoisAsArray()) {
            rois.add(new Roi(parseRoiLabel(r.getName()), r.getPolygon(), Color.CYAN, false));
        }

        imageWindow = new InteractiveImageWindow(img, rois);

        AfmAnalyzerTableModel tableModel = TableUtils.convertResultTableToInteractiveResultTable(ijResultTable);
        resultFrame = new InteractiveAnalyzerResultFrame(imageWindow, Collections.EMPTY_LIST, tableModel);
        resultFrame.addTableSelectionListener((TableSelectionListener) imageWindow);
        resultFrame.setLocationRelativeTo(null);
        imageWindow.addRoiSelectedListener((ImageSelectionListener) resultFrame);
    }

    private int parseRoiLabel(String roiName) {
        logger.trace("Parse roi name: " + roiName);
        String roiLabeString = roiName.split("-")[0];
        return Integer.parseInt(roiLabeString);
    }

    public void run() {
        if (!imageWindow.isVisible()) {
            imageWindow.setImageVisible(true);
        }
        if (!resultFrame.isVisible()) {
            resultFrame.setVisible(true);
        }
    }

}
