package exporter;

import configuration.PluginConfiguration;
import configuration.parser.TagsDescriptionParser;
import writer.ImageTagsWriter;
import writer.CsvImageTagsWriter;
import configuration.parser.TagsXmlDescriptionParser;
import configuration.xml.elements.TagConfiguration;
import configuration.parser.PluginConfigurationValidator;
import ij.IJ;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    private enum SupportedFileTypes {

        //TODO add new enum type for supported files
        CSV_TYPE("File CSV (.csv)", "csv");

        private String description;
        private String extension;

        private SupportedFileTypes(String description, String extension) {
            this.description = description;
            this.extension = extension;
        }

    }

    @Override
    public void exportImageTags(List<ChannelContainer> channels,
            File currentDirectory) {
        logger.info("Exporting tags");
        JFileChooser fileChooser = initializeFileSaver(currentDirectory);

        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logger.debug("Selected file to save is " + file.getAbsolutePath());

            File tagsDescriptionFile = retrieveFilePathOfTagsDescriptionFile();
            List<TagConfiguration> loadTagsDescription = loadTagsDescription(tagsDescriptionFile);
            logger.debug("Number of tags from xml: " + loadTagsDescription.size());

            FileFilter selectedFilter = fileChooser.getFileFilter();
            ImageTagsWriter tagsWriter = getWriterInstance(selectedFilter);
            tagsWriter.addTagsDescriptionSorter(new CategoryTagsDescriptionSorter());
            tagsWriter.addTagsDescriptionSorter(new DecimalIDTagsDescriptionSorter());
            if (tagsWriter != null) {
                tagsWriter.setTagsDescription(loadTagsDescription);
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

    private List<TagConfiguration> loadTagsDescription(File tagsDescriptionFile) {
        if (!tagsDescriptionFile.exists()) {
            return Collections.EMPTY_LIST;
        }
        PluginConfigurationValidator descriptionValidator = new PluginConfigurationValidator();
        boolean isXmlValid = descriptionValidator.validateXml(tagsDescriptionFile);

        if (!isXmlValid) {
            return Collections.EMPTY_LIST;
        }
        TagsDescriptionParser tagsParser = new TagsXmlDescriptionParser();

        return tagsParser.parseConfigurationFile(tagsDescriptionFile.getPath()).getTagsList().getTags();
    }

    private File retrieveFilePathOfTagsDescriptionFile() {
        String directory = IJ.getDirectory("plugins");
        return new File(directory + File.separator + PluginConfiguration.PLUGIN_CONFIGURATION_XML_NAME);
    }

    private JFileChooser initializeFileSaver(File currentDirectory) {
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        registerFileNameExtensionFilters(fileChooser, SupportedFileTypes.values());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
        return fileChooser;
    }

    private void registerFileNameExtensionFilters(JFileChooser fileChooser,
            SupportedFileTypes[] types) {
        for (int i = 0; i < types.length; i++) {
            SupportedFileTypes type = types[i];
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(type.description, type.extension));
        }
    }
}
