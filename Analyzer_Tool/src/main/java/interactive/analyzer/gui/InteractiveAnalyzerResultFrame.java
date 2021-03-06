package interactive.analyzer.gui;

import ij.IJ;
import interactive.analyzer.exporter.ITableExporter;
import interactive.analyzer.exporter.ImageExporter;
import interactive.analyzer.exporter.LabeledImageExporter;
import interactive.analyzer.exporter.TextTableExporter;
import static interactive.analyzer.gui.InteractiveAnalyzerResultFrame.TableSelectionMode.*;
import interactive.analyzer.histogram.HistogramOptionDialog;
import interactive.analyzer.listeners.ChartSelectionListener;
import interactive.analyzer.result.table.AbstractInteractiveTableModel;
import interactive.analyzer.listeners.ImageSelectionListener;
import interactive.analyzer.listeners.ImageWindowObjectListener;
import interactive.analyzer.listeners.TableSelectionListener;
import interactive.analyzer.options.ResultTableConfiguration;
import interactive.analyzer.presenter.ImageWindowI;
import interactive.analyzer.presenter.InteractiveImageWindow;
import interactive.analyzer.presenter.Roi;
import interactive.analyzer.result.table.AfmAnalyzerResultTable;
import interactive.analyzer.result.table.AfmAnalyzerTableModel;
import interactive.analyzer.result.table.DecimalPrecisionRenderer;
import interactive.analyzer.result.table.TableColorSelectionManager;
import java.awt.Color;
import java.awt.Polygon;
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
import java.util.Set;
import javax.swing.JDialog;
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
public class InteractiveAnalyzerResultFrame extends JFrame implements ImageSelectionListener, ChartSelectionListener {

    //TODO problem with dragged scrolling
    private static Logger logger = Logger.getLogger(InteractiveAnalyzerResultFrame.class);

    enum TableSelectionMode {

        SINGLE_CLICK, CLICK_WITH_CTRL, CLICK_WITH_SHIFT, DRAGGED_MOVE, CLEAR_SELECTIONS_IN_TABLE, ROW_DESELECTED, NONE;
    }

    private static final int CTRL_WITH_LMB_DOWN = CTRL_DOWN_MASK | BUTTON1_DOWN_MASK;
    private static final int SHIFT_WITH_LMB_DOWN = SHIFT_DOWN_MASK | BUTTON1_DOWN_MASK;

    private ImageWindowI interactiveImageWindow;
    private TableModel tableModel;
    private List<String> tableHeaderTooltips;
    private List<String> tableColumnNames;
    private String selectedColumnName;
    private List<TableSelectionListener> tableSelectionListeners = new ArrayList<TableSelectionListener>();
    private List<ImageWindowObjectListener> imageWindowObjectListeners = new ArrayList<ImageWindowObjectListener>();
    private ObjectFilteringFrame objectFilteringFrame;
    private TableColorSelectionManager selectionManager;

    private TableSelectionMode selectionMode = NONE;
    private boolean notificationSendViaListener = false;
    private int lastSelectedObjectByDragged = -1;
    private boolean mousePressed = false;

    /**
     * Base constructor. TableModel is automatically set up in initComponents to default
     * implementation. For setting table model created by user constructor with specified
     * parameters should be used.
     * @param interactiveImageWindow
     * @param tableColumnNames
     * @param tableHeaderTooltips tooltips for header row
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            List<String> tableColumnNames, List<String> tableHeaderTooltips) {
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = tableColumnNames;
        this.tableHeaderTooltips = tableHeaderTooltips;
        selectedColumnName = tableColumnNames.get(0);
        selectionManager = TableColorSelectionManager.getInstance();
        imageWindowObjectListeners = new ArrayList<ImageWindowObjectListener>();
        imageWindowObjectListeners.add(interactiveImageWindow);
        initComponents();
        ResultTableConfiguration.getInstance().initValues(jTable1.getColumnCount());
    }

    /**
     Initialize Result frame with table model defined by user
     * @param interactiveImageWindow
     * @param tableHeaderTooltips
     @param tableModel table model defined by user
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            List<String> tableHeaderTooltips,
            AbstractInteractiveTableModel tableModel) {
        this.tableHeaderTooltips = tableHeaderTooltips;
        this.tableModel = tableModel;
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        selectedColumnName = tableColumnNames.get(0);
        selectionManager = TableColorSelectionManager.getInstance();
        imageWindowObjectListeners = new ArrayList<ImageWindowObjectListener>();
        imageWindowObjectListeners.add(interactiveImageWindow);
        initComponents();
        ResultTableConfiguration.getInstance().initValues(jTable1.getColumnCount());
    }

    /**
     Initialize result frame with user defined table and table model
     * @param interactiveImageWindow
     @param table table defined by user
     @param tableModel table model defined by user
     */
    public InteractiveAnalyzerResultFrame(ImageWindowI interactiveImageWindow,
            JTable table,
            AbstractInteractiveTableModel tableModel) {
        this.jTable1 = table;
        this.tableModel = tableModel;
        this.interactiveImageWindow = interactiveImageWindow;
        this.tableColumnNames = getColumnNamesFromTableModel(tableModel);
        selectedColumnName = tableColumnNames.get(0);
        selectionManager = TableColorSelectionManager.getInstance();
        imageWindowObjectListeners = new ArrayList<ImageWindowObjectListener>();
        imageWindowObjectListeners.add(interactiveImageWindow);
        initComponents();
        ResultTableConfiguration.getInstance().initValues(jTable1.getColumnCount());
    }

    /**
     * Get column names from specified table model
     * @param tableModel
     * @return list of column names
     */
    private List<String> getColumnNamesFromTableModel(
            AbstractInteractiveTableModel tableModel) {
        int columnCount = tableModel.getColumnCount();
        if (columnCount == 0) {
            logger.trace("No columns in tableModel");
            return Collections.EMPTY_LIST;
        }
        List<String> columnNames = new ArrayList<String>();
        for (int i = 2; i < columnCount; i++) {
            columnNames.add(tableModel.getColumnName(i));
        }
        return columnNames;
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
            jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() || (selectionMode == CLEAR_SELECTIONS_IN_TABLE)) {
                        //changes are being still made
                        return;
                    }

                    boolean needRepaint = false;
                    if (selectionMode.equals(SINGLE_CLICK)) {
                        logger.trace("Single row selection");
                        needRepaint = true;
                        singleRowSelectionInTable(jTable1, tableModel, selectedColumnName);
                    } else if (selectionMode.equals(CLICK_WITH_SHIFT) && !notificationSendViaListener) {
                        needRepaint = true;
                        logger.trace("Multiple selection via pressed shift");
                        //multiple selection via pressed shift on table
                        int minSelectionIndex = jTable1.getSelectionModel().getMinSelectionIndex();
                        int maxSelectionIndex = jTable1.getSelectionModel().getMaxSelectionIndex();
                        for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
                            multipleRowsSelectionInTable(i, jTable1, selectedColumnName);
                        }
                    } else if (selectionMode == CLICK_WITH_CTRL) {
                        needRepaint = true;
                        logger.trace("Multiple selection via pressed ctrl");
                        int rowIndex = jTable1.getSelectionModel().getLeadSelectionIndex();
                        multipleRowsSelectionInTable(rowIndex, jTable1, selectedColumnName);
                    }
                    if (needRepaint) {
                        jTable1.repaint();
                    }
                }
            });
            jTable1.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    notificationSendViaListener = false;
                    mousePressed = false;
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        selectionMode = ROW_DESELECTED;
                        int rowIndex = jTable1.rowAtPoint(e.getPoint());
                        logger.info("Deselection of row " + rowIndex);
                        jTable1.getSelectionModel().removeSelectionInterval(rowIndex, rowIndex);

                        TableColorSelectionManager.getInstance().removeObjectFromSelection(rowIndex);
                        notifyRowDeselected(rowIndex);
                        jTable1.repaint();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    notificationSendViaListener = false;
                    mousePressed = true;
                    retrieveCurrentSelectionMode(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    notificationSendViaListener = false;
                    mousePressed = false;
                    if (selectionMode.equals(DRAGGED_MOVE)) {
                        jTable1.repaint();
                    }
                    retrieveCurrentSelectionMode(e);
                }

                private void retrieveCurrentSelectionMode(MouseEvent e) {
                    int modifiersEx = e.getModifiersEx();

                    if (modifiersEx == SHIFT_WITH_LMB_DOWN) {
                        selectionMode = CLICK_WITH_SHIFT;
                    } else if (modifiersEx == CTRL_WITH_LMB_DOWN) {
                        selectionMode = CLICK_WITH_CTRL;
                    } else if (modifiersEx == BUTTON1_DOWN_MASK) {
                        selectionMode = SINGLE_CLICK;
                    } else {
                        selectionMode = NONE;
                    }
                }

            });
            jTable1.addMouseMotionListener(new MouseAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (mousePressed) {
                        clearTableSelectionAndNotifyListeners();
                    }

                    notificationSendViaListener = false;
                    selectionMode = DRAGGED_MOVE;

                    int leadSelectionRowIndex = jTable1.getSelectionModel().getLeadSelectionIndex();
                    if (leadSelectionRowIndex != lastSelectedObjectByDragged) {
                        logger.trace("Dragged selection on row " + leadSelectionRowIndex);

                        multipleRowsSelectionInTable(leadSelectionRowIndex, jTable1, selectedColumnName);
                        lastSelectedObjectByDragged = jTable1.getSelectionModel().getLeadSelectionIndex();
                    }
                    mousePressed = false;
//                    int lastVisibleRow = getIndexOfLastVisibleRow(jTable1);
//                    logger.warn("Last visible row: " + lastVisibleRow);
//                    if (leadSelectionRowIndex == lastVisibleRow) {
//                        logger.error("Need to translate to other row");
//                        jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(lastVisibleRow + 1, 0, false)));
//                    }
                }

                private int getIndexOfLastVisibleRow(JTable table) {
                    Rectangle visibleRect = table.getVisibleRect();
                    visibleRect.translate(0, visibleRect.height);
                    return table.rowAtPoint(visibleRect.getLocation());
                }

            });
            jTable1.getTableHeader().addMouseListener(new PopupListener());
            ((AfmAnalyzerResultTable) jTable1).setHeaderTooltips(this.tableHeaderTooltips);
        }
        return jTable1;
    }

    private void singleRowSelectionInTable(JTable jTable, TableModel tableModel,
            String selectedColumnName) {
        int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);

        if (columnIndex == -1) {
            logger.trace("Column index is -1");
            return;
        }

        double columnValue = (Double) jTable.getValueAt(jTable.getSelectionModel().getLeadSelectionIndex(), columnIndex);

        clearTableSelection();
        int rowIndex = jTable.getSelectionModel().getLeadSelectionIndex();
        jTable.addRowSelectionInterval(rowIndex, rowIndex);
        ((AfmAnalyzerResultTable) jTable).addObjectToColorSelection(selectionManager.getCurrentSelectionColor(), getObjectIdFromRow(rowIndex));
        if (!notificationSendViaListener) {
            notifySingleRowSelected(rowIndex, columnValue, selectionManager.getCurrentSelectionColor());
        }
    }

    private void multipleRowsSelectionInTable(int rowIndex, JTable jTable,
            String selectedColumnName) {
        int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);

        if (columnIndex == -1) {
            logger.trace("Column index is -1");
            return;
        }

        double columnValue = (Double) jTable1.getValueAt(rowIndex, columnIndex);
        ((AfmAnalyzerResultTable) jTable).addObjectToColorSelection(selectionManager.getCurrentSelectionColor(), getObjectIdFromRow(rowIndex));
        if (!notificationSendViaListener) {
            notifyMultipleRowsSelected(rowIndex, columnValue, selectionManager.getCurrentSelectionColor());
        }
    }

    /**
     * Notify all listeners that specific row with column value was selected in single selection and draw it with color
     * @param rowIndex
     * @param columnValue
     * @param color
     */
    public void notifySingleRowSelected(int rowIndex, double columnValue,
            Color color) {

        Integer roiId = getRoiIdFromRow(rowIndex);
        if (roiId == null) {
            logger.trace("Object from first column is not roiID");
            return;
        }
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.singleRowSelectedEvent(roiId, columnValue, color);
        }
    }

    /**
     Method parse roi ID from specific row. Roi ID is saved in first column in table.
     @param rowIndex
     @return
     */
    private Integer getRoiIdFromRow(int rowIndex) {
        Object roiIdObject = jTable1.getValueAt(rowIndex, AfmAnalyzerResultTable.ID_COLUMN_INDEX);
        Integer roiId = null;
        if (roiIdObject instanceof Number) {
            return (Integer) roiIdObject;
        } else {
            return null;
        }
    }

    /**
     * Notify all listeners that specific row with column value was selected in multiple selection and draw it with color
     * @param rowIndex
     * @param columnValue
     * @param color
     */
    public void notifyMultipleRowsSelected(int rowIndex, double columnValue,
            Color color) {
        Integer roiId = getRoiIdFromRow(rowIndex);
        if (roiId == null) {
            logger.trace("Object from first column is not roiID");
            return;
        }
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.multipleRowsSelectedEvent(roiId, columnValue, color);
        }
    }

    /**
     * Notify all listeners that row index was deselected
     * @param objectId
     */
    public void notifyRowDeselected(int objectId) {
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.rowDeselectedEvent(objectId);
        }
    }

    /**
     * Notify all subscribers to clear all selections
     */
    public void notifyClearAll() {
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.clearAllSelectionsEvent();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="ImageSelectionListener...">
    @Override
    public void selectedRoiEvent(int roiLabel) {
        logger.trace("ImageSelectionListener - Selection notification received from roi " + roiLabel);
        notificationSendViaListener = true;
        selectionMode = SINGLE_CLICK;

        int rowIndex = roiLabel - 1;
        jTable1.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
        jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(rowIndex, 0, true)));
    }

    @Override
    public void selectedMultipleRoiEvent(int roiLabel) {
        logger.trace("ImageSelectionListener - selection notification received from roi " + roiLabel);
        notificationSendViaListener = true;
        selectionMode = CLICK_WITH_CTRL;

        int rowIndex = roiLabel - 1;
        jTable1.getSelectionModel().addSelectionInterval(rowIndex, rowIndex);

        jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(rowIndex, 0, true)));
    }

    @Override
    public void clearAllSelectionsEvent() {
        logger.trace("ImageSelectionListener");
        clearTableSelection();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ChartSelectionListener...">
    //TODO refactore this methods
    /**
     * Method process received notification from ObjectFiltering.
     * @param downRangeValue
     * @param upperRangeValue
     * @param color
     */
    @Override
    public void singleBarSelectedEvent(double downRangeValue,
            double upperRangeValue, Color color) {
        logger.trace("Single selection notification received from ObjectFilteringFrame: downR=" + downRangeValue + " , upper=" + upperRangeValue + ", color=" + color);
        clearTableSelection();
        selectionManager.setCurrentSelectionColor(color);

        int lastRowIndex = 0;
        notificationSendViaListener = true;
        int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            double val = (Double) tableModel.getValueAt(rowIndex, columnIndex);
            if (val >= downRangeValue && val < upperRangeValue) {
                int objectId = getObjectIdFromRow(rowIndex);
                ((AfmAnalyzerResultTable) jTable1).addObjectToColorSelection(color, objectId);
                jTable1.addRowSelectionInterval(rowIndex, rowIndex);

                lastRowIndex = rowIndex;
                for (ImageWindowObjectListener list : imageWindowObjectListeners) {
                    list.multipleRoiSelected(objectId, color);
                }
            }
        }
        if (lastRowIndex != -1) {
            jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(lastRowIndex, 0, true)));
        }
        jTable1.repaint();
    }

    @Override
    public void multipleBarSelectedEvent(double downRangeValue,
            double upperRangeValue, Color color) {
        logger.trace("selection notification received from ObjectFilteringFrame: downR=" + downRangeValue + " , upper=" + upperRangeValue + ", color=" + color);
        selectionManager.setCurrentSelectionColor(color);

        int lastRowIndex = -1;
        notificationSendViaListener = true;
        int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            double val = (Double) tableModel.getValueAt(rowIndex, columnIndex);
            if (val >= downRangeValue && val < upperRangeValue) {
                int objectId = getObjectIdFromRow(rowIndex);
                ((AfmAnalyzerResultTable) jTable1).addObjectToColorSelection(color, objectId);
                jTable1.addRowSelectionInterval(rowIndex, rowIndex);

                lastRowIndex = rowIndex;
                for (ImageWindowObjectListener list : imageWindowObjectListeners) {
                    list.multipleRoiSelected(objectId, color);
                }
            }
        }
        if (lastRowIndex != -1) {
            jTable1.scrollRectToVisible(new Rectangle(jTable1.getCellRect(lastRowIndex, 0, true)));
        }
        jTable1.repaint();
    }

    @Override
    public void barDeselectedEvent(double downRangeValue,
            double upperRangeValue) {
        logger.trace("deselection notification received from ObjectFilteringFrame: downR=" + downRangeValue + " , upper=" + upperRangeValue);

        notificationSendViaListener = true;
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            int columnIndex = ((AfmAnalyzerTableModel) tableModel).getColumnIndexByName(selectedColumnName);
            double val = (Double) tableModel.getValueAt(rowIndex, columnIndex);
            if (val >= downRangeValue && val < upperRangeValue) {
                int objectId = getObjectIdFromRow(rowIndex);
                ((AfmAnalyzerResultTable) jTable1).removeObjectFromSelection(objectId);
                jTable1.removeRowSelectionInterval(rowIndex, rowIndex);
                //Row is deselected and selection id is removed
                setValueToSelectionColumn(tableModel, null, rowIndex);
                for (ImageWindowObjectListener list : imageWindowObjectListeners) {
                    list.roiDeselected(objectId);
                }
            }
        }
        jTable1.repaint();
    }

    private int getObjectIdFromRow(int row) {
        return (Integer) jTable1.getValueAt(row, AfmAnalyzerResultTable.ID_COLUMN_INDEX);
    }

    @Override
    public void clearBarSelectionsEvent() {
        clearTableSelection();
    }
    // </editor-fold>

    /**
     * Method clear all selections in table
     */
    private void clearTableSelection() {
        selectionMode = CLEAR_SELECTIONS_IN_TABLE;
        jTable1.clearSelection();
        //remove all selection in selection column
        for (int rowIndex = 0; rowIndex < jTable1.getRowCount(); rowIndex++) {
            setValueToSelectionColumn(tableModel, null, rowIndex);
        }
        TableColorSelectionManager.getInstance().clearAllSelections();
        jTable1.repaint();
    }

    private void setValueToSelectionColumn(TableModel model, Object value,
            int rowIndex) {
        model.setValueAt(value, rowIndex, AfmAnalyzerResultTable.SELECTION_COLUMN_INDEX);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jTable1 = createTableInstance();
        jPanel1 = new javax.swing.JPanel();
        showHistogram = new javax.swing.JButton();
        clearSelectionsButton = new javax.swing.JButton();
        deleteSelectedButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        saveTableAsMenuItem = new javax.swing.JMenuItem();
        saveLabeledImageAsMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        optionMeniItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Interactive Analyzer - Results");
        setMinimumSize(new java.awt.Dimension(472, 368));

        jTable1.setModel(getTableModelInstance());
        //set DecimalPrecisonRenderer for all columns but first. First column is column with RowID
        for (int i = 1; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new DecimalPrecisionRenderer());
        }
        jTable1.setAutoResizeMode(jTable1.getAutoResizeMode());
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setDragEnabled(true);
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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

        deleteSelectedButton.setText("Delete selected");
        deleteSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(deleteSelectedButton)
                .addGap(64, 64, 64)
                .addComponent(clearSelectionsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(showHistogram))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showHistogram)
                    .addComponent(deleteSelectedButton)
                    .addComponent(clearSelectionsButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Export");

        saveTableAsMenuItem.setText("Save table as...");
        saveTableAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTableAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveTableAsMenuItem);

        saveLabeledImageAsMenuItem.setText("Save labeled image as...");
        saveLabeledImageAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLabeledImageAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveLabeledImageAsMenuItem);

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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showHistogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHistogramActionPerformed
        logger.info("Show histogram");

        logger.warn("Selection in table: " + Arrays.toString(jTable1.getSelectedRows()));

        if (objectFilteringFrame == null) {
            objectFilteringFrame = new ObjectFilteringFrame((AbstractInteractiveTableModel) tableModel);
            objectFilteringFrame.addChartSelectionListener(this);
            objectFilteringFrame.addChartSelectionListener((ChartSelectionListener) interactiveImageWindow);
            this.addTableSelectionListener((TableSelectionListener) objectFilteringFrame.getGraphPanel());
        }

        HistogramOptionDialog histogramDialog = new HistogramOptionDialog(this, true, getColumnHeadersForCombobox());

        histogramDialog.setVisible(true);
        int returnStatus = histogramDialog.getReturnStatus();

        if (returnStatus == 0) {
            logger.trace("Canceled");
            return;
        }

        //TODO jedna se o chtenou funkcionalitu?
        clearTableSelectionAndNotifyListeners();

        selectedColumnName = histogramDialog.getSelectedColumnName();
        objectFilteringFrame.showWindow(selectedColumnName);
    }//GEN-LAST:event_showHistogramActionPerformed

    private void clearSelectionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSelectionsButtonActionPerformed
        logger.debug("");
        clearTableSelectionAndNotifyListeners();
    }//GEN-LAST:event_clearSelectionsButtonActionPerformed

    private void optionMeniItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionMeniItemActionPerformed
        OptionsFrame frame = new OptionsFrame(this, true);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        jTable1.repaint();
        notifyRedrawAll();
    }//GEN-LAST:event_optionMeniItemActionPerformed

    /**
     * This notify all subscribers to redraw graphics
     */
    private void notifyRedrawAll() {
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.repaintAllEvent();
        }
    }

    private void saveTableAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTableAsMenuItemActionPerformed
        new Runnable() {

            @Override
            public void run() {
                ITableExporter exporter = new TextTableExporter();
                exporter.export(interactiveImageWindow.getImageTitle(), jTable1, TableColorSelectionManager.getInstance());
            }
        }.run();
    }//GEN-LAST:event_saveTableAsMenuItemActionPerformed

    private void deleteSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedButtonActionPerformed
        final Set<Integer> rowIds = TableColorSelectionManager.getInstance().getObjectsInSelection();
        if (rowIds.isEmpty()) {
            IJ.showMessage("No selection to remove.");
            logger.trace("No selection to remove");
            return;
        }
        if (!IJ.showMessageWithCancel("", "Are you sure to delete selected objects?")) {
            return;
        }

        ((AbstractInteractiveTableModel) jTable1.getModel()).removeRows(rowIds);
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.removeRoisEvent(rowIds);
        }
    }//GEN-LAST:event_deleteSelectedButtonActionPerformed

    private void saveLabeledImageAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLabeledImageAsMenuItemActionPerformed
        new Runnable() {

            @Override
            public void run() {
                ImageExporter imageExporter = new LabeledImageExporter();
                imageExporter.export(interactiveImageWindow.getImageTitle() + "-labeled", interactiveImageWindow.getAllRoisInImage(), interactiveImageWindow.getImageWidth(), interactiveImageWindow.getImageHeight());
            }

        }.run();
    }//GEN-LAST:event_saveLabeledImageAsMenuItemActionPerformed

    private void clearTableSelectionAndNotifyListeners() {
        clearTableSelection();
        for (TableSelectionListener listener : tableSelectionListeners) {
            listener.clearAllSelectionsEvent();
        }
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
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InteractiveAnalyzerResultFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                InteractiveImageWindow iiw = new InteractiveImageWindow(IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif"), Arrays.asList(new Roi(1, new Polygon(), Color.red, false)));
                AfmAnalyzerTableModel model = new AfmAnalyzerTableModel(Arrays.asList("A", "b", "C", "D"));
                Object[][] values = new Object[][]{{1, 2, 3, 4}, {2, 10, 6, 9}};
                model.setValues(values);
                InteractiveAnalyzerResultFrame frame = new InteractiveAnalyzerResultFrame(iiw, Arrays.asList("A", "b", "C", "D"), model);
                frame.setVisible(true);
            }
        });
    }

    class PopupListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                int column = jTable1.columnAtPoint(e.getPoint());

                if (column == AfmAnalyzerResultTable.SELECTION_COLUMN_INDEX || column == AfmAnalyzerResultTable.ID_COLUMN_INDEX) {
                    logger.trace("Column 0 and 1 hasn't configurable decimal places");
                    return;
                }

                String columnName = jTable1.getColumnName(column);
                logger.info("Right click on header column " + columnName);
                JDialog popup = new DecimalPlacesPopup(column);
                popup.setTitle(columnName);
                popup.setLocation(e.getXOnScreen(), e.getYOnScreen());
                popup.setVisible(true);
                jTable1.repaint();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearSelectionsButton;
    private javax.swing.JButton deleteSelectedButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    private volatile javax.swing.JTable jTable1;
    private javax.swing.JMenuItem optionMeniItem;
    private javax.swing.JMenuItem saveLabeledImageAsMenuItem;
    private javax.swing.JMenuItem saveTableAsMenuItem;
    private javax.swing.JButton showHistogram;
    // End of variables declaration//GEN-END:variables
}
