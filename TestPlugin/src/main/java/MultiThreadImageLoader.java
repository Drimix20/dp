
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.FileOpener;
import ij.io.TiffDecoder;
import ij.plugin.PlugIn;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFileChooser;

/**
 *
 * @author Drimal
 */
public class MultiThreadImageLoader implements PlugIn{
    File[] selectedFiles;

    public void run(String arg) {
        final ImagePlus[] images = new ImagePlus[8];
        
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setCurrentDirectory(getCurrentDir());
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFiles = chooser.getSelectedFiles();
        }
        final Thread[] threads = newThreadArray(selectedFiles.length);
        final AtomicInteger ai = new AtomicInteger(-1);

        for(int iThread=0; iThread < threads.length; iThread++)
        {
            threads[iThread] = new Thread() {
                { setPriority(Thread.NORM_PRIORITY); }
                
                public void run(){
                    int pos = ai.getAndIncrement();
                    File file = selectedFiles[pos];
                    String dir = file.getParent();
                    String name = file.getName();
                    TiffDecoder td = new TiffDecoder(dir, name);
                    FileOpener fo = new FileOpener(null);
                    images[pos]=fo.open(false);
                }
            };
        }
        
        for (int i = 0; i < selectedFiles.length; i++) {
            images[i].show();
        }
    }

    public static void main(String[] args) {

            // set the plugins.dir property to make the plugin appear in the Plugins menu
            Class<?> clazz = MultiThreadImageLoader.class;
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

    private File getCurrentDir(){
        File file = null;
        if(new File("C:\\Users\\Drimal\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data").exists()){
            file = new File("C:\\Users\\Drimal\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data");
        }else{
            if (new File("C:\\Skola\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data").exists()){
                file = new File("C:\\Skola\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data");
            }else{
                file = new File("E:\\Dropbox\\DP\\2014_Martin_Drimal_-_AFM\\data");
            }
        }
        return file;
    }
    
    /** Create a Thread[] array as large as the number of processors available. 
    * From Stephan Preibisch's Multithreading.java class. See: 
    * http://repo.or.cz/w/trakem2.git?a=blob;f=mpi/fruitfly/general/MultiThreading.java;hb=HEAD 
    */  
    private Thread[] newThreadArray(int count) {   
        return new Thread[count];  
    } 
}
