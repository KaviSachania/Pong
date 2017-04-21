import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class BackgroundComponent extends JComponent implements ActionListener
{
    private int x;
    private int y;
    private int frameW;
    private int frameH;
    
    public BackgroundComponent()
    {
        
    }

    public BackgroundComponent(int frameSize0)
    {
        frameW=frameSize0;
        frameH=frameSize0;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        this.drawBackground(g2);

    }

    public void drawBackground(Graphics2D g2)//draws the grass
    {

        Rectangle bg = new Rectangle(0, 0, frameW, frameH);//rectangle the size of the entire frame
        g2.setColor(Color.BLACK);//black background
        g2.draw(bg);
        g2.fill(bg);

    }
    
    public void actionPerformed(ActionEvent e)
    {
        this.repaint();//needs to be repainted so that it continues to appear below to ball and paddles
    }

}