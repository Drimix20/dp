
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 *
 * @author Drimal
 */
public class HistogramDemo implements PlugIn {

    public static int[] calculateHistogram(double[] data, double min, double max,
            int numBins) {
        final int[] result = new int[numBins];
        final double binSize = (max - min) / numBins;

        for (double d : data) {
            int bin = (int) ((d - min) / binSize); // changed this from numBins
            if (isBinInRange(bin, 0, numBins)) {
                result[bin] += 1;
            }
        }
        return result;
    }

    private static boolean isBinInRange(int bin, int low, int max) {
        return bin > low && bin < max;
    }

    public static int[] calcHistogram(double[] data, double min, double max,
            int numBins) {
        final int[] result = new int[numBins];
        final double binSize = (max - min) / numBins;

        for (double d : data) {
            int bin = (int) ((d - min) / binSize);
            if (bin < 0) {
                /* this data is smaller than min */
            } else if (bin >= numBins) {
                /* this data point is bigger than max */
            } else {
                result[bin] += 1;
            }
        }
        return result;
    }

    public void printHistogram(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.println(i + ": " + data[i]);
        }
    }

    @Override
    public void run(String arg) {
        ImagePlus img = IJ.openImage("http://imagej.nih.gov/ij/images/clown.jpg");//IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif");
        ImageProcessor processor = img.getProcessor();
        int[] histogram = processor.getHistogram();
        double histogramMax = processor.getHistogramMax();
        double histogramMin = processor.getHistogramMin();
        int histogramSize = processor.getHistogramSize();
//        System.out.println("Image Histogram: minValue=" + histogramMin + ", maxValue=" + histogramMax + "");
//        printHistogram(histogram);

        double[] values = new double[processor.getPixelCount()];
        int pos = 0;
        for (int i = 0; i < processor.getWidth(); i++) {
            for (int j = 0; j < processor.getHeight(); j++) {
                values[pos] = processor.getPixelValue(i, j);
                pos++;
            }
        }
        System.out.println("####################################");
        int[] calculateHistogram = calcHistogram(values, 0, processor.getMax(), 256);
        printHistogram(calculateHistogram);
    }

    public static
            void main(String[] args) {

        // set the plugins.dir property to make the plugin appear in the Plugins menu
        Class<?> clazz = HistogramDemo.class;
        String url = clazz.getResource("/" + clazz.getName().replace('.', '/') + ".class").toString();
        String pluginsDir = url.substring(5, url.length() - clazz.getName().length() - 6);

        System.setProperty(
                "plugins.dir", pluginsDir);

        // start ImageJ
        new ImageJ();

        // run the plugin
        IJ.runPlugIn(clazz.getName(), "");
    }
}
