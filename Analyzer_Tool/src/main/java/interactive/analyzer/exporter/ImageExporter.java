package interactive.analyzer.exporter;

import interactive.analyzer.presenter.Roi;
import java.util.Collection;

/**
 *
 * @author Drimal
 */
public interface ImageExporter {

    void export(String imageName, Collection<Roi> rois, int width,
            int height);
}
