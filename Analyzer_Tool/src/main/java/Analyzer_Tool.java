
import interactive.analyzer.gui.InteractiveAnalyzerDialog;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.WindowManager;
import ij.plugin.PlugIn;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * This main class serve to run plugin in Fiji menu
 * @author Drimal
 */
public class Analyzer_Tool implements PlugIn {

    private static final Logger logger = Logger.getLogger(Analyzer_Tool.class);

    @Override
    public void run(String arg) {
        logger.info("Running Analyzer_Tool plugin");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                InteractiveAnalyzerDialog dialog = new InteractiveAnalyzerDialog();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = Analyzer_Tool.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        preparationForDevelop();
        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }

    private static void preparationForDevelop() {
        //Import jpk image
        ImagePlus imp = IJ.openVirtual("C:\\Users\\Drimal\\Downloads\\zasilka-CHKRI8DLZPAYS4EY\\thyroglobulin 669 kDa-2013.08.13-13.59.14_height_trace.jpk");
        //Convert stack slices into separated images
        IJ.run(imp, "Stack to Images", "");
        //Retrieve height image
        ImagePlus origin = WindowManager.getImage("thyroglobulin-0002");
        origin.setTitle("thyroglobulin-0002-origin");
        ImagePlus heightImg = origin.duplicate();
        heightImg.show();
        //Threshold height image by triangle method
        IJ.setAutoThreshold(heightImg, "Triangle");
        Prefs.blackBackground = false;
        IJ.run(heightImg, "Convert to Mask", "");
        //Compute default measurements and show results table and roi manager
        IJ.run("Set Measurements...", "area mean center fit feret's redirect=None decimal=0");
        IJ.run(heightImg, "Analyze Particles...", "display add");
        //Compute default measurements and show just results
        //IJ.run(heightImg, "Analyze Particles...", "display");
    }

}
