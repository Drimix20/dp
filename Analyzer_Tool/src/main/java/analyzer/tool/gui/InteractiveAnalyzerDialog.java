package analyzer.tool.gui;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.frame.RoiManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class InteractiveAnalyzerDialog extends javax.swing.JDialog {

    private static Logger logger = Logger.getLogger(InteractiveAnalyzerDialog.class);

    private static final String INTERACTIVE__ANALYZER_NAME = "Interactive Analyzer";
    private static final String RESULTS_TABLE_NAME = "Results";
    private String[] imageTitles;
    private ResultsTable resultsTable = null;
    private RoiManager roiManager;

    /**
     * Creates new form AnalyzerToolFrame
     */
    public InteractiveAnalyzerDialog() {
        super();
        initComponents();

        constructImageTitles();
    }

    /**
     * Creates new form AnalyzerToolFrame
     */
    public InteractiveAnalyzerDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        constructImageTitles();
    }

    private void constructImageTitles() {
        int[] wList = WindowManager.getIDList();
        if (wList == null) {
            imageTitles = new String[1];
            imageTitles[0] = "NONE";
        } else {
            imageTitles = new String[wList.length];
            for (int i = 0; i < wList.length; i++) {
                ImagePlus imp = WindowManager.getImage(wList[i]);
                imageTitles[i] = imp != null ? imp.getTitle() : "";
            }
        }
        for (int i = 0; i < imageTitles.length; i++) {
            imageWindowChoice.add(imageTitles[i]);
        }

        logger.trace(Arrays.toString(imageTitles));
    }

    @Override
    public void setVisible(boolean b) {
        if (isValidToRunPlugin()) {
            super.setVisible(b);
        }
    }

    private boolean isValidToRunPlugin() {
        boolean returnVal = true;
        //Check if any image window is visible
        if (imageTitles.length == 1 && imageTitles[0].equals("NONE")) {
            IJ.showMessage(INTERACTIVE__ANALYZER_NAME, "No images are open");
            returnVal = false;
        }

        //Check if results table is visible
        Window window = WindowManager.getWindow(RESULTS_TABLE_NAME);
        resultsTable = Analyzer.getResultsTable();
        if (window == null) {
            IJ.showMessage(INTERACTIVE__ANALYZER_NAME, "No Results table is open");
            returnVal = false;
        } else {
            resultsTableTextField.setText(RESULTS_TABLE_NAME);
            resultsTableTextField.setEditable(false);
        }

        //Retrieve RoiManager
        roiManager = RoiManager.getInstance();
        if (roiManager == null) {
            IJ.showMessage(INTERACTIVE__ANALYZER_NAME, "No RoiManager is open, please import rois by dialog");
            this.openRoisButton.setEnabled(true);
            roiManager = new RoiManager(false);
        }
        return returnVal;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        imageWindowChoice = new java.awt.Choice();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        openRoisButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        resultsTableTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Interactive Analyzer");

        jLabel1.setText("Image Window:");

        jLabel2.setText("Result Table:");

        jLabel3.setText("Images Rois:");

        openRoisButton.setText("Import rois");
        openRoisButton.setEnabled(false);
        openRoisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openRoisButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");

        resultsTableTextField.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imageWindowChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resultsTableTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openRoisButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageWindowChoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6))
                    .addComponent(resultsTableTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(openRoisButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void openRoisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openRoisButtonActionPerformed
        ActionEvent event = new ActionEvent(evt.getSource(), evt.getID(), "Open...");
        roiManager.actionPerformed(event);
    }//GEN-LAST:event_openRoisButtonActionPerformed

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
            java.util.logging.Logger.getLogger(InteractiveAnalyzerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InteractiveAnalyzerDialog dialog = new InteractiveAnalyzerDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private java.awt.Choice imageWindowChoice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton okButton;
    private javax.swing.JButton openRoisButton;
    private javax.swing.JTextField resultsTableTextField;
    // End of variables declaration//GEN-END:variables
}
