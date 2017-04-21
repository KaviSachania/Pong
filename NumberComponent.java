import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
public class NumberComponent extends JComponent
{
    private int num1;//player 1 score
    private int num2;//player 2 score
    private int frameW;
    private int frameH;

    public NumberComponent()
    {

    }

    public NumberComponent(int frameSize0, int num10, int num20)//scores are passed in to simply be displayed
    {
        frameW=frameSize0;
        frameH=frameSize0;
        num1=num10;
        num2=num20;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        this.drawNumbers(g2);

    }

    public void drawNumbers(Graphics2D g2)//draws the grass
    {
        int numSize=(int)Math.round(frameW/20);//size
        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, numSize));//font
        g2.drawString(Integer.toString(num1), frameW/30, frameH/20);//draws the player 1 score
        g2.drawString(Integer.toString(num2), (frameW)-(frameW/10), frameH/20);//draws the player 2 score

    }
}