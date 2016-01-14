package afm.opener.exporter;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Save file chooser for exported tags
 * @author Drimal
 */
public class ExportedTagsSaveFileChooser extends JFileChooser {

    private enum SupportedFileTypes {

        CSV_TYPE("File CSV (.csv)", "csv");

        private String description;
        private String extension;

        private SupportedFileTypes(String description, String extension) {
            this.description = description;
            this.extension = extension;
        }

    }

    public ExportedTagsSaveFileChooser(File currentDirectory) {
        super(currentDirectory);
        registerFileNameExtensionFilters(this, SupportedFileTypes.values());
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setAcceptAllFileFilterUsed(false);
        setFileFilter(this.getChoosableFileFilters()[0]);
    }

    private void registerFileNameExtensionFilters(JFileChooser fileChooser,
            SupportedFileTypes[] types) {
        for (int i = 0; i < types.length; i++) {
            SupportedFileTypes type = types[i];
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(type.description, type.extension));
        }
    }
}
