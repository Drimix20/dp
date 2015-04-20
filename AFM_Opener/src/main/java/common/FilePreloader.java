package common;

import ij.IJ;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metadata.decoder.ChannelMetadata;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class FilePreloader {

    private List<ChannelContainer> channelContainer = new ArrayList<ChannelContainer>();
    /* Start of wrapper */

    public Map<File, List<Integer>> preprocess(File parent) {
        Map<File, List<Integer>> map;

        if (parent.isDirectory()) {
            map = processDirectory(parent);
        } else {
            map = processSimpleFile(parent);
        }

        return map;
    }

    private Map<File, List<Integer>> processDirectory(File dir) {
        Map<File, List<Integer>> map = new HashMap<File, List<Integer>>();
        File[] dirFiles = dir.listFiles();
        for (int i = 0; i < dirFiles.length; i++) {
            File dirFile = dirFiles[i];
            List<Integer> allChannelsIndexesList = retrieveImageChannelIndexes(dirFile);
            if (!allChannelsIndexesList.isEmpty()) {
                map.put(dirFile, allChannelsIndexesList);
            }
        }

        return map;
    }

    private Map<File, List<Integer>> processSimpleFile(File file) {
        Map<File, List<Integer>> map = new HashMap<File, List<Integer>>();

        channelContainer = preloadJpkImageFiles(file);
        List<Integer> allChannelIndexes = new ArrayList<Integer>();
        for (int i = 0; i < channelContainer.size(); i++) {
            allChannelIndexes.add(i);
        }
        map.put(file, allChannelIndexes);

        return map;
    }

    public List<ChannelContainer> getChannelContainer() {
        return channelContainer;
    }

    /**
     * Method retrieve indexes for image channels saved in specific file
     *
     * @param file
     * @return indexes of channels
     */
    private List<Integer> retrieveImageChannelIndexes(File file) {
        List<Integer> slices = new ArrayList<Integer>();

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

        if (fi == null) {
            IJ.error("AFM Opener", "Unsupported file: \n" + name);
            return Collections.emptyList();
        }

        if (fi.length != 1) {
            for (int i = 1; i < fi.length; i++) {
                slices.add(i);
            }
        }
        return slices;
    }

    /* End of wrapper */
    public List<ChannelContainer> preloadJpkImageFiles(File parent) {
        FileFilter fileFilter = new JkpFileFilter();
        return preprocessToChannelContainer(parent, fileFilter);
    }

    private List<ChannelContainer> preprocessToChannelContainer(File file, FileFilter filter) {
        List<ChannelContainer> container = new ArrayList<ChannelContainer>();

        File[] filesToPreprocess = null;
        if (file.isDirectory()) {
            filesToPreprocess = file.listFiles(filter);
        } else {
            filesToPreprocess = new File[]{file};
        }

        for (int i = 0; i < filesToPreprocess.length; i++) {
            File processingFile = filesToPreprocess[i];
            MetadataLoader metadataLoader = new MetadataLoader();
            List<ChannelMetadata> parseMetadata = metadataLoader.parseMetadata(processingFile);
            container.addAll(mapMetadataToChannelContainerList(processingFile, parseMetadata));
        }

        return container;
    }

    private List<ChannelContainer> mapMetadataToChannelContainerList(File file, List<ChannelMetadata> metadata) {
        List<ChannelContainer> container = new ArrayList<ChannelContainer>();
        for (int i = 0; i < metadata.size(); i++) {
            ChannelContainer channel = new ChannelContainer(file, i, metadata.get(i));
            container.add(channel);
        }
        return container;
    }

    private static class JkpFileFilter implements FileFilter {

        public JkpFileFilter() {
        }

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().contains(".jpk");
        }
    }
}
