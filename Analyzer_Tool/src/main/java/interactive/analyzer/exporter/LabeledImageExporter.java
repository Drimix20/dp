package interactive.analyzer.exporter;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import interactive.analyzer.presenter.Roi;
import java.io.File;
import java.util.Collection;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Drimal
 */
public class LabeledImageExporter implements ImageExporter {

    @Override
    public void export(String imageName, Collection<Roi> rois,
            int width, int height) {
        ImageProcessor ip = new ShortProcessor(width, height);
        ip.setBackgroundValue(0);
        for (Roi r : rois) {
            ip.setColor(r.getName());
            ip.fillPolygon(r.getPolygon());
        }

        ImagePlus imagePlus = new ImagePlus(imageName, ip);

        JFileChooser fc = new JFileChooser(IJ.getDirectory("current"));
        fc.setSelectedFile(new File(imageName));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.tif", "*.tif"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.jpg", "*.jpg"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.png", "*.png"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.gif", "*.gif"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.bmp", "*.bmp"));

        int retrival = fc.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {

            String absolutePath = fc.getSelectedFile().getAbsolutePath();
            String description = fc.getFileFilter().getDescription();

            if (description.equals("*.tif")) {
                new FileSaver(imagePlus).saveAsTiff(absolutePath + ".tif");
            } else if (description.equals("*.jpg")) {
                new FileSaver(imagePlus).saveAsJpeg(absolutePath + ".jpg");
            } else if (description.equals("*.png")) {
                new FileSaver(imagePlus).saveAsPng(absolutePath + ".png");
            } else if (description.equals("*.gif")) {
                new FileSaver(imagePlus).saveAsGif(absolutePath + ".gif");
            } else if (description.equals("*.bmp")) {
                new FileSaver(imagePlus).saveAsBmp(absolutePath + ".bmp");
            }
        }
        imagePlus.unlock();
        imagePlus.flush();
    }

}
