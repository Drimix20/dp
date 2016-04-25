package afm.analyzer.gui;

import afm.analyzer.measurements.AbstractMeasurement;
import afm.analyzer.measurements.list.AreaMeasurement;
import afm.analyzer.measurements.list.AverageIntensityMeasurement;
import afm.analyzer.measurements.list.VolumeMeasurement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class MeasurementsElementManager extends Thread {

    private Logger logger = Logger.getLogger(MeasurementsElementManager.class);
    private List<AbstractMeasurement> selectedMeasurements;
    private final JScrollPane scrollPane;

    public MeasurementsElementManager(JScrollPane scrollPane) {
        super();
        this.scrollPane = scrollPane;
    }

    public void setSelectedMeasurements(
            List<AbstractMeasurement> selectedMeasurements) {
        this.selectedMeasurements = selectedMeasurements;
    }

    @Override
    public void run() {
        super.run();
        createElementForScrollPanel();
    }

    public void createElementForScrollPanel() {
        JPanel backgroundPanel = new JPanel();
        scrollPane.setViewportView(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout(0, 0));

        scrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                scrollPane.repaint();
            }
        });

        JPanel columnpanel = createColumnPanelForOptionElements(backgroundPanel);
        List<AbstractMeasurement> instantiatedClasses = new ArrayList<AbstractMeasurement>();
        //TODO there is need to add an measurement to the list for view on gui
        instantiatedClasses.add(new AreaMeasurement());
        instantiatedClasses.add(new AverageIntensityMeasurement());
        instantiatedClasses.add(new VolumeMeasurement());

        int rowIndex = 1;
        for (AbstractMeasurement instantiatedMeasurement : instantiatedClasses) {
            final MeasurementRowPanel rowPanel = new MeasurementRowPanel(instantiatedMeasurement, selectedMeasurements, false);
            columnpanel.add(rowPanel);
            if (rowIndex % 2 == 0) {
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
            }
            rowIndex++;
        }
    }

    /**
     * Create column panel which will contains horizontal option panel for each measurements
     *
     * @param backgroundPanel panel where column panel will be added
     * @return created column panel
     */
    private JPanel createColumnPanelForOptionElements(JPanel backgroundPanel) {
        JPanel columnPanel = new JPanel();
        backgroundPanel.add(columnPanel, BorderLayout.NORTH);
        columnPanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnPanel.setBackground(Color.ORANGE);
        return columnPanel;
    }

}
