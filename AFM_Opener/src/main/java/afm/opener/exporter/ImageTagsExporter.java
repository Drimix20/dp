package afm.opener.exporter;

import afm.opener.configuration.PluginConfiguration;
import afm.opener.writer.ImageTagsWriter;
import afm.opener.writer.CsvImageTagsWriter;
import afm.opener.configuration.xml.elements.TagConfiguration;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;
import afm.opener.selector.ChannelContainer;
import afm.opener.writer.tags.sorter.CategoryTagsDescriptionSorter;
import afm.opener.writer.tags.sorter.DecimalIDTagsDescriptionSorter;

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

            File tagsDescriptionFile = new File(PluginConfiguration.getPluginConfigurationXmlPath());
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

}
