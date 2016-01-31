package afm.opener.selector;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drimal
 */
public class SelectedChannelsContainer {

    private static SelectedChannelsContainer instance;
    private List<ChannelContainer> channelContainers = new ArrayList<ChannelContainer>();

    private SelectedChannelsContainer() {
    }

    public static synchronized SelectedChannelsContainer newInstance() {
        if (instance == null) {
            instance = new SelectedChannelsContainer();
        }
        return instance;
    }

    public int getNumberOfChannels() {
        return channelContainers.size();
    }

    public synchronized void makeEmpty() {
        channelContainers = new ArrayList<ChannelContainer>();
    }

    public synchronized void add(ChannelContainer channel) {
        channelContainers.add(channel);
    }
}
