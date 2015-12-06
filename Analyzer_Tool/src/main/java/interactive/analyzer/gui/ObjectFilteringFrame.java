package interactive.analyzer.gui;

import interactive.analyzer.file.tools.ImageFileFilter;
import interactive.analyzer.graph.BarChart;
import interactive.analyzer.graph.Chart;
import interactive.analyzer.graph.GraphPanel;
import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.data.DataStatistics;
import interactive.analyzer.graph.data.HistogramPair;
import interactive.analyzer.listeners.ChartSelectionListener;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ObjectFilteringFrame extends javax.swing.JFrame {

    //TODO dynamic resize margins due to label
    private static Logger logger = Logger.getLogger(ObjectFilteringFrame.class);
    private static final Color DEFAULT_SELECTION_COLOR = Color.red;

    private HistogramDataSet originChartData;
    private Color currentSelectionColor = Color.red;

    /**
     * Creates new frame of ObjectFilteringFrame
     */
    public ObjectFilteringFrame() {
        initComponents();
        graphPanel.setSelectionColor(currentSelectionColor);
        colorThumbnail.setBackground(DEFAULT_SELECTION_COLOR);
        this.setLocationRelativeTo(null);
        logger.trace("Frame width=" + getWidth() + ", height=" + getHeight());
    }

    public void addChart(Chart chart) {
        graphPanel.setChart(chart);
        originChartData = chart.getData();
        graphPanel.updatePaint();
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public boolean addChartSelectionListener(ChartSelectionListener listener) {
        return graphPanel.addChartSelectionListener(listener);
    }

    public boolean removeChartSelectionListener(ChartSelectionListener listener) {
        return graphPanel.removeChartSelectionListener(listener);
    }

    public void removeAllChartSelectionListeners() {
        graphPanel.removeAllChartSelectionListeners();
    }

    /** This method is called from within the constructor to
     * initializeZ the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        graphPanel = new interactive.analyzer.graph.GraphPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tagNameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        colorThumbnail = new javax.swing.JPanel();
        colorChooserButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        addTagButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        Save = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        sortingMenuItem = new javax.swing.JRadioButtonMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Object's filtering");

        graphPanel.setMinimumSize(new java.awt.Dimension(401, 249));
        graphPanel.setPreferredSize(new java.awt.Dimension(401, 249));
        graphPanel.setFocusable(true);
        graphPanel.requestFocusInWindow();

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tags"));

        jLabel1.setText("Name:");

        jLabel2.setText("Description:");

        jLabel3.setText("Color:");

        colorThumbnail.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout colorThumbnailLayout = new javax.swing.GroupLayout(colorThumbnail);
        colorThumbnail.setLayout(colorThumbnailLayout);
        colorThumbnailLayout.setHorizontalGroup(
            colorThumbnailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        colorThumbnailLayout.setVerticalGroup(
            colorThumbnailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        colorChooserButton.setText("Choose");
        colorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooserButtonActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tagNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(colorThumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(colorChooserButton))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tagNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colorThumbnail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colorChooserButton))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)))
        );

        deleteButton.setText("Delete");

        saveButton.setText("Save");

        addTagButton.setText("Add");

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addTagButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(clearButton)
                        .addGap(18, 18, 18)
                        .addComponent(addTagButton)
                        .addGap(18, 18, 18)
                        .addComponent(saveButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        fileMenu.setText("File");

        Save.setText("Save graph as...");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        fileMenu.add(Save);

        jMenuBar1.add(fileMenu);

        viewMenu.setText("View");

        sortingMenuItem.setText("Sorting");
        sortingMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortingMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(sortingMenuItem);

        jMenuBar1.add(viewMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        JFileChooser fc = new JFileChooser();

        fc.removeChoosableFileFilter(fc.getFileFilter());
        for (FileFilter filter : graphPanel.getFileFilters()) {
            fc.addChoosableFileFilter(filter);
        }
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            try {
                graphPanel.save(file, (ImageFileFilter) fc.getFileFilter());
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_SaveActionPerformed

    private void sortingMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortingMenuItemActionPerformed
        if (sortingMenuItem.isSelected()) {
            HistogramDataSet dataCopy = DataStatistics.createNewInstanceOfData(originChartData);
            dataCopy.sortHistogramPairs();
            graphPanel.getChart().loadData(dataCopy);
            graphPanel.updatePaint();
        } else {
            graphPanel.getChart().loadData(originChartData);
            graphPanel.updatePaint();
        }
    }//GEN-LAST:event_sortingMenuItemActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        graphPanel.clearAllSelections();

    }//GEN-LAST:event_clearButtonActionPerformed

    private void colorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorChooserButtonActionPerformed
        currentSelectionColor = JColorChooser.showDialog(null,
                "Color chooser", DEFAULT_SELECTION_COLOR);
        colorThumbnail.setBackground(currentSelectionColor);
        graphPanel.setSelectionColor(currentSelectionColor);
    }//GEN-LAST:event_colorChooserButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ObjectFilteringFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ObjectFilteringFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ObjectFilteringFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ObjectFilteringFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ObjectFilteringFrame frame = new ObjectFilteringFrame();
                Chart chart = new BarChart();
                chart.setColumnName("ColumnA");
                HistogramDataSet dataSet = new HistogramDataSet();
                dataSet.setMinValue(35);
                dataSet.setMaxValue(140);
                dataSet.setMinOccurence(1);
                dataSet.setMaxOccurence(6);
                dataSet.addPair(new HistogramPair(1, 60, 1));
                dataSet.addPair(new HistogramPair(2, 40, 2));
                dataSet.addPair(new HistogramPair(3, 80, 3));
                dataSet.addPair(new HistogramPair(4, 120, 1));
                dataSet.addPair(new HistogramPair(5, 140, 5));
                dataSet.addPair(new HistogramPair(6, 35, 6));
                dataSet.addPair(new HistogramPair(7, 110, 1));
                dataSet.setMedianValue(80);

                chart.loadData(dataSet);
                frame.addChart(chart);
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Save;
    private javax.swing.JButton addTagButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton colorChooserButton;
    private javax.swing.JPanel colorThumbnail;
    private javax.swing.JButton deleteButton;
    private javax.swing.JMenu fileMenu;
    private interactive.analyzer.graph.GraphPanel graphPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton saveButton;
    private javax.swing.JRadioButtonMenuItem sortingMenuItem;
    private javax.swing.JTextField tagNameField;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
}
