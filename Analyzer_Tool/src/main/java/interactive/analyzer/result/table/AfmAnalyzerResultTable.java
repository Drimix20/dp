package interactive.analyzer.result.table;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Drimal
 */
public class AfmAnalyzerResultTable extends JTable {

    //TODO implement funcionality to changing header text(see http://codewizpt.blogspot.cz/2011/03/change-jtable-column-header-text.html)
    //TODO implement functionality for set tooltip headers
    //TODO maybe will be needed List<MeasurementResult>
    private List<String> headerTooltips = new ArrayList<>();

    public List<String> getTooltips() {
        return headerTooltips;
    }

    public void setHeaderTooltips(List<String> headerTooltips) {
        this.headerTooltips = headerTooltips;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        return super.getToolTipText(e);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                //String tip = null;
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return headerTooltips.get(realIndex);
            }
        };
    }

    public void setCellRenderers() {
        int columnCount = columnModel.getColumnCount();
        if (columnCount > 1) {
            columnModel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
        }
        for (int i = 1; i < columnCount; i++) {
            columnModel.getColumn(i).setCellRenderer(new DoublePrecisionlRenderer());
        }
    }

}
