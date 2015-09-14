package common;

import java.util.List;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public interface ImagePresenter {

    void show(List<ChannelContainer> containers);

    void showAsStack(boolean showStack);

}
