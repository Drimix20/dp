
import fiji.Debug;
import gui.PluginFrame;
import ij.IJ;
import ij.ImageJ;
import ij.plugin.PlugIn;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Drimi
 */
public class Gui_Plugin implements PlugIn {

    public static void main(String[] args) {

            // set the plugins.dir property to make the plugin appear in the Plugins menu
            Class<?> clazz = Gui_Plugin.class;
            String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
            String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
            System.setProperty("plugins.dir", pluginsDir);

            // start ImageJ
            new ImageJ();

            // open the Clown sample
            //ImagePlus image = IJ.openImage("http://imagej.net/images/clown.jpg");
            //image.show();

            // run the plugin
            IJ.runPlugIn(clazz.getName(), "");
    }

    public void run(String arg) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PluginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PluginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PluginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PluginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PluginFrame().setVisible(true);
            }
        });
    }
}
