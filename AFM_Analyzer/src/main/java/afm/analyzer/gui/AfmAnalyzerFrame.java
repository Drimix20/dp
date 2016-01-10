package afm.analyzer.gui;

import interactive.analyzer.gui.InteractiveAnalyzerResultFrame;
import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.measurements.MeasurementComputation;
import interactive.analyzer.presenter.InteractiveImageWindow;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.result.table.AbstractMeasurementResult;
import afm.analyzer.segmentation.Segmentation;
import interactive.analyzer.selection.ImageSegments;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.TableSelectionListener;
import afm.analyzer.threshold.ImageThresholdStrategy;
import afm.analyzer.threshold.ThresholderExecutor;
import afm.analyzer.threshold.ThresholderExecutor.Strategies;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import interactive.analyzer.InteractiveAnalyzer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;
import afm.opener.selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerFrame extends javax.swing.JFrame {

    //TODO pokud uzivatel na panelu segmentace zaklikne checkbox, tak se enabluje combobox a uzivatel si vybere okno se segmentovanym obrazem
    // zaroven dojde k disablovani metody segmentace, options a preview
    private static Logger logger = Logger.getLogger(AfmAnalyzerFrame.class);
    private String[] strategiesName;
    private List<AbstractMeasurement> selectedMeasurements;
    private List<ChannelContainer> selectedChannelContainer;
    private ImageThresholdStrategy thresholder;
    private SegmentationConfigDialog segmentationConfDialog;
    private ImageWindowI analyzerImageWindow;

    public AfmAnalyzerFrame() {
        this(new InteractiveImageWindow());
    }

    /**
     * Creates new form AfmAnalyzer
     * @param imageWindow
     */
    public AfmAnalyzerFrame(ImageWindowI imageWindow) {
        if (imageWindow == null) {
            throw new IllegalArgumentException("Image window must be specified.");
        }
        strategiesName = ThresholderExecutor.getStrategiesName();
        initComponents();
        selectedMeasurements = new ArrayList<AbstractMeasurement>();
        selectedChannelContainer = new ArrayList<ChannelContainer>();
        segmentationOptionButton.setEnabled(false);
        segmentationPreviewButton.setEnabled(false);
        analyzerImageWindow = imageWindow;

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
        measurementsLabel = new javax.swing.JLabel();
        measurementsPanel = new javax.swing.JScrollPane();
        cancelButton = new javax.swing.JButton();
        measureButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        segmentationComboBox = new javax.swing.JComboBox(strategiesName);
        segmentationOptionButton = new javax.swing.JButton();
        segmentationPreviewButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AFM Analyzer");
        setMinimumSize(new java.awt.Dimension(290, 655));

        prefilteringLabel.setText("Prefiltering");

        prefilteringOptionButton.setText("Options");
        prefilteringOptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickOnPrefilteringOptions(evt);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Segmentation"));

        segmentationComboBox.setSelectedItem(ThresholderExecutor.getStrategiesName());
        segmentationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentationComboBoxActionPerformed(evt);
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

        jLabel1.setText("Method");

        jCheckBox1.setText("Select Segmented");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(segmentationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(segmentationOptionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(segmentationPreviewButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(segmentationOptionButton)
                    .addComponent(segmentationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(measurementsPanel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 155, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prefilteringOptionButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(measureButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prefilteringLabel)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 114, Short.MAX_VALUE)
                        .addComponent(measurementsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(measurementsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(measureButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
        //TODO fix threshold preview
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
        if (thresholder == null) {
            //TODO validate all parameters
            IJ.showMessage("Segmentation is not selected");
            return;
        }

        logger.info("Start computing");

        Segmentation segmentation = new Segmentation();
        List<ImageSegments> segmentImages = segmentation.segmentImages(selectedChannelContainer, thresholder);
        MeasurementComputation measComputation = new MeasurementComputation();
        //TODO create abstract class as AnalyzerResult and its implementation (because of usage in AfmAnalyzerFrame)
        Map<String, List<AbstractMeasurementResult>> afmAnalyzerResult = new HashMap<String, List<AbstractMeasurementResult>>();

        List<String> resultTableHeader = new ArrayList<>();
        resultTableHeader.add("id");
        //for (ImageSegments segmImage : segmentImages) {
        for (int i = 0; i < selectedChannelContainer.size(); i++) {
            ChannelContainer channelContainer = selectedChannelContainer.get(i);
            ImagePlus imagePlus = channelContainer.getImagePlus();

            ImageSegments segmentImage = segmentImages.get(i);
            //TODO implement multiple measurements
            List<AbstractMeasurementResult> measurementResultsForImage = new ArrayList<>();
            for (AbstractMeasurement am : selectedMeasurements) {
                AbstractMeasurementResult computedResult = measComputation.compute(selectedChannelContainer.get(i), segmentImage, am);
                measurementResultsForImage.add(computedResult);
                resultTableHeader.add(am.getLabel() + " [nm]");
            }
            afmAnalyzerResult.put(channelContainer.getFile().getName(), measurementResultsForImage);
        }
        InteractiveAnalyzerResultFrame resultFrame = new InteractiveAnalyzerResultFrame(resultTableHeader, Collections.EMPTY_LIST);
        resultFrame.setAnalyzerValues(afmAnalyzerResult);
        analyzerImageWindow.setImagesSegments(segmentImages);
        resultFrame.addTableSelectionListener((TableSelectionListener) this.analyzerImageWindow);
        analyzerImageWindow.addRoiSelectedListener((ImageSelectionListener) resultFrame);

        InteractiveAnalyzer analyzer = new InteractiveAnalyzer(resultFrame, analyzerImageWindow);
        analyzer.run();
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

        //TODO repair preview
        segmentationOptionButton.setEnabled(false);
        segmentationPreviewButton.setEnabled(false);
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
                    new AfmAnalyzerFrame(new InteractiveImageWindow()).setVisible(true);
                } catch (Exception ex) {
                    logger.info(ex.getMessage(), ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton measureButton;
    private javax.swing.JLabel measurementsLabel;
    private javax.swing.JScrollPane measurementsPanel;
    private javax.swing.JLabel prefilteringLabel;
    private javax.swing.JButton prefilteringOptionButton;
    private javax.swing.JComboBox segmentationComboBox;
    private javax.swing.JButton segmentationOptionButton;
    private javax.swing.JButton segmentationPreviewButton;
    // End of variables declaration//GEN-END:variables
}
