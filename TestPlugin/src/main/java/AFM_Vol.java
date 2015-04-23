
import java.awt.*;
import java.awt.event.*;
import ij.plugin.frame.*;
import ij.*;
import ij.gui.*;

public class AFM_Vol extends PlugInFrame implements ActionListener {

    private Panel panel;
    static private TextArea ta;
    private RoiManager roim;
    private int previousID;
    private static Frame instance;

    public AFM_Vol() {
        super("AFM_Volume");
        if (instance != null) {
            instance.toFront();
            return;
        }
        instance = this;
        //roim = new RoiManager();
        addKeyListener(IJ.getInstance());
        setLayout(new FlowLayout());
        ta = new TextArea(15, 20);
        ta.setEditable(false);
        add(ta);
        panel = new Panel();

        panel.setLayout(new GridLayout(4, 1));
        addButton("Open");
        addButton("Threshold");
        addButton("Volume");
        addButton("Reset");
        add(panel);
        pack();
        GUI.center(this);
        setVisible(true);
    }

    final void addButton(String label) {
        Button b = new Button(label);
        b.addActionListener(this);
        b.addKeyListener(IJ.getInstance());
        panel.add(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ImagePlus imp = WindowManager.getCurrentImage();
        if (imp == null) {
            String label = e.getActionCommand();
            //IJ.showMessage(label);
            if ("Open".equals(label)) {
                IJ.showStatus("Opening...");
                new Runner(label, null);
                return;
            }
            IJ.beep();
            IJ.showStatus("No image");
            previousID = 0;
        }
        if (!imp.lock()) {
            previousID = 0;
            return;
        }
        int id = imp.getID();
        if (id != previousID) {
            imp.getProcessor().snapshot();
        }
        previousID = id;
        String label = e.getActionCommand();
        if (label == null) {
            return;
        }
        new Runner(label, imp);
    }

    @Override
    public void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            instance = null;
        }
    }

    static void setText(String s) {
        ta.append(s);
    }

    static void resetText() {
        ta.setText("");
    }

}
