import javax.swing.JDialog;

import javax.swing.*;
import java.awt.*;

public class ChildWindow extends JDialog{
    

    public ChildWindow(int w, int h){
        super(View.getInstance(), true);
        init(w, h);
    }
    public ChildWindow(){
        super(View.getInstance(), true);
        init();
    }

    private void init(int w, int h){
        this.setSize(w,h);
        this.setTitle("Attention !");
        this.setResizable(false);
        centerOnScreen(this, true);
    }
    private void init(){
        this.setSize(380,100);
        this.setTitle("Attention !");
        this.setResizable(false);
        centerOnScreen(this, true);
    }
    private void init(Dimension pref){
        this.setPreferredSize(pref);
        this.setTitle("Attention !");
        this.setResizable(false);
        centerOnScreen(this, true);
    }

    public void centerOnScreen(final Component c, final boolean absolute) {
        final int width = c.getWidth();
        final int height = c.getHeight();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width / 2) - (width / 2);
        int y = (screenSize.height / 2) - (height / 2);
        if (!absolute) {
            x /= 2;
            y /= 2;
        }
        c.setLocation(x, y);
    }
}
