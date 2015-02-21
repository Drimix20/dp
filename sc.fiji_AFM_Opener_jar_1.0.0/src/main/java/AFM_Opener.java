
import gui.OpenerFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;

/**
 *
 * @author Drimal
 */
public class AFM_Opener implements PlugIn {

    public void run(String arg) {
        OpenerFrame frame = new OpenerFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
