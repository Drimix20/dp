package afm.analyzer.scalings;

import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import importer.ImageLoader;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata.decoder.ChannelMetadata;
import metadata.decoder.MetadataDecoder;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class ScalerModuleDemo {

    //TODO implement unit convertor for set results unit
    public static void main(String[] args) {
        try {
            File file = new File("c:\\Users\\Drimal\\Downloads\\zasilka-CHKRI8DLZPAYS4EY\\thyroglobulin 669 kDa-2013.08.13-13.59.14_height_trace.jpk");
            MetadataDecoder decoder = new MetadataDecoder();
            List<ChannelMetadata> decodedMetadata = decoder.decodeMetadata(file);
            ChannelContainer cc = new ChannelContainer(file, 1, decodedMetadata.get(0), decodedMetadata.get(1));

            ImageLoader imageLoader = new ImageLoader();
            List<ChannelContainer> loadedImages = imageLoader.loadImages(Arrays.asList(cc));

            ChannelContainer container = loadedImages.get(0);
            ImageProcessor imageProcessor = container.getImagePlus().getProcessor();
            new ImagePlus("unscaled img", imageProcessor).show();
            ScalerModule sm = new ScalerModule(container.getGeneralMetadata(), container.getChannelMetadata());
            FloatProcessor fp = new FloatProcessor(1024, 1024);
            for (int i = 0; i < 1024; i++) {
                for (int j = 0; j < 1024; j++) {
                    long pixelValue = imageProcessor.get(i, j);
                    double scaledPixelValueInMeter = sm.scalePixelIntensityToObtainReailHeight(pixelValue) * Math.pow(10, 9);
                    fp.putPixelValue(i, j, scaledPixelValueInMeter);
                }
            }
            new ImagePlus("scaled img", fp).show();
        } catch (Exception ex) {
            Logger.getLogger(ScalerModuleDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
