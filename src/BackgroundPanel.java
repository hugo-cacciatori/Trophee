import javax.swing.JPanel;
import java.awt.*;

public class BackgroundPanel extends JPanel
{

    private Image background;
 
    public BackgroundPanel(Image background)
    {
        this.background = background;
        setLayout( new BorderLayout() );
    }
 
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
 
        g.drawImage(background, 0, 0, null);
    }
 
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(background.getWidth(this), background.getHeight(this));
    }

}
