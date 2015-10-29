package common;

import configuration.PluginConfiguration;
import gui.ChannelListElement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.io.File;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;
import selector.ChannelContainer;

/**
 * Manager object for element option in image option panel
 *
 * @author Drimal
 */
public class ImageOptionManager extends Thread {

    private Logger logger = Logger.getLogger(ImageOptionManager.class);
    private final JScrollPane scrollPane;
    private List<ChannelContainer> originalChannels;
    private List<ChannelContainer> selectedChannels;
    private boolean selectAll;

    public ImageOptionManager(JScrollPane scrollPane,
            List<ChannelContainer> originalChannels,
            List<ChannelContainer> selectedChannels) {
        super();
        this.scrollPane = scrollPane;
        this.originalChannels = originalChannels;
        this.selectedChannels = selectedChannels;
    }

    public List<ChannelContainer> getSelectedChannels2() {
        return selectedChannels;
    }

    /**
     * Method select check button for all elements in image option panel. If boolean parameter is true then all elements will be selected else
     * elements will be unselected.
     *
     * @param select selection flag
     */
    public void selectAllImages(boolean select) {
        selectAll = select;
    }

    @Override
    public void run() {
        createElementsForScrollPanel();
    }

    public void createElementsForScrollPanel() {
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

        int rowIndex = 1;
        for (int i = 0; i < originalChannels.size(); i++) {
            ChannelContainer channelContainer = originalChannels.get(i);
            File parentFile = channelContainer.getFile();

            String channelName = (String) channelContainer.getMetadata().getTagValue(PluginConfiguration.getChannelNameTag());
            final ChannelListElement rowPanel = new ChannelListElement(channelContainer, selectedChannels, selectAll);
            columnpanel.add(rowPanel);
            if (rowIndex % 2 == 0) {
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
            }
            rowIndex++;
        }
    }

    /**
     * Create column panel which will contains horizontal option panels for each image
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
