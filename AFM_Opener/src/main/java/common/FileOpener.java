package common;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import ij.measure.Calibration;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Drimal
 */
public class FileOpener {
    private List<ImagePlus> images;
    private int stackType;
    
    private static final int rgb = 33;
    private static final int COPY_CENTER = 0, COPY_TOP_LEFT = 1, SCALE_SMALL = 2, SCALE_LARGE = 3;
    private static int method = COPY_CENTER;
    private static boolean bicubic;
    private static boolean keep;
    private Calibration cal2;
    private String name = "Stack";

    public ImageStack openFiles(Map<File, List<Integer>> map){
        images = new ArrayList<ImagePlus>();
        IJ.showStatus("Loading image...");
        int currentIndex=1;
                
        for (Map.Entry<File, List<Integer>> entry : map.entrySet()) {
            File file = entry.getKey();
            List<Integer> list = entry.getValue();
            
            FileInfo[] fi = retrieveFiloInfos(file);
            
            for(Integer integer : list){
                ImagePlus img = loadImage(fi, integer, false);
                images.add(img);
                img.show();                
            }
            IJ.showProgress(currentIndex, map.size());
            currentIndex++;
        }
        
        //convertImagesToStack();
        return null;
    }

    /**
     * Initialization of image stack. Set of width and height
     * @param fi array of file infos
     * @param index index of image used to settings
     * @return new instance of image stack
     */
    private ImageStack initImageStack(FileInfo[] fi, int index) {
        ImageProcessor ip = loadImage(fi,index, false).getProcessor();        
        return new ImageStack(ip.getWidth(), ip.getHeight());
    }
    
    private FileInfo[] retrieveFiloInfos(File file){
        String dir = file.getParent();
        String name = file.getName();
        TiffDecoder td = new TiffDecoder(dir, name);
        td.enableDebugging();
        FileInfo[] fi = new FileInfo[1];
        
        try {
            fi = td.getTiffInfo();
        } catch (IOException ex) {
            IJ.log(ex.getMessage());
            return null;
        }
        String str = fi[0].debugInfo;
        return fi;
    }
    
    private ImagePlus loadImage(FileInfo[] infos, int index, boolean show){
        ij.io.FileOpener fo = new ij.io.FileOpener(infos[0]);
        TiffDecoder decoder = new TiffDecoder(infos[0].directory, infos[0].fileName);
        decoder.enableDebugging();
        FileInfo[] info = new FileInfo[1];
        try{
            info = decoder.getTiffInfo();
        }catch(Exception e){}

        ImagePlus imp1 = fo.open(false);
        Calibration cal = imp1.getCalibration();
        System.out.println("Type"+imp1.getType());
        
        fo = new ij.io.FileOpener(infos[index]);
        ImagePlus imp2 = fo.open(show);

        return imp2;
    }
    
    private ImagePlus[] convertToArray(List<ImagePlus> imageList){
        return imageList.toArray(new ImagePlus[imageList.size()]);
    }
    
    private void convertImagesToStack(){
        ImagePlus[] image = convertToArray(images);
        int count = image.length;
        int width = image[0].getWidth();
        int height = image[0].getHeight();
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        ImageStack stack = new ImageStack(width, height);
        FileInfo fi = image[0].getOriginalFileInfo();
        if (fi != null && fi.directory == null) {
            fi = null;
        }
        for (int i = 0; i < count; i++) {
            ImageProcessor ip = image[i].getProcessor();
            if (ip == null) {
                break;
            }
            if (ip.getMin() < min) {
                min = ip.getMin();
            }
            if (ip.getMax() > max) {
                max = ip.getMax();
            }
            String label = image[i].getTitle();
            if (label != null) {
                String info = (String) image[i].getProperty("Info");
                if (info != null) {
                    label += "\n" + info;
                }
            }
            if (fi != null) {
                FileInfo fi2 = image[i].getOriginalFileInfo();
                if (fi2 != null && !fi.directory.equals(fi2.directory)) {
                    fi = null;
                }
            }
            switch (stackType) {
                case 16:
                    ip = ip.convertToShort(false);
                    break;
                case 32:
                    ip = ip.convertToFloat();
                    break;
                case rgb:
                    ip = ip.convertToRGB();
                    break;
                default:
                    break;
            }
            if (ip.getWidth() != width || ip.getHeight() != height) {
                switch (method) {
                    case COPY_TOP_LEFT:
                    case COPY_CENTER:
                        ImageProcessor ip2 = null;
                        switch (stackType) {
                            case 8:
                                ip2 = new ByteProcessor(width, height);
                                break;
                            case 16:
                                ip2 = new ShortProcessor(width, height);
                                break;
                            case 32:
                                ip2 = new FloatProcessor(width, height);
                                break;
                            case rgb:
                                ip2 = new ColorProcessor(width, height);
                                break;
                        }
                        int xoff = 0,
                         yoff = 0;
                        if (method == COPY_CENTER) {
                            xoff = (width - ip.getWidth()) / 2;
                            yoff = (height - ip.getHeight()) / 2;
                        }
                        ip2.insert(ip, xoff, yoff);
                        ip = ip2;
                        break;
                    case SCALE_SMALL:
                    case SCALE_LARGE:
                        ip.setInterpolationMethod((bicubic ? ImageProcessor.BICUBIC : ImageProcessor.BILINEAR));
                        ip.resetRoi();
                        ip = ip.resize(width, height);
                        break;
                }
            } else if (keep) {
                ip = ip.duplicate();
            }
            stack.addSlice(label, ip);
            if (!keep) {
                image[i].changes = false;
                image[i].close();
            }
        }
        if (stack.getSize() == 0) {
            return;
        }
        ImagePlus imp = new ImagePlus(name, stack);
        if (stackType == 16 || stackType == 32) {
            imp.getProcessor().setMinAndMax(min, max);
        }
        if (cal2 != null) {
            imp.setCalibration(cal2);
        }
        if (fi != null) {
            fi.fileName = "";
            fi.nImages = imp.getStackSize();
            imp.setFileInfo(fi);
        }
        imp.show();
    }

}
