
import java.io.*;
import ij.*;
import ij.process.*;
import ij.io.*;
import javax.swing.JFileChooser;

public class Runner extends Thread {

    private String command;
    private ImagePlus imp;
    private ImageProcessor out;
    private ImageProcessor ip;
    private Double totalVolume = 0.0;

    Runner(String command, ImagePlus imp) {
        super(command);
        this.command = command;
        this.imp = imp;
        this.ip = getImage().getProcessor();
        setPriority(Math.max(getPriority() - 2, MIN_PRIORITY));
        start();
    }

    Runner(String command) {
        super(command);
        this.command = command;
        this.ip = getImage().getProcessor();
        setPriority(Math.max(getPriority() - 2, MIN_PRIORITY));
        start();
    }

    @Override
    public void run() {
        try {
            runCommand(command, imp);
        } catch (OutOfMemoryError e) {
            IJ.outOfMemory(command);
            if (imp != null) {
                imp.unlock();
            }
        } catch (Exception e) {
            CharArrayWriter caw = new CharArrayWriter();
            PrintWriter pw = new PrintWriter(caw);
            e.printStackTrace(pw);
            IJ.log(caw.toString());
            IJ.showStatus("");
            if (imp != null) {
                imp.unlock();
            }
        }
    }

    public void setImage(ImagePlus imp) {
        this.imp = imp;
    }

    public ImagePlus getImage() {
        return this.imp;
    }

    void runCommand(String command, ImagePlus imp) {
        if (imp == null) {
            if (command.equals("Open")) {
                open();
                return;
            }
        }
        ImageProcessor ip = imp.getProcessor();
        IJ.showStatus(command + "...");
        long startTime = System.currentTimeMillis();
			//Roi roi = imp.getRoi();
        //ImageProcessor mask =  roi!=null?roi.getMask():null;   
        if (command.equals("Reset")) {
            reset();
        }
        if (command.equals("Volume")) {
            volume();
        }
        if (command.equals("Open")) {
            open();
        }
        if (command.equals("Threshold")) {
            ip.setAutoThreshold("Triangle");
        }
        //if (mask!=null) ip.reset(mask);
        imp.updateAndDraw();
        imp.unlock();
        IJ.showStatus((System.currentTimeMillis() - startTime) + " milliseconds");
    }

    public void open() {
        IJ.resetEscape();
        String path = new String();
        String version = IJ.getVersion();
        if (version.compareTo("1.47a") < 0) {
            JFileChooser fc = new JFileChooser();
            try {
                fc = new JFileChooser();
            } catch (Throwable e) {
                IJ.error("This plugin requires Java 2 or Swing.");
                return;
            }

            int returnVal = fc.showOpenDialog(IJ.getInstance());
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = fc.getSelectedFile();
            path = file.getPath();

        } else {

            OpenDialog od = new OpenDialog("Open", null);
            path = od.getPath();
        }
        if (path != null) {
            WindowManager.closeAllWindows();
            try {
                IJ.run("TIFF Virtual Stack...", "open=[" + path + "]");
            } catch (Exception e) {
                System.err.println("Caught IOException: " + e.getMessage());

                return;
            }

            ImagePlus imp = WindowManager.getCurrentImage();
            ImageStack stack = new ImageStack();
            stack = imp.getStack();
            stack.deleteSlice(1);
            imp.setStack(stack);
            imp.show();

            imp.updateImage();
            this.setImage(imp);
            ImageProcessor ip = imp.getProcessor();
            imp.updateAndDraw();

        }
    }

    void volume() {
        if (ip != null) {
            int num = ip.getPixelCount();
            for (int i = 0; i < num; ++i) {
                if (ip.get(i) >= ip.getMinThreshold()) {
                    totalVolume += ip.get(i);
                }
            }
            //IJ.showMessage(Double.toString(totalVolume));
            AFM_Volume.setText(Double.toString(totalVolume));
        }
    }

    public void reset() {
        ip.reset();
        ip.resetThreshold();
        AFM_Volume.resetText();
    }

    void threshold() {
        ImageProcessor ip = getImage().getProcessor();
        // create the output image as a copy of the input one
        ImageProcessor out = ip.duplicate();

        // the number of pixels in the image 'ip'
        int num = ip.getPixelCount();

        // threshold
        int T = 0;

        // compute the intensity histogram and find the minimum and maximum intensities
        int min = ip.get(0);
        int max = ip.get(0);
        int[] hist = new int[256];
        int value = 0;

        for (int i = 0; i < num; ++i) {
            value = ip.get(i);
            ++hist[value];

            if (value < min) {
                min = value;
            } else if (value > max) {
                max = value;
            }
        }

        // find the maximum peak position in the histogram
        int histMax = hist[0];
        int histPeak = 0;

        for (int i = 1; i < hist.length; ++i) {
            if (hist[i] > histMax) {
                histMax = hist[i];
                histPeak = i;
            }
        }

        // find the farest non-zero position from the maximum peak position
        int beginX = 0;
        int beginY = 0;
        int endX = 0;
        int endY = 0;

        if ((histPeak - min) > (max - histPeak)) {
            beginX = min;
            beginY = hist[min];
            endX = histPeak;
            endY = histMax;
        } else {
            beginX = histPeak;
            beginY = histMax;
            endX = max;
            endY = hist[max];
        }

        // construct a line between the points 'begin' and 'end'	
        float a = endY - beginY;
        float b = beginX - endX;
        float c = beginY * endX - beginX * endY;

        // find the histogram position of the maximum distance from the constructed line		
        float maxDist = 0;
        float dist = 0;

        for (int i = beginX; i <= endX; ++i) {
            dist = Math.abs(a * i + b * hist[i] + c);

            if (dist > maxDist) {
                maxDist = dist;
                T = i;
            }
        }

		// threshold the image 'ip' with the computed threshold 'T'
        // write the thresholded image into the image 'out' (background -> 0, foreground -> 255)
        for (int i = 0; i < num; ++i) {
            if (ip.get(i) >= T) {
                out.set(i, 255);
            } else {
                out.set(i, 0);
            }
        }

        // show the output image
        ImagePlus outImg = new ImagePlus("My unimodal thresholding", out);
        outImg.show();
    }

}
