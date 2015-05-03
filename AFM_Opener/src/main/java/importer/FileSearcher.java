package importer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import metadata.decoder.ChannelMetadata;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class FileSearcher {

    private List<ChannelContainer> channelContainer = new ArrayList<ChannelContainer>();

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
            channel.setGridULength((Double) metadata.get(0).getTagValue(32834));
            channel.setGridVLength((double) metadata.get(0).getTagValue(32835));
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

    private double parseStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return 1.0;
        }
    }
}
