package exporter;

import configuration.TagsDescriptionParser;
import writer.ImageTagsWriter;
import writer.CsvImageTagsWriter;
import configuration.TagsXmlDescriptionParser;
import configuration.xml.elements.Tag;
import configuration.TagsDescriptionValidator;
import ij.IJ;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

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
        JFileChooser fileChooser = initializeFileSaver(currentDirectory);

        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            logger.debug("Selected file to save is " + file.getAbsolutePath());

            File tagsDescriptionFile = retrieveFilePathOfTagsDescriptionFile();
            List<Tag> loadTagsDescription = loadTagsDescription(tagsDescriptionFile);
            logger.debug("Number of tags from xml: " + loadTagsDescription.size());

            FileFilter selectedFilter = fileChooser.getFileFilter();
            ImageTagsWriter tagsWriter = getWriterInstance(selectedFilter);
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

    private List<Tag> loadTagsDescription(File tagsDescriptionFile) {
        if (!tagsDescriptionFile.exists()) {
            return Collections.EMPTY_LIST;
        }
        TagsDescriptionValidator descriptionValidator = new TagsDescriptionValidator();
        boolean isXmlValid = descriptionValidator.validateXmlBySchema(tagsDescriptionFile);

        if (!isXmlValid) {
            return Collections.EMPTY_LIST;
        }
        TagsDescriptionParser tagsParser = new TagsXmlDescriptionParser();

        return tagsParser.parseTagsDescriptions(tagsDescriptionFile.getPath());
    }

    private File retrieveFilePathOfTagsDescriptionFile() {
        String directory = IJ.getDirectory("plugins");
        return new File(directory + File.separator + "tagsDescription.xml");
    }

    private JFileChooser initializeFileSaver(File currentDirectory) {
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        //TODO possible add new file filter
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("File CSV (.csv)", "csv"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
        return fileChooser;
    }

}
