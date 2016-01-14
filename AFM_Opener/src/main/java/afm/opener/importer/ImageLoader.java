package afm.opener.importer;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.*;
import java.util.*;
import afm.opener.selector.ChannelContainer;

/**
 * @author Drimal
 */
public class ImageLoader {

    private boolean makeStack;

    public List<ChannelContainer> loadImages(List<ChannelContainer> channels) {
        for (int i = 0; i < channels.size(); i++) {
            ChannelContainer currentChannelContainer = channels.get(i);
            File file = currentChannelContainer.getFile();

            ImagePlus imp = loadChannelOfSpecificPossition(file, currentChannelContainer.getChannelIndex());

            currentChannelContainer.setImagePlus(imp);
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

    private ImagePlus loadImage(FileInfo[] infos, int index) {
        FileInfo fiForLoad = infos[index];
        ij.io.FileOpener fo = new ij.io.FileOpener(fiForLoad);
        ImagePlus imp = fo.open(false);

        return imp;
    }
}