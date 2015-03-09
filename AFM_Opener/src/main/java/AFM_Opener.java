
import gui.AfmOpenerFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;
import javax.swing.SwingUtilities;

/**
 * Main class serve to run plugin in Fiji menu
 *
 * @author Drimal
 */
public class AFM_Opener implements PlugIn {

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
