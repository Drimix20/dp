
import ij.*;
import ij.gui.Roi;
import ij.macro.Interpreter;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import java.awt.Frame;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Length;
import static javax.measure.unit.SI.METRE;
import static javax.measure.unit.SI.MetricPrefix.NANO;
import javax.measure.unit.Unit;

/**
 *
 * @author Drimi
 */
public class Test_Plugin implements PlugInFilter {

    private ImagePlus imp;

    public static void main(String[] args) {

        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = Test_Plugin.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // open the Clown sample
        //ImagePlus image = IJ.openImage("http://imagej.net/images/clown.jpg");
        //image.show();
        // run the plugin
        IJ.openImage("C:\\Users\\Drimal\\Downloads\\testData\\Mask of blobs.gif").show();
        IJ.runPlugIn(clazz.getName(), "");
    }

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        ImagePlus imp2 = IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif");
        ImagePlus imp3 = IJ.openImage("C:\\Users\\Drimal\\Downloads\\testData\\Cell_Colony.jpg");

        ImageStack stack = new ImageStack(imp2.getWidth(), imp2.getHeight());
        stack.addSlice("Blobs", imp2.getProcessor());
        stack.addSlice("Cell_colony", imp3.getProcessor());
        new ImagePlus("stack", stack).show();

        ResultsTable resultsTable = new ResultsTable();
        //create new instance of my roiManager and set to don't show window
        RoiManager roiManager = new RoiManager(true);
        ParticleAnalyzer analyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_NONE, Measurements.ADD_TO_OVERLAY | Measurements.RECT, resultsTable,
                0, Double.POSITIVE_INFINITY, 0, 1);
        ParticleAnalyzer.setRoiManager(roiManager);
        ParticleAnalyzer.setLineWidth(1);
        analyzer.analyze(imp, ip);

        resultsTable.show("Results");
        int numberOfRows = resultsTable.size();
        String[] headings = resultsTable.getHeadings();
        int bxIndex = resultsTable.getColumnIndex(headings[0]);
        int byIndex = resultsTable.getColumnIndex(headings[1]);
        int widthIndex = resultsTable.getColumnIndex(headings[2]);
        int heightIndex = resultsTable.getColumnIndex(headings[3]);
        for (int i = 0; i < numberOfRows; i++) {
            System.out.println(
                    "id=" + (i + 1)
                    + ", bx=" + resultsTable.getValueAsDouble(bxIndex, i)
                    + ", by=" + resultsTable.getValueAsDouble(byIndex, i)
                    + ", width=" + resultsTable.getValueAsDouble(widthIndex, i)
                    + ", height=" + resultsTable.getValueAsDouble(heightIndex, i)
            );
        }

        Roi[] roisAsArray = roiManager.getRoisAsArray();
        System.out.println("RoisAsArray.size: " + roisAsArray.length);

        int[] selectedIndexes = new int[roisAsArray.length];
        for (int i = 0; i < roisAsArray.length; i++) {
            selectedIndexes[i] = i;
        }
        roiManager.setSelectedIndexes(selectedIndexes);
        roiManager.runCommand("Delete");
        for (int i = 0; i < roisAsArray.length; i++) {
            ExtendedRoi extRoi = new ExtendedRoi(roisAsArray[i].getPolygon(), Roi.TRACED_ROI);
            extRoi.setLabel(i + 1);
            roiManager.addRoi(extRoi);
        }

//        ExtendedRoi extRoi = new ExtendedRoi(roisAsArray[0].getPolygon(), Roi.TRACED_ROI);
//        roiManager.select(0);
//        roiManager.runCommand("Delete");
//        roiManager.addRoi(extRoi);
        Unit<Length> unit = METRE;
        Unit<Length> target = NANO(METRE);
        UnitConverter converter = unit.getConverterTo(target);
        double result = converter.convert(1.0);
        System.out.println("From 1 meter to nanometer:" + result);

        converter = target.getConverterTo(unit);
        result = converter.convert(1.0);
        System.out.println("From 1 nanometer to meter:" + result);

    }

    private RoiManager instantiateRoiManager() {
        RoiManager manager = null;
        if (Macro.getOptions() != null && Interpreter.isBatchMode()) {
            manager = Interpreter.getBatchModeRoiManager();
        }
        if (manager == null) {
            Frame frame = WindowManager.getFrame("ROI Manager");
            if (frame == null) {
                IJ.run("ROI Manager...");
            }
            frame = WindowManager.getFrame("ROI Manager");
            if (frame == null || !(frame instanceof RoiManager)) {
                return null;
            }
            manager = (RoiManager) frame;
        }
        return manager;
    }

}
