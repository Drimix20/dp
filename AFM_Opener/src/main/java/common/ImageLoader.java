package common;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.*;
import java.util.*;
import metadata.decoder.ChannelMetadata;
import selector.ChannelContainer;

/**
 * @author Drimal
 */
public class ImageLoader implements NewInterface {

    private List<ImagePlus> images;
    private boolean makeStack;

    @Override
    public List<ChannelContainer> loadImages(List<ChannelContainer> channels) {
        for (int i = 0; i < channels.size(); i++) {
            ChannelContainer currentChannelContainer = channels.get(i);
            File file = currentChannelContainer.getFile();
            currentChannelContainer.setImagePlus(loadChannelOfSpecificPossition(file, currentChannelContainer.getChannelIndex()));
        }

        return channels;
    }

    private ImagePlus loadChannelOfSpecificPossition(File file, int channelIndex) {
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

        return loadImage(fi, channelIndex);
    }

    @Override
    public List<ChannelContainer> loadImages(Map<File, List<Integer>> channelMap) {
        List<ChannelContainer> channelContainers = new ArrayList<ChannelContainer>();
        images = new ArrayList<ImagePlus>();
        IJ.showStatus("Loading image...");
        int currentIndex = 1;

        for (Map.Entry<File, List<Integer>> entry : channelMap.entrySet()) {
            File file = entry.getKey();

            List<Integer> list = entry.getValue();

            MetadataLoader metadataLoader = new MetadataLoader();
            List<ChannelMetadata> parsedMetadata = metadataLoader.parseMetadata(file);
            channelContainers.addAll(mapMetadataToChannelContainer(file, parsedMetadata));

            FileInfo[] fi = retrieveFiloInfos(file);
            //TODO zbytecny metadata loader, akorat bych mel pridat ImagePlus do specifickeho ChannelContaineru
            for (Integer integer : list) {
                ImagePlus img = loadImage(fi, integer);
                ChannelContainer channelCont = channelContainers.get(integer);
                channelCont.setImagePlus(img);
                images.add(img);
            }
            IJ.showProgress(currentIndex, channelMap.size());
            currentIndex++;
        }

        return channelContainers;
    }

    private List<ChannelContainer> mapMetadataToChannelContainer(File file, List<ChannelMetadata> metadata) {
        List<ChannelContainer> container = new ArrayList<ChannelContainer>();
        for (int i = 0; i < metadata.size(); i++) {
            ChannelContainer channel = new ChannelContainer(file, i, metadata.get(i));
            container.add(channel);
        }
        return container;
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
        FileInfo fiForLoad = infos[index];
        ij.io.FileOpener fo = new ij.io.FileOpener(fiForLoad);
        ImagePlus imp = fo.open(false);

        return imp;
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
