
import gui.AfmOpenerFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import selector.ChannelContainer;

/**
 * Main class serve to run plugin in Fiji menu
 *
 * @author Drimal
 */
public class AFM_Opener implements PlugIn {

    private CountDownLatch latch;
    private boolean disposeAfterOpen;
    private AfmOpenerRunnable afmOpenerRunnable;

    public AFM_Opener() {
        latch = new CountDownLatch(1);
        disposeAfterOpen = false;
    }

    public AFM_Opener(CountDownLatch latch, boolean disposeAfterOpen) {
        super();
        this.latch = latch;
        this.disposeAfterOpen = disposeAfterOpen;
    }

    public List<ChannelContainer> getSelectedContainer() {
        return afmOpenerRunnable.getSelectedChannels();
    }

    public void run(String arg) {
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
     *
     * @param disposeAfterOpen
     * @return
     */
    public void exec(boolean disposeAfterOpen) {
        afmOpenerRunnable = new AfmOpenerRunnable(latch, true);
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
