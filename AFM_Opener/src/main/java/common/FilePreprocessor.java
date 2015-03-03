package common;

import ij.IJ;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class FilePreprocessor {

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
        List<Integer> allChannelIndexesList = retrieveImageChannelIndexes(file);
        if (!allChannelIndexesList.isEmpty()) {
            map.put(file, allChannelIndexesList);
        }

        return map;
    }

    /**
     * Method retrieve indexes for image channels saved in specific file
     *
     * @param file
     * @return indexes of channels
     */
    public List<Integer> retrieveImageChannelIndexes(File file) {
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
}
