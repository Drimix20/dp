package interactive.analyzer.gui;

import ij.ImagePlus;
import interactive.analyzer.graph.HistogramChart;
import interactive.analyzer.graph.Chart;
import interactive.analyzer.graph.data.HistogramDataSet;
import interactive.analyzer.graph.data.DataStatistics;
import interactive.analyzer.graph.data.StatisticsTool;
import interactive.analyzer.histogram.Histogram;
import interactive.analyzer.histogram.HistogramOptionDialog;
import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.result.table.AbstractAfmTableModel;
import interactive.analyzer.result.table.AbstractMeasurementResult;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.listeners.RoiSelectedListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.presenter.InteractiveImageWindow;
import interactive.analyzer.presenter.Roi;
import interactive.analyzer.result.table.DecimalPrecisionRenderer;
import java.awt.Color;
import java.awt.Rectangle;
import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class InteractiveAnalyzerResultFrame extends JFrame implements RoiSelectedListener, ChartSelectionListener {

    private static Logger logger = Logger.getLogger(InteractiveAnalyzerResultFrame.class);

    //TODO not show other window after recomputation is performed
    //TODO implement export - save as (csv), show as ImageJ's ResultsTable
    //TODO implement as new thread
    //TODO implement setting up values from measurement results
    //constants
    private static final int CTRL_WITH_LMB_DOWN = CTRL_DOWN_MASK | BUTTON1_DOWN_MASK;
    private static final int SHIFT_WITH_LMB_DOWN = SHIFT_DOWN_MASK | BUTTON1_DOWN_MASK;

    private ImageWindowI interactiveImageWindow;
    private TableModel tableModel;
    private List<String> tableHeaderTooltips;
    private List<String> tableColumnNames;
    private String selectedColumnName;
    private Map<String, List<AbstractMeasurementResult>> analyzerValues;
    private List<TableSelectionListener> tableSelectionListeners = new ArrayList<>();
    private ObjectFilteringFrame objectFilteringFrame;
    private Chart chart;
    private Color currentSelectionColor = Color.RED;

    private boolean notificationSendViaChartListener = false;
    private boolean shiftIsDown = false;
    private boolean ctrlIsDown = false;
    private boolean justButton1IsDown = true;

    /**
     * Base constructor. TableModel is automatically set up in initComponents to default
     * implementation. For setting table model created by user constructor with specified
     * parameters should be used.
     * @param interactiveImageWindow
     * @param tableColumnNames
     * @param tableHeaderTooltips tooltips for header row
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            List<String> tableColumnNames,
            List<String> tableHeaderTooltips) {
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = tableColumnNames;
        this.tableHeaderTooltips = tableHeaderTooltips;
        initComponents();
        selectedColumnName = tableColumnNames.get(0);
    }

    /**
     Initialize Result frame with table model defined by user
     * @param interactiveImageWindow
     * @param tableHeaderTooltips
     @param tableModel table model defined by user
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            List<String> tableHeaderTooltips,
            AbstractAfmTableModel tableModel) {
        this.tableHeaderTooltips = tableHeaderTooltips;
        this.tableModel = tableModel;
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        selectedColumnName = tableColumnNames.get(0);
        initComponents();
    }

    /**
     Initialize result frame with user defined table and table model
     * @param interactiveImageWindow
     @param table table defined by user
     @param tableModel table model defined by user
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            JTable table,
            AbstractAfmTableModel tableModel) {
        this.jTable1 = table;
        this.tableModel = tableModel;
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        selectedColumnName = tableColumnNames.get(0);
        initComponents();
    }

    /**
     * Get column names from specified table model
     * @param tableModel
     * @return list of column names
     */
    private List<String> getColumnNamesFromTableModel(
            AbstractAfmTableModel tableModel) {
        int columnCount = tableModel.getColumnCount();
        if (columnCount == 0) {
            logger.trace("No columns in tableModel");
            return Collections.EMPTY_LIST;
        }
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i < columnCount; i++) {
            columnNames.add(tableModel.getColumnName(i));
        }
        return columnNames;
    }

    private Color getCurrentSelectionColor() {
        if (objectFilteringFrame == null) {
            return Color.RED;
        } else {
            return objectFilteringFrame.getCurrentSelectionColor();
        }
    }

    /**
     * Get column headers without first column for columnSelector. First column always contains id
     * @return column headers
     */
    private String[] getColumnHeadersForCombobox() {
        String[] columnHeaders = new String[tableColumnNames.size()];
        return tableColumnNames.toArray(columnHeaders);
    }

    public boolean addTableSelectionListener(TableSelectionListener listener) {
        return tableSelectionListeners.add(listener);
    }

    public boolean removeTableSelectionListener(TableSelectionListener listener) {
        return tableSelectionListeners.remove(listener);
    }

    public void removeAllTableSelectionListeners() {
        tableSelectionListeners.clear();
    }

    /**
     If user does not specify used table then instance of default table is used
     @return table instance
     */
    private JTable createTableInstance() {
        if (jTable1 == null) {
            jTable1 = new AfmAnalyzerResultTable();
            jTable1.setColumnSelectionAllowed(false);
            jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            //jTable1.setDefaultRenderer(Object.class, new DecimalPrecisionRenderer());
            jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        //changes are being still made
                        return;
                    }

                    int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
                    Object columnValue = null;
                    if (columnIndex != -1) {
                        columnValue = jTable1.getValueAt(jTable1.getSelectionModel().getLeadSelectionIndex(), columnIndex);
                    } else {
                        logger.trace("Column index is -1 for selected column " + selectedColumnName);
                        //return
                    }

                    for (TableSelectionListener listener : tableSelectionListeners) {
                        if (justButton1IsDown && !notificationSendViaChartListener) {
                            listener.clearAllSelections();
                            notifyClearAllSelections();
                            int rowIndex = jTable1.getSelectionModel().getLeadSelectionIndex();
                            ((AfmAnalyzerResultTable) jTable1).addRowToColorSelection(currentSelectionColor, rowIndex);
                            listener.selectedRowIndexIsChanged(rowIndex, (double) columnValue, currentSelectionColor);
                            notificationSendViaChartListener = false;
                        }
                        if (shiftIsDown) {
                            int minSelectionIndex = jTable1.getSelectionModel().getMinSelectionIndex();
                            int maxSelectionIndex = jTable1.getSelectionModel().getMaxSelectionIndex();
                            for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
                                columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
                                columnValue = jTable1.getValueAt(i, columnIndex);
                                ((AfmAnalyzerResultTable) jTable1).addRowToColorSelection(currentSelectionColor, i);
                                listener.selectedMultipleRows(i, (double) columnValue, currentSelectionColor);
                            }
                        }
                        if (ctrlIsDown) {
                            int rowIndex = jTable1.getSelectionModel().getLeadSelectionIndex();
                            ((AfmAnalyzerResultTable) jTable1).addRowToColorSelection(currentSelectionColor, rowIndex);
                            listener.selectedMultipleRows(jTable1.getSelectionModel().getLeadSelectionIndex(), (double) columnValue, currentSelectionColor);
                        }
                    }
                    jTable1.repaint();
                }

            });
            jTable1.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    notificationSendViaChartListener = false;
                    super.mouseClicked(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    retrieveCurrentSelectionMode(e);

                    super.mousePressed(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    retrieveCurrentSelectionMode(e);

                    super.mouseReleased(e);
                }

                private void retrieveCurrentSelectionMode(MouseEvent e) {
                    int modifiersEx = e.getModifiersEx();
                    if (modifiersEx == SHIFT_WITH_LMB_DOWN) {
                        shiftIsDown = true;
                    } else {
                        shiftIsDown = false;
                    }

                    if (modifiersEx == CTRL_WITH_LMB_DOWN) {
                        ctrlIsDown = true;
                    } else {
                        ctrlIsDown = false;
                    }

                    if (modifiersEx == BUTTON1_DOWN_MASK) {
                        justButton1IsDown = true;
                    } else {
                        justButton1IsDown = false;
                    }
                }

            });
            ((AfmAnalyzerResultTable) jTable1).setHeaderTooltips(this.tableHeaderTooltips);
        }
        return jTable1;
    }

    @Override
    public void notifySelectedRoi(int roiLabel) {
        logger.trace("Selection notification received from roi " + roiLabel);
        jTable1.getSelectionModel().setSelectionInterval(roiLabel - 1, roiLabel - 1);
        jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(roiLabel - 1, 0, true)));
    }

    @Override
    public void notifyBarSelected(double downRangeValue, double upperRangeValue,
            Color color) {
        logger.trace("selection notification received from ObjectFilteringFrame: downR=" + downRangeValue + " , upper=" + upperRangeValue + ", color=" + color);
        currentSelectionColor = color;

        notificationSendViaChartListener = true;
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
            double val = (double) tableModel.getValueAt(rowIndex, columnIndex);
            if (val >= downRangeValue && val <= upperRangeValue) {
                ((AfmAnalyzerResultTable) jTable1).addRowToColorSelection(color, rowIndex);
                jTable1.addRowSelectionInterval(rowIndex, rowIndex);
                for (TableSelectionListener listener : tableSelectionListeners) {
                    listener.selectedMultipleRows(rowIndex, val, color);
                }
            }
        }
        jTable1.repaint();
    }

    @Override
    public void notifyBarDeselected(double downRangeValue,
            double upperRangeValue) {
        logger.trace("deselection notification received from ObjectFilteringFrame: downR=" + downRangeValue + " , upper=" + upperRangeValue);

        notificationSendViaChartListener = true;
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
            double val = (double) tableModel.getValueAt(rowIndex, columnIndex);
            if (val >= downRangeValue && val <= upperRangeValue) {
                ((AfmAnalyzerResultTable) jTable1).removeRowFromSelection(rowIndex);
                jTable1.removeRowSelectionInterval(rowIndex, rowIndex);
                for (TableSelectionListener listener : tableSelectionListeners) {
                    listener.deselectedRow(rowIndex);
                }
            }
        }
        jTable1.repaint();
    }

    @Override
    public void notifyClearAllSelections() {
        jTable1.clearSelection();
        jTable1.repaint();
    }

    /**
     If user does not specify used table model then default table model is used
     @return
     */
    private TableModel getTableModelInstance() {
        if (tableModel == null) {
            tableModel = new AfmAnalyzerTableModel(this.tableColumnNames);
        }
        return tableModel;
    }

    public void setAnalyzerValues(
            Map<String, List<AbstractMeasurementResult>> analyzerResultValues) {
        this.analyzerValues = analyzerResultValues;
        //TODO class will setting results
        List<String> keySet = new ArrayList<>(analyzerResultValues.keySet());
        ((AfmAnalyzerTableModel) tableModel).setValues(analyzerValues.get(keySet.get(0)));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = createTableInstance();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        columnComboBox = new javax.swing.JComboBox();
        showHistogram = new javax.swing.JButton();
        clearSelectionsButton = new javax.swing.JButton();
        cumulHistCheckBox = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        optionMeniItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AFM Analyzer Results");

        jTable1.setModel(getTableModelInstance());
        //set DecimalPrecisonRenderer for all columns but first. First column is column with RowID
        for (int i = 1; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new DecimalPrecisionRenderer());
        }
        jTable1.setAutoResizeMode(jTable1.getAutoResizeMode());
        jTable1.setFillsViewportHeight(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jLabel1.setText("Selected Column:");

        columnComboBox.setModel(new javax.swing.DefaultComboBoxModel(getColumnHeadersForCombobox()));

        showHistogram.setText("Show histogram");
        showHistogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHistogramActionPerformed(evt);
            }
        });

        clearSelectionsButton.setText("Clear selections");
        clearSelectionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSelectionsButtonActionPerformed(evt);
            }
        });

        cumulHistCheckBox.setText("Cumulative");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(clearSelectionsButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(columnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cumulHistCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(showHistogram)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showHistogram)
                    .addComponent(columnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cumulHistCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clearSelectionsButton)
                .addGap(16, 16, 16))
        );

        jMenu1.setText("Export");
        jMenu1.setEnabled(false);

        jMenuItem1.setText("Save as...");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        optionMeniItem.setText("Options");
        optionMeniItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionMeniItemActionPerformed(evt);
            }
        });
        jMenu2.add(optionMeniItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showHistogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHistogramActionPerformed
        selectedColumnName = (String) columnComboBox.getSelectedItem();

        Object[] columnData = null;
        if (jTable1.getSelectedRowCount() == 0) {
            //no row is selected so compute chart from all rows
            logger.trace("Show data for whole table");
            columnData = ((AbstractAfmTableModel) tableModel).getColumnData(selectedColumnName);
        } else {
            //compute chart from selected rows
            logger.trace("Show data for selected rows: [" + jTable1.getSelectedRows() + "]");
            columnData = ((AbstractAfmTableModel) tableModel).getColumnData(selectedColumnName, jTable1.getSelectedRows());
        }

        if (objectFilteringFrame == null) {
            objectFilteringFrame = new ObjectFilteringFrame();
            objectFilteringFrame.addChartSelectionListener(this);
            objectFilteringFrame.addChartSelectionListener((ChartSelectionListener) interactiveImageWindow);
            this.addTableSelectionListener((TableSelectionListener) objectFilteringFrame.getGraphPanel());
        }
        if (chart == null) {
            chart = new HistogramChart();
        }
        clearTableSelectionAndNotifyListeners();
        HistogramDataSet chartData = DataStatistics.computeDataSetFromTable(columnData);
        HistogramOptionDialog histogramDialog = new HistogramOptionDialog(this, true, chartData.getHistogramPairs().size(), chartData.getMinValue(), chartData.getMaxValue());
        histogramDialog.setVisible(true);

        int[] calculatedHistogram;
        if (cumulHistCheckBox.isSelected()) {
            chart.setColumnName(selectedColumnName + " cumulated ");
            calculatedHistogram = Histogram.calculateCumulatedHistogram(columnData, histogramDialog.getXMinValue(), histogramDialog.getXMaxValue(), histogramDialog.getNumbBins());
        } else {
            chart.setColumnName(selectedColumnName);
            calculatedHistogram = Histogram.calculateHistogram(columnData, histogramDialog.getXMinValue(), histogramDialog.getXMaxValue(), histogramDialog.getNumbBins());
        }
        Histogram.printHistogram(calculatedHistogram);
        chartData.setMaxOccurence((int) StatisticsTool.computeMaxValue(calculatedHistogram));
        chartData.setMinOccurence((int) StatisticsTool.computeMinValue(calculatedHistogram));
        chartData.setMeanOccurence((int) StatisticsTool.computeMean(calculatedHistogram));
        chartData.setPairs(Histogram.createHistogramPairsFromHistogram(calculatedHistogram));
        chartData.setBinSize(Histogram.getBinSize());
        chartData.setNumberOfBins(Histogram.getNumberBins());
        System.out.println("maxOccurence= " + chartData.getMaxOccurence());
        System.out.println("minOccurence= " + chartData.getMinOccurence());
        System.out.println("maxVal= " + chartData.getMaxValue());
        System.out.println("minVal= " + chartData.getMinValue());

        chart.setColumnName(selectedColumnName);
        chart.loadData(chartData);
        objectFilteringFrame.addChart(chart);
        objectFilteringFrame.getGraphPanel().updatePaint();
        if (!objectFilteringFrame.isVisible()) {
            objectFilteringFrame.setVisible(true);
        }
    }//GEN-LAST:event_showHistogramActionPerformed

    private void clearSelectionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSelectionsButtonActionPerformed
        logger.debug("");
        clearTableSelectionAndNotifyListeners();
    }//GEN-LAST:event_clearSelectionsButtonActionPerformed

    private void clearTableSelectionAndNotifyListeners() {
        jTable1.clearSelection();
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.clearAllSelections();
        }
    }

    private void optionMeniItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionMeniItemActionPerformed
        OptionsFrame frame = new OptionsFrame(this, true);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);

        ((AbstractAfmTableModel) tableModel).forceFireDataChanged();
    }//GEN-LAST:event_optionMeniItemActionPerformed

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
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InteractiveAnalyzerResultFrame frame = new InteractiveAnalyzerResultFrame(new InteractiveImageWindow(new ImagePlus(), new ArrayList<Roi>()), Arrays.asList("A", "b", "C", "D"), new AfmAnalyzerTableModel());
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearSelectionsButton;
    private javax.swing.JComboBox columnComboBox;
    private javax.swing.JCheckBox cumulHistCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem optionMeniItem;
    private javax.swing.JButton showHistogram;
    // End of variables declaration//GEN-END:variables
}
