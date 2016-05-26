package afm.opener.gui;

import afm.opener.calibration.IntensityCalibrator;
import afm.opener.calibration.SizeCalibrator;
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
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Logger;
import scaler.module.types.LengthUnit;
import scaler.module.types.UnsupportedScalingType;

/**
 *
 * @author Drimal
 */
public class AfmOpenerFrame extends javax.swing.JFrame {

    private Logger logger = Logger.getLogger(AfmOpenerFrame.class);
    private final CountDownLatch latch;
    //TODO property for manual testing
    private File currentDirectory = new File("c:\\Users\\Drimal\\Skola\\dp-volumetricka-analyza\\obrazy\\");
//    private File currentDirectory = new File("c:\\Users\\Drimal\\Downloads\\zasilka-CHKRI8DLZPAYS4EY\\");
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
        selectedChannelContainer = new ArrayList<ChannelContainer>();
        this.latch = latch;
        this.disposeAfterOpen = disposeAfterOpen;
        this.showLoadedImages = showLoadedImages;
    }

    private void resetButtons() {
        selectAll.setSelected(false);
    }

    private void resetDataStructures() {
        selectedChannelContainer = new ArrayList<ChannelContainer>();
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
        exportTagsCheckbox = new javax.swing.JCheckBox();
        calibrateImageCheckbox = new javax.swing.JCheckBox();
        dimensionsUnitComboBox = new javax.swing.JComboBox();
        dimensionUnitLabel = new javax.swing.JLabel();
        calibrationUnitLabel = new javax.swing.JLabel();
        calibrationUnitComboBox = new javax.swing.JComboBox();

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
        calibrateImageCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calibrateImageCheckboxActionPerformed(evt);
            }
        });

        dimensionsUnitComboBox.setModel(new DefaultComboBoxModel(LengthUnit.retrieveAbbreviations()));
        dimensionsUnitComboBox.setSelectedItem(LengthUnit.MICROMETER);

        dimensionUnitLabel.setText(" Dimensions unit:");
        dimensionUnitLabel.setToolTipText("");

        calibrationUnitLabel.setText(" Height value unit:");

        calibrationUnitComboBox.setModel(new DefaultComboBoxModel(LengthUnit.retrieveAbbreviations()));
        calibrationUnitComboBox.setSelectedItem(LengthUnit.NANOMETER);

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
                                    .addComponent(exportTagsCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(calibrateImageCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(calibrationUnitLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(calibrationUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(dimensionUnitLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dimensionsUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(41, 41, 41)
                                            .addComponent(OpenButton)
                                            .addGap(18, 18, 18)
                                            .addComponent(cancelButton))))))
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
                .addComponent(imageOptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportTagsCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(calibrateImageCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dimensionsUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dimensionUnitLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calibrationUnitLabel)
                    .addComponent(calibrationUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addComponent(OpenButton))
                .addContainerGap())
        );

        dimensionUnitLabel.getAccessibleContext().setAccessibleName("Dimensions Unit:");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectButtonActionPerformed
        logger.info("Clicked on select button");

        //TODO delete before deploy
        if (!currentDirectory.exists()) {
            currentDirectory = new File(System.getProperty("user.home"));
        }
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpk", "jpk"));

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
        logger.info("Clicked on cancel button");
        disposeAfmOpener(true);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void OpenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenButtonActionPerformed
        logger.info("Clicked on open button");

        if (selectedChannelContainer.isEmpty()) {
            IJ.showMessage("No image selected to open");
            return;
        }

        ImageLoader loader = new ImageLoader();
        List<ChannelContainer> loadedImages = loader.loadImages(selectedChannelContainer);

        if (calibrateImageCheckbox.isSelected()) {
            try {
                LengthUnit dimensionUnit = LengthUnit.parseFromAbbreviation((String) dimensionsUnitComboBox.getSelectedItem());
                LengthUnit calibrationUnit = LengthUnit.parseFromAbbreviation((String) calibrationUnitComboBox.getSelectedItem());
                calibrateImages(loadedImages, dimensionUnit, calibrationUnit);
            } catch (UnsupportedScalingType ex) {
                IJ.error(ex.toString());
            }
        }

        showLoadedImages(loadedImages, showLoadedImages);
        disposeAfmOpener(disposeAfterOpen);

        if (exportTagsCheckbox.isSelected()) {
            TagsExporter tagsExporter = new ImageTagsExporter();
            tagsExporter.exportImageTags(loadedImages, currentDirectory);
        }

        latchCountDown();

        logger.info("Images were loaded");
    }//GEN-LAST:event_OpenButtonActionPerformed

    private void calibrateImages(List<ChannelContainer> loadedImages,
            LengthUnit dimensionUnit, LengthUnit heightValueUnit) throws UnsupportedScalingType {
        for (ChannelContainer cc : loadedImages) {
            if (cc.getChannelName().trim().equals("thumbnail")) {
                logger.trace("Channel is thumbnail skipping calibration");
                continue;
            }

            cc.setImagePlus(cc.getImagePlus());

            SizeCalibrator sizeCalibrator = new SizeCalibrator();
            double calibrateImageWidth = sizeCalibrator.calibrateImageWidth(cc, dimensionUnit);
            IJ.run(cc.getImagePlus(), "Set Scale...", "distance=" + cc.getImagePlus().getWidth() + " known=" + calibrateImageWidth + " unit=" + dimensionUnit.getAbbreviation());

            logger.trace("Intensity calibration");
            ImagePlus imagePlus = cc.getImagePlus();
            double minValueBeforeChangingTo16bit = imagePlus.getProcessor().getMin();
            double maxValueBeforeChangingTo16bit = imagePlus.getProcessor().getMax();
            logger.trace("Before converting to 16-bit image: min: " + minValueBeforeChangingTo16bit + ", max: " + maxValueBeforeChangingTo16bit);
            logger.trace("Convert image into 16-bit image");
            IJ.run(imagePlus, "16-bit", "");

            cc.setImagePlus(imagePlus);
            IntensityCalibrator ic = new IntensityCalibrator();
            double[] parameters = ic.computeCalibrationFunction(cc, minValueBeforeChangingTo16bit, maxValueBeforeChangingTo16bit, Calibration.STRAIGHT_LINE, heightValueUnit);

            if (parameters != null) {
                logger.trace("Calibration function parameters " + Arrays.toString(parameters));
                //imagePlus.getCalibration().setFunction(Calibration.STRAIGHT_LINE, parameters, heightValueUnit.getAbbreviation());
                IJ.run(imagePlus, "Calibrate...", "function=[Straight Line] unit=nm text1=[" + imagePlus.getProcessor().getMin() + " " + imagePlus.getProcessor().getMax() + "] text2=[" + parameters[0] + " " + (double) parameters[1] + "]");
            } else {
                logger.trace("Parameters of calibration funcition are null");
            }

            //Define whic value is used to scale bar
            double scaleBarValue = 0.025;
            IJ.run(cc.getImagePlus(), "Scale Bar...", "width=" + scaleBarValue + " height=3 font=18 color=Black background=None location=[Lower Right] bold overlay");
        }
    }

    private void showLoadedImages(List<ChannelContainer> loadedImages,
            boolean show) {
        if (show) {
            AfmOpenerImagePresenter presenter = new AfmOpenerImagePresenter();

            presenter.showAsStack(false);
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
        logger.info("Clicked on select all");
        if (selectAll.isSelected()) {
            logger.trace("Selecting all");
            imageOptionManager.selectAllImages(true);
        } else {
            logger.trace("Deselecting all");
            imageOptionManager.selectAllImages(false);
        }
        imageOptionManager.run();
    }//GEN-LAST:event_selectAllPerformed

    private void calibrateImageCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calibrateImageCheckboxActionPerformed
        if (calibrateImageCheckbox.isSelected()) {
            IJ.showMessage("AFM Opener", "Thumbnail image will not be calibrated");
            setVisibilityOfCalibrationComponents(true);
        } else {
            setVisibilityOfCalibrationComponents(false);
        }
    }//GEN-LAST:event_calibrateImageCheckboxActionPerformed

    private void setVisibilityOfCalibrationComponents(boolean isVisible) {
        dimensionUnitLabel.setEnabled(isVisible);
        calibrationUnitLabel.setEnabled(isVisible);
        dimensionsUnitComboBox.setEnabled(isVisible);
        calibrationUnitComboBox.setEnabled(isVisible);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OpenButton;
    private javax.swing.JButton SelectButton;
    private javax.swing.JCheckBox calibrateImageCheckbox;
    private javax.swing.JComboBox calibrationUnitComboBox;
    private javax.swing.JLabel calibrationUnitLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel dimensionUnitLabel;
    private javax.swing.JComboBox dimensionsUnitComboBox;
    private javax.swing.JCheckBox exportTagsCheckbox;
    private javax.swing.JScrollPane imageOptionPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nameField;
    private javax.swing.JCheckBox selectAll;
    // End of variables declaration//GEN-END:variables
}
