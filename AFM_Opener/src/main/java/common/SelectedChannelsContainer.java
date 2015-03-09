package common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class SelectedChannelsContainer {

    private static SelectedChannelsContainer instance;
    private Map<File, List<Integer>> selectedChannels;

    public static synchronized SelectedChannelsContainer newInstance() {
        if (instance == null) {
            instance = new SelectedChannelsContainer();
        }
        return instance;
    }

    public int getNumberOfChannels() {
        int size = 0;
        for (Map.Entry<File, List<Integer>> entrySet : selectedChannels.entrySet()) {
            size += entrySet.getValue().size();
        }
        return size;
    }

    public int getNumberOfFiles() {
        return selectedChannels.size();
    }

    public synchronized void initializeKey(File key) {
        selectedChannels.put(key, new ArrayList<Integer>());
    }

    public synchronized void put(File key, Integer index) {
        List<Integer> values = selectedChannels.get(key);
        values.add(index);
        selectedChannels.put(key, values);
    }
}
