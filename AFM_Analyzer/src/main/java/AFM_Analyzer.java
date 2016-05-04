
import afm.analyzer.gui.AfmAnalyzerFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import afm.opener.selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class AFM_Analyzer implements PlugIn {

    private Logger logger = Logger.getLogger(AFM_Analyzer.class);
    private List<ChannelContainer> selectedChannelContainer;

    @Override
    public void run(String arg) {
        logger.info("Running plugin AFM_Analyzer");
        final CountDownLatch latch = new CountDownLatch(1);
        AFM_Opener openerPlugin = new AFM_Opener(latch, true);
        openerPlugin.showImages(false);
        openerPlugin.execute();

        try {
            latch.await();
            selectedChannelContainer = openerPlugin.getSelectedContainer();
        } catch (Exception ex) {
            logger.error(ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    AfmAnalyzerFrame analyzer = new AfmAnalyzerFrame();
                    analyzer.setChannels(selectedChannelContainer);
                    analyzer.setLocationRelativeTo(null);
                    analyzer.setVisible(true);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = AFM_Analyzer.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }

    //TODO po zavreni obrazku v ImageJ dojde ke ztrate informace
    //TODO otevirani stacku - rozdilna dimense apod
}
