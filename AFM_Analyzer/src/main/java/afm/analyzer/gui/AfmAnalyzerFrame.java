package afm.analyzer.gui;

import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.measurements.AbstractMeasurementResult;
import afm.analyzer.measurements.MeasurementComputation;
import afm.analyzer.segmentation.ImageSegments;
import afm.analyzer.segmentation.Segmentation;
import afm.analyzer.threshold.ImageThresholdStrategy;
import afm.analyzer.threshold.ThresholderExecutor;
import afm.analyzer.threshold.ThresholderExecutor.Strategies;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import afm.opener.selector.ChannelContainer;
import ij.WindowManager;
import ij.measure.ResultsTable;
import java.awt.event.ItemEvent;
import java.util.Arrays;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerFrame extends javax.swing.JFrame {

    //TODO review computation of image
    private static Logger logger = Logger.getLogger(AfmAnalyzerFrame.class);
    private String[] strategiesName;
    private List<AbstractMeasurement> selectedMeasurements;
    private List<ChannelContainer> selectedChannelContainer;
    List<ImageSegments> segmentImages;
    private boolean segmentationChanged = false;
    private boolean selectSegmentedImageManually = false;
    private ImageThresholdStrategy thresholder;
    private SegmentationConfigDialog segmentationConfDialog;

    /**
     * Creates new form AfmAnalyzer
     */
    public AfmAnalyzerFrame() {
        strategiesName = ThresholderExecutor.getStrategiesName();
        initComponents();
        selectedMeasurements = new ArrayList<AbstractMeasurement>();
        selectedChannelContainer = new ArrayList<ChannelContainer>();
        segmentImages = new ArrayList<ImageSegments>();
        segmentationOptionButton.setEnabled(false);

        MeasurementsElementManager elementManager = new MeasurementsElementManager(measurementsPanel);
        elementManager.setSelectedMeasurements(selectedMeasurements);
        elementManager.run();
    }

    public void setChannels(List<ChannelContainer> selectedChannelContainer) {
        this.selectedChannelContainer = selectedChannelContainer;
    }

    private void openImages() {
        for (int i = 0; i < selectedChannelContainer.size(); i++) {
            selectedChannelContainer.get(i).getImagePlus().show();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        measurementsLabel = new javax.swing.JLabel();
        measurementsPanel = new javax.swing.JScrollPane();
        cancelButton = new javax.swing.JButton();
        measureButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        segmentationMethodComboBox = new javax.swing.JComboBox(strategiesName);
        segmentationOptionButton = new javax.swing.JButton();
        segmentationPreviewButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        selectSegmentedCheckBox = new javax.swing.JCheckBox();
        segmentedImagesComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AFM Analyzer");
        setMinimumSize(new java.awt.Dimension(380, 450));
        setPreferredSize(new java.awt.Dimension(380, 655));

        measurementsLabel.setText("Measurements");

        measurementsPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        measureButton.setText("Measure");
        measureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                measureButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Segmentation"));

        segmentationMethodComboBox.setSelectedItem(ThresholderExecutor.getStrategiesName());
        segmentationMethodComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                segmentationMethodComboBoxItemStateChanged(evt);
            }
        });

        segmentationOptionButton.setText("Options");
        segmentationOptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationOptionButtonActionPerformed(evt);
            }
        });

        segmentationPreviewButton.setText("Preview");
        segmentationPreviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationPreviewButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Method:");

        selectSegmentedCheckBox.setText("Select segmented:");
        selectSegmentedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSegmentedCheckBoxActionPerformed(evt);
            }
        });

        segmentedImagesComboBox.setEnabled(false);
        segmentedImagesComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                segmentedImagesComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(selectSegmentedCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(segmentedImagesComboBox, 0, 146, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(segmentationMethodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(segmentationPreviewButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(segmentationOptionButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectSegmentedCheckBox)
                    .addComponent(segmentedImagesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(segmentationOptionButton)
                    .addComponent(segmentationMethodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(segmentationPreviewButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(measurementsLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(measurementsPanel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(measureButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(measurementsLabel)
                .addGap(18, 18, 18)
                .addComponent(measurementsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(measureButton))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void segmentationPreviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentationPreviewButtonActionPerformed
        String labelBtn = evt.getActionCommand();
        if ("Preview".equals(labelBtn)) {
            logger.info("Clicked on Preview button");
            this.segmentationPreviewButton.setText("Reset");

            for (ChannelContainer channelContainer : selectedChannelContainer) {
                ImagePlus imp = channelContainer.getImagePlus();
                thresholder = ThresholderExecutor.getThresholder(getSelectedThresholdStrategy());
                thresholder.makeBinary(imp.duplicate());
                ImageProcessor processor = imp.getProcessor();
                processor.setThreshold(thresholder.getLowerThreshold(), thresholder.getUpperThreshold(), ImageProcessor.RED_LUT);
                imp.updateAndDraw();
            }
        } else if ("Reset".equals(labelBtn)) {
            logger.info("Clicked on Reset button");
            this.segmentationPreviewButton.setText("Preview");
            for (ChannelContainer channelContainer : selectedChannelContainer) {
                ImagePlus imp = channelContainer.getImagePlus();
                IJ.resetThreshold(imp);
            }
        }
    }//GEN-LAST:event_segmentationPreviewButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        logger.info("Canceled");
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void measureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_measureButtonActionPerformed
        thresholder = ThresholderExecutor.getThresholder(getSelectedThresholdStrategy());
        segmentationConfDialog = ThresholderExecutor.getSegmentationConfigDialog(getSelectedThresholdStrategy());
        logger.info("Start computing");

        if (segmentImages.isEmpty() || segmentationChanged) {
            logger.info("Recompute segments");
            segmentationChanged = false;
            Segmentation segmentation = new Segmentation();
            segmentImages = segmentation.segmentImages(selectedChannelContainer, thresholder);
        }
        MeasurementComputation measComputation = new MeasurementComputation();

        Map<String, List<AbstractMeasurementResult>> afmAnalyzerResult = new HashMap<String, List<AbstractMeasurementResult>>();

        for (int i = 0; i < selectedChannelContainer.size(); i++) {
            ChannelContainer channelContainer = selectedChannelContainer.get(i);

            ImageSegments segmentImage = segmentImages.get(i);
            List<AbstractMeasurementResult> measurementResultsForImage = new ArrayList<AbstractMeasurementResult>();
            for (AbstractMeasurement am : selectedMeasurements) {
                AbstractMeasurementResult computedResult = measComputation.compute(channelContainer, segmentImage, am);
                measurementResultsForImage.add(computedResult);
            }
            afmAnalyzerResult.put(channelContainer.getFile().getName(), measurementResultsForImage);
        }

        boolean increaseRowCounter = true;
        ResultsTable resultTable = new ResultsTable();

        int columnIndex = 0;
        for (Map.Entry<String, List<AbstractMeasurementResult>> entrySet : afmAnalyzerResult.entrySet()) {
            String key = entrySet.getKey();//imageFilename
            for (AbstractMeasurementResult measRes : entrySet.getValue()) {
                for (Integer roiObjectId : measRes.getRoiKeys()) {
                    if (increaseRowCounter) {
                        //Increment row counter just for first measurement
                        resultTable.incrementCounter();
                    }
                    resultTable.setDecimalPlaces(columnIndex, 9);
//                    resultTable.setValue(measRes.getMeasurementName() + " [" + measRes.getUnit() + "^" + measRes.getUnitExponent() + "] ", (roiObjectId - 1), ResultsTable.d2s((Double) measRes.getResultForRoiKey(roiObjectId), 9));
                    resultTable.setValue(measRes.getMeasurementName() + " [" + measRes.getUnit() + "^" + measRes.getUnitExponent() + "] ", (roiObjectId - 1), (Double) measRes.getResultForRoiKey(roiObjectId));
                }
                increaseRowCounter = false;
            }
            columnIndex++;
        }
        resultTable.setPrecision(9);
        resultTable.show("Afm Analyzer Results Table");
    }//GEN-LAST:event_measureButtonActionPerformed

    private void segmentationOptionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentationOptionButtonActionPerformed
        logger.info("Segmentation Option selected");

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                segmentationConfDialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_segmentationOptionButtonActionPerformed

    //Add selected images as segmented
    private void selectSegmentedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSegmentedCheckBoxActionPerformed
        if (selectSegmentedCheckBox.isSelected()) {
            //enable selection of opened image as segmented
            visibilityOfSelectionOfSegmentedImages(true);
            segmentedImagesComboBox.removeAllItems();
            String[] imageTitles = retrieveImageTitles();
            for (int i = 0; i < imageTitles.length; i++) {
                segmentedImagesComboBox.addItem(imageTitles[i]);
            }

            if (imageTitles.length == 0) {
                segmentedImagesComboBox.setEnabled(false);
            }
            selectSegmentedImageManually = true;
            segmentationChanged = false;
        } else {
            //disable selection of opened images
            visibilityOfSelectionOfSegmentedImages(false);
            segmentImages = new ArrayList<ImageSegments>();
            selectSegmentedImageManually = false;
            segmentationChanged = true;
        }
    }//GEN-LAST:event_selectSegmentedCheckBoxActionPerformed

    private void segmentationMethodComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_segmentationMethodComboBoxItemStateChanged
        //Segmentation method was changed so recomputation of segments is needed
        segmentationChanged = true;
    }//GEN-LAST:event_segmentationMethodComboBoxItemStateChanged

    private void segmentedImagesComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_segmentedImagesComboBoxItemStateChanged
        //Selected segmented image from opened images
        if (evt.getStateChange() == ItemEvent.DESELECTED || !selectSegmentedImageManually) {
            return;
        }
        logger.info("Selected segment image: " + (String) evt.getItem());
        ImagePlus image = WindowManager.getImage((String) evt.getItem());
        if (!image.getProcessor().isBinary()) {
            IJ.error("Selected segmented image must be binary");
            return;
        }
        Segmentation segmentation = new Segmentation();
        segmentImages = segmentation.segmentImage(image);
    }//GEN-LAST:event_segmentedImagesComboBoxItemStateChanged

    private void visibilityOfSelectionOfSegmentedImages(
            boolean setSegmentedImagesComboBoxVisible) {
        segmentedImagesComboBox.setEnabled(setSegmentedImagesComboBoxVisible);
        segmentationMethodComboBox.setEnabled(!setSegmentedImagesComboBoxVisible);
        segmentationOptionButton.setEnabled(getSelectedThresholdStrategy().isStrategyWitchConfigurableOptions() && !setSegmentedImagesComboBoxVisible);
        segmentationPreviewButton.setEnabled(!setSegmentedImagesComboBoxVisible);
    }

    private String[] retrieveImageTitles() {
        int[] wList = WindowManager.getIDList();
        if (wList == null) {
            IJ.showMessage("No image is open");
            return new String[]{};
        } else {
            String[] imageTitles = new String[wList.length];
            for (int i = 0; i < wList.length; i++) {
                ImagePlus imp = WindowManager.getImage(wList[i]);
                imageTitles[i] = imp != null ? imp.getTitle() : "";
            }

            logger.trace("Image titles: " + Arrays.toString(imageTitles));
            return imageTitles;
        }
    }

    private Strategies getSelectedThresholdStrategy() {
        return ThresholderExecutor.getStrategy((String) segmentationMethodComboBox.getSelectedItem());
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            openImages();
        }
        super.setVisible(b);
    }

    /**
     * //TODO This method should be deprecated or remove
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AfmAnalyzerFrame().setVisible(true);
                } catch (Exception ex) {
                    logger.info(ex.getMessage(), ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton measureButton;
    private javax.swing.JLabel measurementsLabel;
    private javax.swing.JScrollPane measurementsPanel;
    private javax.swing.JComboBox segmentationMethodComboBox;
    private javax.swing.JButton segmentationOptionButton;
    private javax.swing.JButton segmentationPreviewButton;
    private javax.swing.JComboBox segmentedImagesComboBox;
    private javax.swing.JCheckBox selectSegmentedCheckBox;
    // End of variables declaration//GEN-END:variables
}
