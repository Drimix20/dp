package afm.opener.common;

import java.util.List;
import afm.opener.selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public interface ImagePresenter {

    void show(List<ChannelContainer> containers);

    void showAsStack(boolean showStack);

}
