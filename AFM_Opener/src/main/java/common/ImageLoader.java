package common;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
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
                ImagePlus img = loadImage(fi, integer);
                images.add(img);
            }
            IJ.showProgress(currentIndex, channelMap.size());
            currentIndex++;
        }
    }

    public void showLoadedImages() {
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

    private ImagePlus loadImage(FileInfo[] infos, int index) {
        String debugInfoForAllFileInfos = infos[0].debugInfo;
        String debugInfoByIndex = debugInfoForAllFileInfos.split("nextIFD=\\d+")[index];

        FileInfo fiForLoad = infos[index];
        fiForLoad.debugInfo = debugInfoByIndex;
        ij.io.FileOpener fo = new ij.io.FileOpener(fiForLoad);
        ImagePlus imp2 = fo.open(false);

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
