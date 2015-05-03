
import gui.AfmOpenerFrame;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import selector.ChannelContainer;

class AfmOpenerRunnable implements Runnable {

    private AfmOpenerFrame frame;
    private CountDownLatch latch;
    private boolean disposeAfterOpen;

    public AfmOpenerRunnable(CountDownLatch latch, boolean disposeAfterOpen) {
        this.latch = latch;
        this.disposeAfterOpen = disposeAfterOpen;
    }

    public List<ChannelContainer> getSelectedChannels() {
        return frame.getSelectedChannelContainer();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public boolean isDisposeAfterOpne() {
        return disposeAfterOpen;
    }

    @Override
    public void run() {
        frame = new AfmOpenerFrame(latch, disposeAfterOpen);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
