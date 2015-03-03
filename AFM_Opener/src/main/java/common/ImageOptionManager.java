package common;

import gui.ChanelListElement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Manager object for element option in image option panel
 *
 * @author Drimal
 */
public class ImageOptionManager extends Thread{

    private final JScrollPane scrollPane;
    private Map<File, List<Integer>> channels;
    private Map<File, List<Integer>> selectedChannels;
    private boolean selectAll;

    public ImageOptionManager(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Map<File, List<Integer>> getChannels() {
        return channels;
    }

    public void setChannels(Map<File, List<Integer>> channels) {
        this.channels = channels;
    }

    public Map<File, List<Integer>> getSelectedChannels() {
        return selectedChannels;
    }

    /**
     * Method select check button for all elements in image option panel. If
     * boolean parameter is true then all elements will be selected else
     * elements will be unselected.
     *
     * @param select selection flag
     */
    public void selectAllImages(boolean select) {
        selectAll = select;
    }

    @Override
    public void run() {
        createElementForScrollPanel();
    }

    public void createElementForScrollPanel() {
        selectedChannels = new TreeMap<File, List<Integer>>();
        for (Map.Entry<File, List<Integer>> entry : channels.entrySet()) {
                File key = entry.getKey();
                selectedChannels.put(key, new ArrayList<Integer>());
        }

        JPanel backgroundPanel = new JPanel();
        scrollPane.setViewportView(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout(0, 0));

        scrollPane.getViewport().addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                scrollPane.repaint();
            }
        });

        JPanel columnpanel = new JPanel();
        backgroundPanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.ORANGE);

        int fileIndex=1;

        for (Map.Entry<File, List<Integer>> entry : channels.entrySet()) {
            File file = entry.getKey();
            int i=1;
            for(Integer value : entry.getValue()){
                final ChanelListElement rowPanel = new ChanelListElement("Image_"+fileIndex+"/"+i, file, value, selectedChannels, selectAll);

                columnpanel.add(rowPanel);
                if(i%2==0){
                    rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
                }
                i++;
            }
            fileIndex++;
        }
    }
}
