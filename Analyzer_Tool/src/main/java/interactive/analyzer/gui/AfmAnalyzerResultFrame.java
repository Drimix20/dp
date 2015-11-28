package interactive.analyzer.gui;

import interactive.analyzer.graph.BarChart;
import interactive.analyzer.graph.Chart;
import interactive.analyzer.graph.data.DataStatistics;
import interactive.analyzer.result.table.AbstractAfmTableModel;
import interactive.analyzer.result.table.AbstractMeasurementResult;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.listeners.RoiSelectedListener;
import interactive.analyzer.listeners.RowSelectedListener;
import java.awt.Rectangle;
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
public class AfmAnalyzerResultFrame extends JFrame implements RoiSelectedListener {

    private static Logger logger = Logger.getLogger(AfmAnalyzerResultFrame.class);

    //TODO not show other window after recomputation is performed
    //TODO implement export - save as (csv), show as ImageJ's ResultsTable
    //TODO implement as new thread
    //TODO implement setting up values from measurement results
    private TableModel tableModel;
    private List<String> tableHeaderTooltips;
    private List<String> tableColumnNames;
    private Map<String, List<AbstractMeasurementResult>> analyzerValues;
    private List<RowSelectedListener> rowSelectedListeners = new ArrayList<>();

    /**
     * Base constructor. TableModel is automatically set up in initComponents to default
     * implementation. For setting table model created by user constructor with specified
     * parameters should be used.
     * @param tableColumnNames
     * @param tableHeaderTooltips tooltips for header row
     */
    public AfmAnalyzerResultFrame(List<String> tableColumnNames,
            List<String> tableHeaderTooltips) {
        this.tableColumnNames = tableColumnNames;
        this.tableHeaderTooltips = tableHeaderTooltips;
        initComponents();
    }

    /**
     Initialize Result frame with table model defined by user
     * @param tableHeaderTooltips
     @param tableModel table model defined by user
     */
    public AfmAnalyzerResultFrame(List<String> tableHeaderTooltips,
            AbstractAfmTableModel tableModel) {
        this.tableHeaderTooltips = tableHeaderTooltips;
        this.tableModel = tableModel;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        initComponents();
    }

    /**
     Initialize result frame with user defined table and table model
     @param table table defined by user
     @param tableModel table model defined by user
     */
    public AfmAnalyzerResultFrame(JTable table, AbstractAfmTableModel tableModel) {
        this.jTable1 = table;
        this.tableModel = tableModel;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        initComponents();
    }

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

    /**
     * Get column headers without first column for columnSelector. First column always contains id
     * @return column headers
     */
    private String[] getColumnHeadersForCombobox() {
        int size = tableColumnNames.size() + 1;
        String[] columnHeaders = new String[size];
        columnHeaders[0] = "NONE";
        for (int i = 1; i < size; i++) {
            columnHeaders[i] = tableColumnNames.get(i - 1);
        }
        return columnHeaders;
    }

    public boolean addRowSelectedListener(RowSelectedListener listener) {
        return rowSelectedListeners.add(listener);
    }

    public boolean removeRowSelecredListener(RowSelectedListener listener) {
        return rowSelectedListeners.remove(listener);
    }

    public void removeAllRowSelectedListeners() {
        rowSelectedListeners.clear();
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
            jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) {
                        //changes are being still made
                        return;
                    }
                    String rows = "";
                    for (RowSelectedListener listener : rowSelectedListeners) {
                        listener.selectedRowIndexIsChanged(jTable1.getSelectionModel().getLeadSelectionIndex());
                    }
                    for (int r : jTable1.getSelectedRows()) {
                        rows += ", " + r;
                    }
                    logger.trace("Lead: " + jTable1.getSelectionModel().getLeadSelectionIndex() + ", Rows: " + rows);
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
        jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(roiLabel, 0, true)));
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
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AFM Analyzer Results");

        jTable1.setModel(getTableModelInstance());
        jTable1.setAutoResizeMode(jTable1.getAutoResizeMode());
        jTable1.setFillsViewportHeight(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jLabel1.setText("Selected Column:");

        columnComboBox.setModel(new javax.swing.DefaultComboBoxModel(getColumnHeadersForCombobox()));
        columnComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                columnComboBoxItemStateChanged(evt);
            }
        });

        jButton1.setText("Show histogram");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Global");
        jCheckBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(columnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addComponent(jCheckBox1))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(columnComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jCheckBox1)
                    .addContainerGap(19, Short.MAX_VALUE)))
        );

        jMenu1.setText("Export");
        jMenu1.setEnabled(false);

        jMenuItem1.setText("Save as...");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Options");
        jMenu2.setEnabled(false);
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void columnComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_columnComboBoxItemStateChanged
        String selectedItem = (String) columnComboBox.getSelectedItem();
        boolean enabled = !selectedItem.equals("NONE");
        this.jButton1.setEnabled(enabled);
    }//GEN-LAST:event_columnComboBoxItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String selectedColumnName = (String) columnComboBox.getSelectedItem();
        Object[] columnData = ((AbstractAfmTableModel) tableModel).getColumnData(selectedColumnName);

        ObjectFilteringFrame frame = new ObjectFilteringFrame();
        Chart chart = new BarChart();
        chart.loadData(DataStatistics.computeDataSetFromTable(columnData));
        frame.addChart(chart);
        frame.setVisible(true);

        DataStatistics.PairsToString(DataStatistics.computeDataSetFromTable(columnData).getPairs());
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AfmAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AfmAnalyzerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AfmAnalyzerResultFrame frame = new AfmAnalyzerResultFrame(Arrays.asList("A", "b", "C", "D"), new AfmAnalyzerTableModel());
                frame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox columnComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
