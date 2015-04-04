package amf.analyzer.other;


import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Drimal
 */
public class Volume implements PlugIn {
    public ImageProcessor ip;
    public int totalVolume;
    public void run(String arg) {
        ImagePlus img = IJ.getImage();
        ip = img.getProcessor();
        ip.setAutoThreshold("Triangle");
        img.updateAndDraw();
        volume();
    }

    void volume() {
        if (ip != null) {
            int num = ip.getPixelCount();
            for (int i = 0; i < num; ++i) {
                if (ip.get(i) >= ip.getMinThreshold()) {
                    totalVolume += ip.get(i);
                }
            }
            System.out.println("Total volume: "+totalVolume);
        }
    }
    
    public static void main(String[] args) {
        //TODO progres bar, name stack
            // set the plugins.dir property to make the plugin appear in the Plugins menu
            Class<?> clazz = Volume.class;
            String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
            String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);
            System.setProperty("plugins.dir", pluginsDir);

            // start ImageJ
            new ImageJ();

            // run the plugin
            IJ.runPlugIn(clazz.getName(), "");
    }

}
