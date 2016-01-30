package afm.opener.gui;

import afm.opener.common.ImageOptionManager;
import afm.opener.common.AfmOpenerImagePresenter;
import afm.opener.exporter.ImageTagsExporter;
import afm.opener.exporter.TagsExporter;
import afm.opener.importer.FileSearcher;
import afm.opener.importer.ImageLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.swing.JFileChooser;
import afm.opener.selector.ChannelContainer;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Drimal
 */
public class AfmOpenerFrame extends javax.swing.JFrame {

    private Logger logger = LoggerFactory.getLogger(AfmOpenerFrame.class);
    private final CountDownLatch latch;
    private File currentDirectory = new File("c:\\Users\\Drimal\\Downloads\\zasilka-CHKRI8DLZPAYS4EY\\");
    private List<ChannelContainer> selectedChannelContainer;
    private ImageOptionManager imageOptionManager;
    private boolean showLoadedImages;
    private boolean disposeAfterOpen;

    public AfmOpenerFrame() {
        this(new CountDownLatch(1), false, true);
    }

    public AfmOpenerFrame(CountDownLatch latch,
            boolean disposeAfterOpen, boolean showLoadedImages) {
        if (latch == null) {
            throw new IllegalArgumentException("Count down latch is null");
        }
        initComponents();
        selectedChannelContainer = new ArrayList<>();
        this.latch = latch;
        this.disposeAfterOpen = disposeAfterOpen;
        this.showLoadedImages = showLoadedImages;
    }

    private void resetButtons() {
        selectAll.setSelected(false);
        this.showInStack.setSelected(true);
    }

    private void resetDataStructures() {
        selectedChannelContainer = new ArrayList<>();
    }

    public void showLoadedImages(boolean showLoadedImages) {
        this.showLoadedImages = showLoadedImages;
    }

    public List<ChannelContainer> getSelectedChannelContainer() {
        return selectedChannelContainer;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        SelectButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        imageOptionPanel = new javax.swing.JScrollPane();
        cancelButton = new javax.swing.JButton();
        OpenButton = new javax.swing.JButton();
        selectAll = new javax.swing.JCheckBox();
        showInStack = new javax.swing.JCheckBox();
        exportTagsCheckbox = new javax.swing.JCheckBox();
        calibrateImageCheckbox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AFM Opener");
        setIconImages(null);
        setResizable(false);

        jLabel1.setText("Select folder/ file");

        nameField.setEditable(false);

        SelectButton.setText("Select");
        SelectButton.setToolTipText("Select image or directory to open");
        SelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Images / Channels");

        imageOptionPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        imageOptionPanel.setName(""); // NOI18N
        imageOptionPanel.setPreferredSize(new java.awt.Dimension(183, 195));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        OpenButton.setText("Open");
        OpenButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenButtonActionPerformed(evt);
            }
        });

        selectAll.setText("Select all:                                               ");
        selectAll.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        selectAll.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        selectAll.setMaximumSize(new java.awt.Dimension(198, 24));
        selectAll.setPreferredSize(new java.awt.Dimension(198, 24));
        selectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllPerformed(evt);
            }
        });

        showInStack.setSelected(true);
        showInStack.setText("Show in stack:                              ");
        showInStack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showInStack.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        showInStack.setMaximumSize(new java.awt.Dimension(198, 24));
        showInStack.setPreferredSize(new java.awt.Dimension(198, 24));

        exportTagsCheckbox.setText("Export all tags:                             ");
        exportTagsCheckbox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exportTagsCheckbox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        exportTagsCheckbox.setMaximumSize(new java.awt.Dimension(198, 24));
        exportTagsCheckbox.setPreferredSize(new java.awt.Dimension(198, 24));

        calibrateImageCheckbox.setSelected(true);
        calibrateImageCheckbox.setText("Calibrate image:                           ");
        calibrateImageCheckbox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        calibrateImageCheckbox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        calibrateImageCheckbox.setMaximumSize(new java.awt.Dimension(198, 24));
        calibrateImageCheckbox.setPreferredSize(new java.awt.Dimension(198, 24));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageOptionPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(showInStack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(OpenButton)
                                        .addGap(18, 18, 18)
                                        .addComponent(cancelButton))
                                    .addComponent(exportTagsCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(calibrateImageCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addComponent(selectAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SelectButton))
                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SelectButton)
                .addGap(2, 2, 2)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imageOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(showInStack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportTagsCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calibrateImageCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addComponent(OpenButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectButtonActionPerformed
        logger.info("Clicked on select button...");

        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int returnVal = fileChooser.showOpenDialog(AfmOpenerFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            resetButtons();
            resetDataStructures();

            File file = fileChooser.getSelectedFile();

            currentDirectory = file.getParentFile();
            nameField.setText(file.getName());
            nameField.setToolTipText(file.getName());

            FileSearcher processor = new FileSearcher();
            List<ChannelContainer> channelContainer = processor.preloadJpkImageFiles(file);

            imageOptionManager = new ImageOptionManager(imageOptionPanel, channelContainer, selectedChannelContainer);
            imageOptionManager.run();
        }
    }//GEN-LAST:event_SelectButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        logger.info("Canceled");
        disposeAfmOpener(true);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void OpenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenButtonActionPerformed
        logger.info("Loading images...");

        if (selectedChannelContainer.isEmpty()) {
            IJ.showMessage("No image selected to open");
            return;
        }

        ImageLoader loader = new ImageLoader();
        List<ChannelContainer> loadedImages = loader.loadImages(selectedChannelContainer);

        showLoadedImages(loadedImages, showLoadedImages);
        disposeAfmOpener(disposeAfterOpen);

        if (calibrateImageCheckbox.isSelected()) {
            calibrateImages(loadedImages);
        }

        if (exportTagsCheckbox.isSelected()) {
            TagsExporter tagsExporter = new ImageTagsExporter();
            tagsExporter.exportImageTags(loadedImages, currentDirectory);
        }

        latchCountDown();

        logger.info("Images were loaded");
    }//GEN-LAST:event_OpenButtonActionPerformed

    private void calibrateImages(List<ChannelContainer> loadedImages) {
        for (ChannelContainer cc : loadedImages) {
            double uLength = (double) cc.getGeneralMetadata().getTagValue(32834);
            double vLength = (double) cc.getGeneralMetadata().getTagValue(32835);

            ImagePlus imagePlus = cc.getImagePlus();
            int imgWidth = imagePlus.getWidth();
            int imgHeight = imagePlus.getHeight();

            Calibration cal = imagePlus.getCalibration();
            logger.trace("ImageWidth: " + uLength * Math.pow(10, 9));
            double pixelWidth = uLength / imgWidth * Math.pow(10, 9);
            cal.pixelWidth = pixelWidth;
            double pixelHeight = vLength / imgHeight * Math.pow(10, 9);
            cal.pixelHeight = pixelHeight;
            double pixelDepth = uLength / imgWidth * Math.pow(10, 9);
            cal.pixelDepth = pixelDepth;
            logger.trace("pixelWidth: " + pixelWidth + ", pixelHeight: " + pixelHeight + ", pixelDepth:" + pixelDepth);
            cal.setUnit("nm");

            //TODO calibration of 32-bit image cannot be done
//            double min = imagePlus.getProcessor().getMin();
//            double max = imagePlus.getProcessor().getMax();
//
//            IntensityCalibrator ic = new IntensityCalibrator(cc);
//            int calibrationType = Calibration.STRAIGHT_LINE;
//            if (imagePlus.getType() != ImagePlus.GRAY32) {
//                calibrationType = Calibration.STRAIGHT_LINE;
//            }
//            double[] parameters = ic.computeCalibrationFunctionInNanometer(min, max, Calibration.STRAIGHT_LINE);
//            logger.trace("Calibration function parameters " + Arrays.toString(parameters));
//            if (parameters != null) {
//                cal.setFunction(calibrationType, parameters, "nm");
//            } else {
//                logger.trace("Parameters of calibration funcition are null");
//            }
            cc.getImagePlus().setCalibration(cal);

//            IJ.run(imagePlus, "Calibration Bar...", "location=[Upper Right] fill=White label=Black number=5 decimal=0 font=12 zoom=1.3 overlay");
            IJ.run(imagePlus, "Scale Bar...", "width=50 height=5 font=18 color=Black background=White location=[Lower Right] overlay");
        }
    }

    private void showLoadedImages(List<ChannelContainer> loadedImages,
            boolean show) {
        if (showLoadedImages) {
            AfmOpenerImagePresenter presenter = new AfmOpenerImagePresenter();
            if (loadedImages.size() == 1 && showInStack.isSelected()) {
                IJ.showMessage("One image cannot be opened in stack");
                showInStack.setSelected(false);
            }

            presenter.showAsStack(showInStack.isSelected());
            presenter.show(loadedImages);
        }
    }

    private void latchCountDown() {
        if (latch != null) {
            latch.countDown();
        }
    }

    private void disposeAfmOpener(boolean dispose) {
        if (!dispose) {
            return;
        }

        if (imageOptionManager != null && imageOptionManager.isAlive()) {
            imageOptionManager.interrupt();
        }
        this.dispose();
    }

    private void selectAllPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllPerformed
        logger.info("All channels selected");
        if (selectAll.isSelected()) {
            imageOptionManager.selectAllImages(true);
        } else {
            imageOptionManager.selectAllImages(false);
        }
        imageOptionManager.run();
    }//GEN-LAST:event_selectAllPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OpenButton;
    private javax.swing.JButton SelectButton;
    private javax.swing.JCheckBox calibrateImageCheckbox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox exportTagsCheckbox;
    private javax.swing.JScrollPane imageOptionPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameField;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JCheckBox showInStack;
    // End of variables declaration//GEN-END:variables
}
