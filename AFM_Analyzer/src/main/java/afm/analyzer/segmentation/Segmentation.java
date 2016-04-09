package afm.analyzer.segmentation;

import static afm.analyzer.segmentation.SegmentationConfiguration.*;
import afm.analyzer.threshold.ImageThresholdStrategy;
import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import afm.opener.selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class Segmentation {

    private static Logger logger = Logger.getLogger(Segmentation.class);

    public List<ImageSegments> segmentImages(
            List<ChannelContainer> channelContainers,
            ImageThresholdStrategy thresholdStrategy) {
        logger.info("Segment images " + channelContainers.size());
        IJ.showStatus("Image segmentation");

        List<ImageSegments> segmentedImages = new ArrayList<ImageSegments>();
        ResultsTable resultsTable = new ResultsTable();
        RoiManager roiManager = new RoiManager(true);

        ParticleAnalyzer analyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_NONE,
                Measurements.ADD_TO_OVERLAY | Measurements.RECT,
                resultsTable, getMinPixelSize(), getMaxPixelSize(),
                getMinCircularity(), getMaxCircularity());
        ParticleAnalyzer.setRoiManager(roiManager);
        ParticleAnalyzer.setLineWidth(1);

        int currentIndex = 1;
        for (ChannelContainer channel : channelContainers) {
            ImagePlus img = channel.getImagePlus();
            logger.info("Make segments of image " + img.getTitle());
            ImageSegments segmentedImage = new ImageSegments();
            ImageProcessor binaryIp = makeBinary(img, thresholdStrategy);
            segmentedImage.setThresholdedIp(binaryIp);
            analyzer.analyze(new ImagePlus("", binaryIp));
            segmentedImage.setRois(computeRoiOfSegments(roiManager));

            //TODO creation of segmented image without rois and showing it
            ImagePlus segmentedImgWithRois = new ImagePlus("Segmented image", segmentedImage.getThresholdedImageProcessor().duplicate());
//            for (Roi roi : segmentedImage.getRois()) {
//                //draw roi at fixed location and not resizable
//                roi.setNonScalable(true);
//                roiManager.add(segmentedImgWithRois, roi, ((ExtendedRoi) roi).getLabel());
//            }
            segmentedImgWithRois.show();
            resultsTable.reset();
            roiManager.reset();

            segmentedImages.add(segmentedImage);

            IJ.showProgress(currentIndex, channelContainers.size());
            currentIndex++;
        }

        return segmentedImages;
    }

    public List<ImageProcessor> makeBinaryImages(
            List<ChannelContainer> channelContainers,
            ImageThresholdStrategy thresholdStrategy) {
        logger.info("Make binary images " + channelContainers.size());
        List<ImageProcessor> binaryProcessors = new ArrayList<ImageProcessor>();
        for (ChannelContainer channel : channelContainers) {
            ImageProcessor ip = makeBinary(channel.getImagePlus().duplicate(), thresholdStrategy);
            binaryProcessors.add(ip);
        }
        return binaryProcessors;
    }

    public ImageProcessor makeBinary(ImagePlus imp,
            ImageThresholdStrategy thresholdStrategy) {
        logger.info("Make binary image " + imp.getTitle());
        return thresholdStrategy.makeBinary(imp.duplicate());
    }

    public List<Roi> computeRoiOfSegments(RoiManager roiManager) {
        List<Roi> rois = new ArrayList<Roi>();
        Roi[] roisAsArray = roiManager.getRoisAsArray();
//        int[] selectedIndexes = new int[roisAsArray.length];
//        for (int i = 0; i < roisAsArray.length; i++) {
//            selectedIndexes[i] = i;
//        }
//        roiManager.setSelectedIndexes(selectedIndexes);
//        roiManager.runCommand("Delete");

        for (int i = 0; i < roisAsArray.length; i++) {
            //TODO check name before setting index
            Roi r = roisAsArray[i];
            r.setName((i + 1) + "");
            rois.add(r);
        }

        return rois;
    }
}
