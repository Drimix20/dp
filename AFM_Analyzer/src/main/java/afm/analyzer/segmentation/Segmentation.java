package afm.analyzer.segmentation;

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
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class Segmentation {

    private static Logger logger = Logger.getLogger(Segmentation.class);

    public List<SegmentedImage> segmentImages(List<ChannelContainer> channelContainers, ImageThresholdStrategy thresholdStrategy) {
        logger.info("Segment images " + channelContainers.size());
        IJ.showStatus("Image segmentation");

        List<SegmentedImage> segmentedImages = new ArrayList<>();
        ResultsTable resultsTable = new ResultsTable();
        RoiManager roiManager = new RoiManager(true);
        ParticleAnalyzer analyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_NONE, Measurements.ADD_TO_OVERLAY
                | Measurements.RECT, resultsTable, 0, Double.POSITIVE_INFINITY, 0, 1);
        ParticleAnalyzer.setRoiManager(roiManager);
        ParticleAnalyzer.setLineWidth(1);
        int currentIndex = 1;
        for (ChannelContainer channel : channelContainers) {
            SegmentedImage segmentedImage = new SegmentedImage();
            ImagePlus img = channel.getImagePlus();

            ImageProcessor binaryIp = makeBinary(img, thresholdStrategy);
            segmentedImage.setThresholdedIp(binaryIp);
            segmentedImage.setSegments(segmentImage(analyzer, resultsTable, roiManager, new ImagePlus("", binaryIp)));
            segmentedImage.setRois(computeRoiOfSegments(roiManager));

            resultsTable.reset();
            roiManager.reset();
            segmentedImages.add(segmentedImage);

            IJ.showProgress(currentIndex, channelContainers.size());
            currentIndex++;
        }

        return segmentedImages;
    }

    public List<ImageProcessor> makeBinaryImages(List<ChannelContainer> channelContainers, ImageThresholdStrategy thresholdStrategy) {
        logger.info("Make binary images " + channelContainers.size());
        List<ImageProcessor> binaryProcessors = new ArrayList<>();
        for (ChannelContainer channel : channelContainers) {
            ImageProcessor ip = makeBinary(channel.getImagePlus().duplicate(), thresholdStrategy);
            binaryProcessors.add(ip);
        }
        return binaryProcessors;
    }

    public ImageProcessor makeBinary(ImagePlus imp, ImageThresholdStrategy thresholdStrategy) {
        logger.info("Make binary image " + imp.getTitle());
        return thresholdStrategy.makeBinary(imp.duplicate());
    }

    public List<Segment> segmentImage(ParticleAnalyzer analyzer, ResultsTable resultsTable, RoiManager roiManager, ImagePlus imp) {
        logger.info("Make segments of image " + imp.getTitle());
        List<Segment> segments = new ArrayList<Segment>();

        analyzer.analyze(imp);

        String[] headings = resultsTable.getHeadings();
        int bxIndex = resultsTable.getColumnIndex(headings[0]);
        int byIndex = resultsTable.getColumnIndex(headings[1]);
        int widthIndex = resultsTable.getColumnIndex(headings[2]);
        int heightIndex = resultsTable.getColumnIndex(headings[3]);
        for (int i = 0; i < resultsTable.size(); i++) {
            Segment segment = new Segment((i + 1),
                    (int) resultsTable.getValueAsDouble(bxIndex, i),
                    (int) resultsTable.getValueAsDouble(byIndex, i),
                    (int) resultsTable.getValueAsDouble(widthIndex, i),
                    (int) resultsTable.getValueAsDouble(heightIndex, i));

            segments.add(segment);
        }

        logger.info("Count of segments " + segments.size());
        return segments;
    }

    public List<Roi> computeRoiOfSegments(RoiManager roiManager) {
        List<Roi> rois = new ArrayList<>();
        Roi[] roisAsArray = roiManager.getRoisAsArray();
//        int[] selectedIndexes = new int[roisAsArray.length];
//        for (int i = 0; i < roisAsArray.length; i++) {
//            selectedIndexes[i] = i;
//        }
//        roiManager.setSelectedIndexes(selectedIndexes);
//        roiManager.runCommand("Delete");

        for (int i = 0; i < roisAsArray.length; i++) {
            ExtendedRoi extRoi = new ExtendedRoi(roisAsArray[i].getPolygon(), Roi.TRACED_ROI);
            extRoi.setLabel(i + 1);
            //roiManager.addRoi(extRoi);
        }

        return rois;
    }
}
