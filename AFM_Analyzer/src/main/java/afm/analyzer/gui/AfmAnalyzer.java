package afm.analyzer.gui;

import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.measurements.MeasurementComputation;
import afm.analyzer.segmentation.Segmentation;
import afm.analyzer.segmentation.SegmentedImage;
import afm.analyzer.threshold.ImageThresholdStrategy;
import afm.analyzer.threshold.ThresholderExecutor;
import afm.analyzer.threshold.ThresholderExecutor.Strategies;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzer extends javax.swing.JFrame {

    private static Logger logger = Logger.getLogger(AfmAnalyzer.class);
    private String[] strategiesName;
    private List<AbstractMeasurement> selectedMeasurements;
    private List<ChannelContainer> selectedChannelContainer;
    private ImageThresholdStrategy thresholder;
    private SegmentationConfigDialog segmentationConfDialog;

    /**
     * Creates new form AfmAnalyzer
     */
    public AfmAnalyzer() throws Exception {
        strategiesName = ThresholderExecutor.getStrategiesName();
        initComponents();
        selectedMeasurements = new ArrayList<AbstractMeasurement>();
        selectedChannelContainer = new ArrayList<ChannelContainer>();
        segmentationOptionButton.setEnabled(false);
        segmentationPreviewButton.setEnabled(false);

        MeasurementsElementManager elementManager = new MeasurementsElementManager(measurementsPanel);
        elementManager.setSelectedMeasurements(selectedMeasurements);
        elementManager.run();
    }

    public void setChannels(List<ChannelContainer> selectedChannelContainer) {
        this.selectedChannelContainer = selectedChannelContainer;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        prefilteringLabel = new javax.swing.JLabel();
        prefilteringOptionButton = new javax.swing.JButton();
        segmentationLabel = new javax.swing.JLabel();
        segmentationComboBox = new javax.swing.JComboBox(strategiesName);
        segmentationPreviewButton = new javax.swing.JButton();
        measurementsLabel = new javax.swing.JLabel();
        measurementsPanel = new javax.swing.JScrollPane();
        cancelButton = new javax.swing.JButton();
        measureButton = new javax.swing.JButton();
        segmentationOptionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(290, 655));
        setPreferredSize(new java.awt.Dimension(290, 655));

        prefilteringLabel.setText("Prefiltering");

        prefilteringOptionButton.setText("Options");
        prefilteringOptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickOnPrefilteringOptions(evt);
            }
        });

        segmentationLabel.setText("Segmentation");

        segmentationComboBox.setSelectedItem(ThresholderExecutor.getStrategiesName());
        segmentationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationComboBoxActionPerformed(evt);
            }
        });

        segmentationPreviewButton.setText("Preview");
        segmentationPreviewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationPreviewButtonActionPerformed(evt);
            }
        });

        measurementsLabel.setText("Measurements");

        measurementsPanel.setBackground(new java.awt.Color(255, 153, 0));
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

        segmentationOptionButton.setText("Options");
        segmentationOptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationOptionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(measurementsPanel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 114, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prefilteringOptionButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(segmentationComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(segmentationPreviewButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(measureButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton))
                            .addComponent(segmentationOptionButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prefilteringLabel)
                            .addComponent(segmentationLabel)
                            .addComponent(measurementsLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(prefilteringLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prefilteringOptionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(segmentationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(segmentationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(segmentationOptionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(segmentationPreviewButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(measurementsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(measurementsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(measureButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clickOnPrefilteringOptions(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickOnPrefilteringOptions
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                PrefilteringDialog prefilteringOption = new PrefilteringDialog(null, rootPaneCheckingEnabled);
                prefilteringOption.setVisible(true);
            }
        });
    }//GEN-LAST:event_clickOnPrefilteringOptions

    private void segmentationPreviewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentationPreviewButtonActionPerformed
        String labelBtn = evt.getActionCommand();
        if (checkThatThresholderIsNotSelected()) {
            logger.warn("Thresholder was not selected.");
            return;
        }

        if ("Preview" == labelBtn) {
            logger.info("Clicked on Preview button");
            this.segmentationPreviewButton.setText("Reset");

            for (ChannelContainer channelContainer : selectedChannelContainer) {
                ImagePlus imp = channelContainer.getImagePlus();
                thresholder.makeBinary(imp.duplicate());
                ImageProcessor processor = imp.getProcessor();
                processor.setThreshold(thresholder.getLowerThreshold(), thresholder.getUpperThreshold(), ImageProcessor.RED_LUT);
                imp.updateAndDraw();
            }
        }
        if ("Reset" == labelBtn) {
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
        logger.info("Start computing");

        Segmentation segmentation = new Segmentation();
        List<SegmentedImage> segmentImages = segmentation.segmentImages(selectedChannelContainer, thresholder);
        MeasurementComputation measComputation = new MeasurementComputation();
        //for (SegmentedImage segmImage : segmentImages) {
        for (int i = 0; i < selectedChannelContainer.size(); i++) {
            ImagePlus imagePlus = selectedChannelContainer.get(i).getImagePlus();
            imagePlus.unlock();
            System.out.println("Compute volume for " + imagePlus.getTitle());
            SegmentedImage segmentImage = segmentImages.get(i);
            //TODO implement multiple measurements
            measComputation.compute(selectedChannelContainer.get(i), segmentImage, selectedMeasurements.get(0));
        }
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

    private void segmentationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentationComboBoxActionPerformed
        logger.info("SegmentationComboBox");

        segmentationOptionButton.setEnabled(true);
        segmentationPreviewButton.setEnabled(true);
        thresholder = ThresholderExecutor.getThresholder(getSelectedThresholdStrategy());
        segmentationConfDialog = ThresholderExecutor.getSegmentationConfigDialog(getSelectedThresholdStrategy());
    }//GEN-LAST:event_segmentationComboBoxActionPerformed

    private boolean checkThatThresholderIsNotSelected() {
        if (segmentationComboBox.getSelectedItem() == "Unselected") {
            IJ.showMessage("Unselected threshold option");
            return true;
        }
        return false;
    }

    private Strategies getSelectedThresholdStrategy() {
        return ThresholderExecutor.getStrategy((String) segmentationComboBox.getSelectedItem());
    }

    /**
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
            java.util.logging.Logger.getLogger(AfmAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AfmAnalyzer().setVisible(true);
                } catch (Exception ex) {
                    logger.info(ex.getMessage(), ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton measureButton;
    private javax.swing.JLabel measurementsLabel;
    private javax.swing.JScrollPane measurementsPanel;
    private javax.swing.JLabel prefilteringLabel;
    private javax.swing.JButton prefilteringOptionButton;
    private javax.swing.JComboBox segmentationComboBox;
    private javax.swing.JLabel segmentationLabel;
    private javax.swing.JButton segmentationOptionButton;
    private javax.swing.JButton segmentationPreviewButton;
    // End of variables declaration//GEN-END:variables
}
