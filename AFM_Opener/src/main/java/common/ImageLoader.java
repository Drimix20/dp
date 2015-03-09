package common;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import ij.measure.Calibration;
import java.io.*;
import java.util.*;

/**
 * @author Drimal
 */
public class ImageLoader {

    private List<ImagePlus> images;
    private boolean makeStack;

    //load selected images
    public void loadImages(Map<File, List<Integer>> channelMap) {
        images = new ArrayList<ImagePlus>();
        IJ.showStatus("Loading image...");
        int currentIndex = 1;

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            File file = entry.getKey();
            List<Integer> list = entry.getValue();

            FileInfo[] fi = retrieveFiloInfos(file);

            for (Integer integer : list) {
                ImagePlus img = loadImage(fi, integer, false);
                images.add(img);
            }
            IJ.showProgress(currentIndex, channelMap.size());
            currentIndex++;
        }
    }

    public void show() {
        if (makeStack) {
            ImagePlus imagesConvertedToStack = convertImagesToStack();
            if (imagesConvertedToStack != null) {
                imagesConvertedToStack.show();
            }
        } else {
            showImages();
        }
    }

    private void showImages() {
        for (int i = 0; i < images.size(); i++) {
            images.get(i).show();
        }
    }

    public void makeStack(boolean make) {
        makeStack = make;
    }

    private FileInfo[] retrieveFiloInfos(File file) {
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
        return fi;
    }

    private ImagePlus loadImage(FileInfo[] infos, int index, boolean show) {
        ij.io.FileOpener fo = new ij.io.FileOpener(infos[0]);
        TiffDecoder decoder = new TiffDecoder(infos[0].directory, infos[0].fileName);
        decoder.enableDebugging();
        FileInfo[] info = new FileInfo[1];
        try {
            info = decoder.getTiffInfo();
        } catch (Exception e) {
        }

        ImagePlus imp1 = fo.open(false);
        Calibration cal = imp1.getCalibration();
        System.out.println("Type" + imp1.getType());

        fo = new ij.io.FileOpener(infos[index]);
        ImagePlus imp2 = fo.open(show);

        return imp2;
    }

    private ImagePlus[] convertToArray(List<ImagePlus> imageList) {
        return imageList.toArray(new ImagePlus[imageList.size()]);
    }

    private ImagePlus convertImagesToStack() {
        if (images.isEmpty()) {
            return null;
        }

        ImagesToStackConverter converter = new ImagesToStackConverter();
        ImagePlus[] imageArray = convertToArray(images);
        converter.convertImagesToStack(imageArray);

        return converter.getConvertedStack();
    }
}
