
import ij.IJ;
import ij.ImageJ;
import ij.ImageStack;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.OpenDialog;
import ij.io.TiffDecoder;
import ij.plugin.FileInfoVirtualStack;
import ij.plugin.PlugIn;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Hello world!
 *
 */
public class Test_Plugin implements PlugIn
{
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
            IJ.runPlugIn(clazz.getName(), "");
    }

    public void run(String arg) {
        OpenDialog od = new OpenDialog("Open Image File..", arg);
        String dir = od.getDirectory();
        String name = od.getFileName();
        TiffDecoder td = new TiffDecoder(dir, name);
        FileInfo[] fi = new FileInfo[1];
        try {
             fi = td.getTiffInfo();
        } catch (IOException ex) {
            IJ.log(ex.getMessage());
            return;
        }
        JpkFileOpener jfo = new JpkFileOpener();
        ImagePlus img = jfo.openImage(fi[1]);
        img.show();
//        try {
//            OpenDialog od = new OpenDialog("Open Image File...", arg);
//            String dir = od.getDirectory();
//            String name = od.getFileName();
//            TiffDecoder td = new TiffDecoder(dir, name);
//            FileInfo[] fi = td.getTiffInfo();
//            System.out.println(fi[0].info);
//            FileInfoVirtualStack vs = new FileInfoVirtualStack(fi[0], true);
//            System.out.println("Virtual stack size: "+vs.getSize());
//            ImageStack stack = new ImageStack(vs.getWidth(), vs.getHeight());
//            for (int i=0; i<vs.getSize(); i++){
//                stack.addSlice(vs.getSliceLabel(i), vs.getProcessor(i));
//            }
//            ImagePlus img = new ImagePlus("stack", stack);
//            img.show();
//        } catch (IOException ex) {
//            Logger.getLogger(Test_Plugin.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
    }
}
