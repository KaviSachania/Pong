import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
public class BorderComponent extends JComponent
{
    private int frameW;
    private int frameH;
    private static Line2D.Double top = new Line2D.Double();
    private static Line2D.Double bottom = new Line2D.Double();
    private static Line2D.Double right = new Line2D.Double();
    private static Line2D.Double left = new Line2D.Double();

    public BorderComponent()
    {

    }

    public BorderComponent(int frameSize0)
    {
        frameW=frameSize0;
        frameH=frameSize0;
        top = new Line2D.Double(-frameW/2, -((frameH/150)*37)+(frameH/600), frameW/2, -((frameH/150)*37)+(frameH/600));//top border
        bottom = new Line2D.Double(-frameW/2, (frameH/150)*38, frameW/2, (frameH/150)*38);//bottom border
        left = new Line2D.Double(-frameW/2, -frameH/2, -frameW/2, frameH/2);//left border
        right = new Line2D.Double(frameW/2, -frameH/2, frameW/2, frameH/2);//right border

    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(frameW/2, frameH/2);

        g2.setColor(Color.WHITE);
        g2.draw(top);
        g2.draw(bottom);

        g2.setColor(Color.GRAY);
        for (int i = -16; i<16; i++)//short lines getting drawn in intervals down the screen
        {
            Line2D.Double dotted = new Line2D.Double(0, ((frameH/64)*i)+(frameH/150), 0, ((frameH/64)*i)+(frameH/128)+(frameH/150));
            g2.draw(dotted);
        }

    }

    public static Line2D getBorder(int num0)//returns the line so that in can be checked in BallComponent if the ball intersects the line
    {
        int num=num0;
        switch (num)
        {
            case 1:
            {
                return (Line2D)top;
            }
            case 2:
            {
                return (Line2D)bottom;
            }
            case 3:
            {
                return (Line2D)left;
            }
            case 4:
            {
                return (Line2D)right;
            }
            default:
            {
                return (Line2D)top;
            }
        }
    }


}