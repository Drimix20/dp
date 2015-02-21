
import fiji.util.gui.GenericDialogPlus;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.io.FileInfo;
import ij.io.FileOpener;
import ij.io.TiffDecoder;
import ij.plugin.PlugIn;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author Drimal
 */
public class AFM_Opener1 implements PlugIn {

    //private String path = "C:\\Users";//\\Drimal\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data";
    //private String path = "C:\\Users\\Drimal\\Downloads\\zasilka-BAVBA6H57BREPI9T";
    private ImagePlus openFiles(String filePath, boolean makeInvert) {
        File file = new File(filePath);
        ImagePlus imp = null;
        if (file.isDirectory()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    String lowerCaseName = name.toLowerCase();
                    return lowerCaseName.endsWith(".jpk");
                }
            };
            File[] files = file.listFiles(filter);
            imp = new ImagePlus("Images", openJpkDataImages(files));
        } else {
            imp = openJpkDataImage(new File(filePath));
        }
        if (makeInvert) {
            IJ.run(imp, "Invert", "stack");
        }
        return imp;
    }

    private ImageStack openJpkDataImages(File[] files) {
        IJ.showStatus("Loading images...");
        ImageStack stack = openJpkDataImage(files[0]).getImageStack();
        for (int i = 0; i < files.length; i++) {
            IJ.showProgress(i, files.length);
            stack.addSlice(openJpkDataImage(files[i]).getProcessor());

        }
        return stack;
    }

    private ImagePlus openJpkDataImage(File file) {
        IJ.showStatus("Loading image...");
        if(!file.getName().contains(".jpk")){
            IJ.error("No supported jpk file selected!");
        }
        String dir = file.getParent();
        String name = file.getName();
        TiffDecoder td = new TiffDecoder(dir, name);
        FileInfo[] fi = new FileInfo[1];
        
        try {
            fi = td.getTiffInfo();
        } catch (IOException ex) {
            IJ.log(ex.getMessage());
            return null;
        }
        int length = fi.length;
        FileOpener fo = new FileOpener(fi[1]);
        
        ImagePlus imp = fo.open(false);
        Properties props = imp.getProperties();
        Enumeration<?> e = props.propertyNames();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            System.out.println(key);
        } 
        return imp;
    }

    public void run(String arg) {
        GenericDialogPlus gd = new GenericDialogPlus("AFM Opener");
        gd.centerDialog(true);
        gd.addCheckbox("Invert", true);
        gd.addDirectoryOrFileField("Dir or file",null);
        gd.showDialog();
        if (gd.wasCanceled()) {
            return;
        }
        boolean makeInvert = gd.getNextBoolean();
        String filePath = gd.getNextString();
        ImagePlus imp = openFiles(filePath, makeInvert);
        imp.show();
    }

    public static void main(String[] args) {
        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = AFM_Opener1.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
        System.setProperty("plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
