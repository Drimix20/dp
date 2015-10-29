package exporter;

import configuration.PluginConfiguration;
import writer.ImageTagsWriter;
import writer.CsvImageTagsWriter;
import configuration.xml.elements.TagConfiguration;
import ij.IJ;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;
import selector.ChannelContainer;
import writer.tags.sorter.CategoryTagsDescriptionSorter;
import writer.tags.sorter.DecimalIDTagsDescriptionSorter;

/**
 *
 * @author Drimal
 */
public class ImageTagsExporter implements TagsExporter {

    private static Logger logger = Logger.getLogger(ImageTagsExporter.class);

    @Override
    public void exportImageTags(List<ChannelContainer> channels,
            File currentDirectory) {
        logger.info("Exporting tags");
        JFileChooser fileChooser = new ExportedTagsSaveFileChooser(currentDirectory);

        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logger.debug("Selected file to save is " + file.getAbsolutePath());

            File tagsDescriptionFile = retrieveFilePathOfTagsDescriptionFile();
            List<TagConfiguration> tagsConfig = PluginConfiguration.getTagConfigurationList();
            logger.debug("Number of tags from xml: " + tagsConfig.size());

            FileFilter selectedFilter = fileChooser.getFileFilter();
            ImageTagsWriter tagsWriter = getWriterInstance(selectedFilter);
            tagsWriter.addTagsDescriptionSorter(new CategoryTagsDescriptionSorter());
            tagsWriter.addTagsDescriptionSorter(new DecimalIDTagsDescriptionSorter());
            if (tagsWriter != null) {
                tagsWriter.setTagsDescription(tagsConfig);
                tagsWriter.dumpTagsIntoFile(channels, file.getParent(), file.getName());
            }
        }
    }

    protected ImageTagsWriter getWriterInstance(FileFilter filter) {
        if (filter.getDescription().contains(".csv")) {
            return new CsvImageTagsWriter();
        }

        return null;
    }

    //TODO move into PluginConfiguration class
    private File retrieveFilePathOfTagsDescriptionFile() {
        String directory = IJ.getDirectory("plugins");
        return new File(directory + File.separator + PluginConfiguration.PLUGIN_CONFIGURATION_XML_NAME);
    }

}
