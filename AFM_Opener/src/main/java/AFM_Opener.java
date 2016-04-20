
import afm.opener.gui.AfmOpenerFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import afm.opener.selector.ChannelContainer;
import org.apache.log4j.Logger;

/**
 * Main class serve to run plugin in Fiji menu
 *
 * @author Drimal
 */
public class AFM_Opener implements PlugIn {

    private static final Logger logger = Logger.getLogger(AFM_Opener.class);
    private CountDownLatch latch;
    private boolean disposeAfterOpen;
    private AfmOpenerRunnable afmOpenerRunnable;
    private boolean showImages;

    public AFM_Opener() {
        latch = new CountDownLatch(1);
        disposeAfterOpen = false;
        showImages = true;
    }

    public AFM_Opener(CountDownLatch latch, boolean disposeAfterOpen) {
        super();
        this.latch = latch;
        this.disposeAfterOpen = disposeAfterOpen;
        showImages = true;
    }

    public void showImages(boolean enabled) {
        showImages = enabled;
    }

    public List<ChannelContainer> getSelectedContainer() {
        return afmOpenerRunnable.getSelectedChannels();
    }

    @Override
    public void run(String arg) {
        logger.info("Running AFM_Opener plugin");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                AfmOpenerFrame frame = new AfmOpenerFrame();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Execution method used to call plugin in other plugin
     */
    public void execute() {
        afmOpenerRunnable = new AfmOpenerRunnable(latch, disposeAfterOpen, showImages);
        SwingUtilities.invokeLater(afmOpenerRunnable);
    }

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = AFM_Opener.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
