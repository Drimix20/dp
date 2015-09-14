package common;

import importer.ImagesToStackConverter;
import ij.ImagePlus;
import java.util.List;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class AfnOpenerImagePresenter implements ImagePresenter {

    private boolean showAsStack;

    @Override
    public void showAsStack(boolean showStack) {
        this.showAsStack = showStack;
    }

    @Override
    public void show(List<ChannelContainer> containers) {
        if (showAsStack) {
            showAllAsStack(containers);
        } else {
            showSingleImage(containers);
        }
    }

    private void showSingleImage(List<ChannelContainer> containers) {
        for (int i = 0; i < containers.size(); i++) {
            containers.get(i).getImagePlus().show();
        }
    }

    private void showAllAsStack(List<ChannelContainer> containers) {
        ImagesToStackConverter converter = new ImagesToStackConverter();
        ImagePlus[] imgArray = new ImagePlus[containers.size()];
        for (int i = 0; i < containers.size(); i++) {
            imgArray[i] = containers.get(i).getImagePlus();
        }
        converter.convertImagesToStack(imgArray);
        converter.getConvertedStack().show();
    }
}
