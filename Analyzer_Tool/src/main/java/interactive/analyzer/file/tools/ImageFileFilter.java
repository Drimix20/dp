package interactive.analyzer.file.tools;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Drimal
 */
public class ImageFileFilter extends FileFilter {

    private String extension;

    public ImageFileFilter(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith("." + extension);
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String getDescription() {
        return extension.toUpperCase() + " file";
    }
}
