package interactive.analyzer.gui;

import ij.IJ;
import interactive.analyzer.file.tools.ImageFileFilter;
import interactive.analyzer.graph.HistogramChart;
import interactive.analyzer.graph.Chart;
import interactive.analyzer.graph.GraphPanel;
import interactive.analyzer.graph.data.DataStatistics;
import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.data.HistogramBin;
import interactive.analyzer.histogram.HistogramImproved;
import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.options.ObjectFilteringConfiguration;
import interactive.analyzer.result.table.AbstractInteractiveTableModel;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.result.table.TableColorSelectionManager;
import interactive.analyzer.selection.TagManager;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class ObjectFilteringFrame extends javax.swing.JFrame {

    private static Logger logger = Logger.getLogger(ObjectFilteringFrame.class);
    private static final int DIALOG_OK_STATUS = 1;

    private AbstractInteractiveTableModel tableModel;
    private TableColorSelectionManager selectionManager;
    private String selectedColumnName;
    private double minColumnValue;
    private double maxColumnValue;

    /**
     * Creates new frame of ObjectFilteringFrame
     * @param tableModel
     */
    public ObjectFilteringFrame(AbstractInteractiveTableModel tableModel) {
        selectionManager = TableColorSelectionManager.getInstance();
        this.tableModel = tableModel;
        initComponents();

        graphPanel.setSelectionColor(selectionManager.getCurrentSelectionColor());
        graphPanel.setInformativePanel(informativePanel1);
        this.setLocationRelativeTo(null);
        logger.trace("Frame width=" + getWidth() + ", height=" + getHeight() + javax.swing.UIManager.getLookAndFeel());
    }

    public Color getCurrentSelectionColor() {
        return selectionManager.getCurrentSelectionColor();
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
        clearButton = new javax.swing.JButton();
        histogramConfigPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        binSpinner = new javax.swing.JSpinner();
        useDataMinAndMaxCheckbox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        minValLabel = new javax.swing.JLabel();
        xMinSpinner = new javax.swing.JSpinner();
        maxValLabel = new javax.swing.JLabel();
        xMaxSpinner = new javax.swing.JSpinner();
        computeCumulativeHist = new javax.swing.JCheckBox();
        computeHistogramButton = new javax.swing.JButton();
        informationPanel = new InformativePanel();
        informativePanel1 = new interactive.analyzer.gui.InformativePanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        Save = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        optionsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Object's filtering");
        setMinimumSize(new java.awt.Dimension(408, 550));

        graphPanel.setMinimumSize(new java.awt.Dimension(401, 249));
        graphPanel.setPreferredSize(new java.awt.Dimension(401, 249));
        graphPanel.setFocusable(true);
        graphPanel.requestFocusInWindow();

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );

        clearButton.setText("Clear all selections");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Bins:");

        binSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        useDataMinAndMaxCheckbox.setSelected(true);
        useDataMinAndMaxCheckbox.setText("Use column range value");
        useDataMinAndMaxCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                useDataMinAndMaxCheckboxStateChanged(evt);
            }
        });

        jLabel2.setText("or use:");

        minValLabel.setText("Minimal:");
        minValLabel.setEnabled(false);

        xMinSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        xMinSpinner.setEnabled(false);
        xMinSpinner.setMinimumSize(new java.awt.Dimension(55, 20));
        xMinSpinner.setName(""); // NOI18N
        xMinSpinner.setPreferredSize(new java.awt.Dimension(55, 20));

        maxValLabel.setText("Maximal:");
        maxValLabel.setEnabled(false);

        xMaxSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(1.0d)));
        xMaxSpinner.setEnabled(false);

        computeCumulativeHist.setLabel("Compute cumulative histogram");

        computeHistogramButton.setText("Compute");
        computeHistogramButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeHistogramButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout histogramConfigPanelLayout = new javax.swing.GroupLayout(histogramConfigPanel);
        histogramConfigPanel.setLayout(histogramConfigPanelLayout);
        histogramConfigPanelLayout.setHorizontalGroup(
            histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                        .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(computeCumulativeHist))
                            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel2)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(binSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                                .addComponent(useDataMinAndMaxCheckbox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, histogramConfigPanelLayout.createSequentialGroup()
                                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(maxValLabel)
                                    .addComponent(minValLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(xMinSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(xMaxSpinner)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, histogramConfigPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(computeHistogramButton)))
                .addContainerGap())
        );
        histogramConfigPanelLayout.setVerticalGroup(
            histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(histogramConfigPanelLayout.createSequentialGroup()
                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(binSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useDataMinAndMaxCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minValLabel)
                    .addComponent(xMinSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(histogramConfigPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xMaxSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxValLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(computeCumulativeHist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(computeHistogramButton))
        );

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(informativePanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(informativePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        editMenu.setText("Edit");

        optionsMenuItem.setText("Options");
        optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(optionsMenuItem);

        jMenuBar1.add(editMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(histogramConfigPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(histogramConfigPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(clearButton)
                .addContainerGap())
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
                IJ.error("Error while saving graph: \n" + ex.getMessage());
            }
        }
    }//GEN-LAST:event_SaveActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        if (IJ.showMessageWithCancel("Object filtering frame", "Are you sure to delete all selections?")) {
            informativePanel1.setCountFieldValue("");
            informativePanel1.setLowerBoundField("");
            informativePanel1.setUpperBoundField("");
            if (graphPanel != null) {
                graphPanel.clearAllSelectionsEvent();
            }
            TagManager.getInstance().clearAllTags();
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuItemActionPerformed
        ObjectFilteringOptionsDialog dialog = new ObjectFilteringOptionsDialog(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        int returnStatus = dialog.getReturnStatus();
        if (returnStatus == DIALOG_OK_STATUS && graphPanel != null) {
            graphPanel.setBarBorderColor(ObjectFilteringConfiguration.getBarBorderColor());
            graphPanel.setBarBackgroundColor(ObjectFilteringConfiguration.getBarBackgroundColor());
            graphPanel.updatePaint();
            informativePanel1.repaint();
        }
    }//GEN-LAST:event_optionsMenuItemActionPerformed

    private void computeHistogramButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeHistogramButtonActionPerformed
        int binNumber = (Integer) binSpinner.getValue();
        if (binNumber == 0) {
            IJ.showMessage("Count of bin must be greater then zero");
            return;
        }

        double minVal;
        double maxVal;
        if (useDataMinAndMaxCheckbox.isSelected()) {
            minVal = minColumnValue;
            maxVal = maxColumnValue;
        } else {
            minVal = (Double) xMinSpinner.getValue();
            maxVal = (Double) xMaxSpinner.getValue();
        }

        Object[] columnData = ((AbstractInteractiveTableModel) tableModel).getColumnData(selectedColumnName);

        HistogramDataSet chartData = DataStatistics.computeDataSetFromTable(columnData);
        HistogramChart chart = new HistogramChart();

        List<HistogramBin> calculatedHistogram;
        if (computeCumulativeHist.isSelected()) {
            chart.setColumnName(selectedColumnName + " cumulated");
            calculatedHistogram = HistogramImproved.calculateCumulatedHistogram(columnData, minVal, maxVal, binNumber);
        } else {
            calculatedHistogram = HistogramImproved.calculateHistogram(columnData, minVal, maxVal, binNumber);
        }
        chartData.setMinValue(minVal);
        chartData.setMaxValue(maxVal);

        chartData.setMaxOccurence(HistogramImproved.getMaxOccurence());
        chartData.setMinOccurence(HistogramImproved.getMinOccurence());
        chartData.setPairs(calculatedHistogram);

        chartData.setBinSize(HistogramImproved.getBinSize());
        chartData.setNumberOfBins(HistogramImproved.getBinsNumber());
        chart.loadData(chartData);

        graphPanel.setChart(chart);
        graphPanel.updatePaint();
    }//GEN-LAST:event_computeHistogramButtonActionPerformed

    private void useDataMinAndMaxCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_useDataMinAndMaxCheckboxStateChanged
        changeVisibilitiForMinMaxSpinner(!this.useDataMinAndMaxCheckbox.isSelected());
    }//GEN-LAST:event_useDataMinAndMaxCheckboxStateChanged

    private void changeVisibilitiForMinMaxSpinner(boolean isSelected) {
        xMaxSpinner.setEnabled(isSelected);
        xMinSpinner.setEnabled(isSelected);
        maxValLabel.setEnabled(isSelected);
        minValLabel.setEnabled(isSelected);
    }

    public void showWindow(String selectedColumName) {
        this.selectedColumnName = selectedColumName;
        graphPanel.setChart(null);
        graphPanel.updatePaint();

        Object[] columnData = ((AbstractInteractiveTableModel) tableModel).getColumnData(selectedColumnName);
        HistogramDataSet computeDataSetFromTable = DataStatistics.computeDataSetFromTable(columnData);
        binSpinner.setValue(0);
        minColumnValue = computeDataSetFromTable.getMinValue();
        maxColumnValue = computeDataSetFromTable.getMaxValue();

        xMaxSpinner.setValue(maxColumnValue);
        xMinSpinner.setValue(minColumnValue);

        if (!super.isVisible()) {
            super.setVisible(true);
        }
    }

    @Override
    @Deprecated
    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Use showWindow() method instead.");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AfmAnalyzerTableModel afmAnalyzerTableModel = new AfmAnalyzerTableModel(new String[]{"ColumnA"});
                afmAnalyzerTableModel.setValues(new Object[][]{{14.0}, {24.0}, {27.0}, {31.0}, {36.0}, {38.0}, {42.0},
                {51.0}, {53.0}, {54.0}, {57.0}, {59.0}, {61.0}, {63.0}, {64.0}, {67.0}, {69.0}, {62.0}, {75.0}});
                ObjectFilteringFrame frame = new ObjectFilteringFrame(afmAnalyzerTableModel);
                Chart chart = new HistogramChart();
                chart.setColumnName("ColumnA");
                HistogramDataSet dataSet = new HistogramDataSet();
                dataSet.setMinValue(10);
                dataSet.setMaxValue(80);
                dataSet.setMinOccurence(1);
                dataSet.setMaxOccurence(6);
                dataSet.addPair(new HistogramBin(1, 10, 20, 1));
                dataSet.addPair(new HistogramBin(2, 20, 30, 2));
                dataSet.addPair(new HistogramBin(3, 30, 40, 3));
                dataSet.addPair(new HistogramBin(4, 40, 50, 1));
                dataSet.addPair(new HistogramBin(5, 50, 60, 5));
                dataSet.addPair(new HistogramBin(6, 60, 70, 6));
                dataSet.addPair(new HistogramBin(7, 70, 80, 1));
                dataSet.setMedianValue(80);

                chart.loadData(dataSet);
                frame.showWindow("ColumnA");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Save;
    private javax.swing.JSpinner binSpinner;
    private javax.swing.JButton clearButton;
    private javax.swing.JCheckBox computeCumulativeHist;
    private javax.swing.JButton computeHistogramButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private interactive.analyzer.graph.GraphPanel graphPanel;
    private javax.swing.JPanel histogramConfigPanel;
    private javax.swing.JPanel informationPanel;
    private interactive.analyzer.gui.InformativePanel informativePanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel maxValLabel;
    private javax.swing.JLabel minValLabel;
    private javax.swing.JMenuItem optionsMenuItem;
    private javax.swing.JCheckBox useDataMinAndMaxCheckbox;
    private javax.swing.JSpinner xMaxSpinner;
    private javax.swing.JSpinner xMinSpinner;
    // End of variables declaration//GEN-END:variables
}
