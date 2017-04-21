
/**
 * Write a description of class SquirrelComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.Timer;
import java.io.*;
import sun.audio.*;
public class BallComponent extends JComponent implements ActionListener
{
    private int frameW;
    private int frameH;
    private double x;
    private double y;
    private int count;
    public static int direction;
    private int speed;
    private boolean point;
    private boolean point1;
    private boolean point2;
    private boolean paddleBounce;
    private int countHold;
    private Random gen = new Random();
    BorderComponent border = new BorderComponent();
    AudioStream audioStream1;
    AudioStream audioStream2;
    AudioStream audioStream3;
    public BallComponent()
    {

    }

    public BallComponent(int frameSize)
    {
        frameW=frameSize;
        frameH=frameSize;
        x=0;
        y=0;
        speed=150;//at home
        //speed=85;//at school
        
        border = new BorderComponent(frameSize);//this border is not to be painted, it is just here so that the ball can stay within the border
        countHold=0;//used to make sure that the ball has had enough time to move away from a paddle before checking if it bounces again

        boolean run = true;
        while (run==true)
        {
            direction=gen.nextInt(360);//random angle
            if ((direction>=87)&&(direction<=93))//near directly to the right
            {
                run=false;
            }
            if ((direction>=267)&&(direction<273))//near directly to the left
            {
                run=false;
            }

            if ((direction==90)||(direction==270))//cannot be exactly to the left or right
            {
                run=true;
            }

        }
        
        /**
        try
        {
            InputStream in1 = new FileInputStream("ping_pong_8bit_peeeeeep.wav");//gets the file location, sound for bouncing off a wall
            audioStream1 = new AudioStream(in1);//audioStream uses the file location for that sound
            InputStream in2 = new FileInputStream("ping_pong_8bit_plop.wav");//gets the file location, sound for bouncing off a wall
            audioStream2 = new AudioStream(in2);
            InputStream in3 = new FileInputStream("ping_pong_8bit_beeep.wav");//gets the file location, sound for bouncing off a wall
            audioStream3 = new AudioStream(in3);
        }
        catch (Exception e)//if the file location cannot be found
        {
            //do nothing
        }
        */
    }

    public void paintComponent(Graphics g)//draws a bird that flaps its wing and flys across the frame
    {
        if (count>100)//gives the ball a delay before being released from the middle of the screen
        {

            Graphics2D g2 = (Graphics2D) g;

            g2.translate(frameW/2, frameH/2);//middle origin

            this.drawBall(g2);//draws the ball
        }
    }

    public void drawBall(Graphics2D g2)
    {

        Ellipse2D.Double ball = new Ellipse2D.Double(x-frameW/100, (-y)-frameH/100, frameW/50, frameH/50);//ball has diameter of frameSize/50
        g2.setColor(Color.WHITE);
        g2.draw(ball);
        g2.fill(ball);

        paddleBounce=false;
        if (ball.intersects(PaddleComponent.getPaddle1().getX(), PaddleComponent.getPaddle1().getY(),PaddleComponent.getPaddle1().getWidth(),PaddleComponent.getPaddle1().getHeight())==true)
        {
            paddleBounce=true;//if the ball intersects a rectangle of these points (points are from the respective paddle)
        }
        if (ball.intersects(PaddleComponent.getPaddle2().getX(), PaddleComponent.getPaddle2().getY(),PaddleComponent.getPaddle2().getWidth(),PaddleComponent.getPaddle2().getHeight())==true)
        {
            paddleBounce=true;
        }

    }

    public void checkBounce()
    {

        if ((paddleBounce==true)&&((count>countHold+100)||(countHold==0)))//if the ball needs to bounce off a paddle, and its been enough time since the last bounce (to avoid double bounces) or its the very first bounce 
        {
            if ((x<=((frameW/2)-(frameW/12))+(frameW/200))||(x>=(-((frameW/2)-(frameW/12)))+(frameW/200)))//makes sure the ball isn't behind the paddle
            {
                this.bouncePaddle();//change of direction for bouncing off paddles
            }

        }

        //top and bottom border, bounce off the wall
        for (int i=1; i<=2; i++)//checks top then bottom
        {
            if ((BorderComponent.getBorder(i)).ptLineDist(x, y)<=frameW/100)//if the center of the ball is less the the radius away from the wall, it should bounce
            {
                this.bounceWall();//change of direction for bouncing off walls
            }
        }

        point=false;
        point1=false;
        point2=false;
        //left and right border, point for one side
        for (int i=3; i<=4; i++)//checks left then right
        {
            if ((BorderComponent.getBorder(i)).ptLineDist(x, y)<=frameW/(speed*2))
            {
                point=true;
                if (i==3)
                {
                    point2=true;//went out the left side, point to player 2 on the right
                }
                if (i==4)
                {
                    point1=true;//went out the right side, point to player 1 on the left
                }

                try
                {
                    InputStream in1 = new FileInputStream("ping_pong_8bit_peeeeeep.wav");//gets the file location, sound for bouncing off a wall
                    audioStream1 = new AudioStream(in1);//audioStream uses the file location for that sound
                }
                catch (Exception e)//if the file location cannot be found
                {
                    //do nothing
                }
                AudioPlayer.player.start(audioStream1);//plays the sound

            }
        }

    }

    public void bounceWall()
    {
        direction=direction+((90-direction)*2);//reflects off the x axis
        while(direction<0)
        {
            direction=direction+360;
        }

        try
        {
            InputStream in2 = new FileInputStream("ping_pong_8bit_plop.wav");//gets the file location, sound for bouncing off a wall
            audioStream2 = new AudioStream(in2);//audioStream uses the file location for that sound
        }
        catch (Exception e)//if the file location cannot be found
        {
            //do nothing
        }
        AudioPlayer.player.start(audioStream2);//plays the sound
    }

    public void bouncePaddle()
    {
        double centerPos=y;
        double distance;
        if (x<0)//left side
        {
            centerPos=(PaddleComponent.getPaddle1().getY())+frameH/40;//finds where the left paddle is
            distance=y+centerPos;//finds the distance from the paddle to the ball

            direction=90;//angle starts out at 90 degrees straight out to the right
            /**
            for (int i=1; i<=15; i++)//progresses 15 steps out from the center to the edges of the paddle in both directions
            {
            if (distance>(i*frameH)/600)//moves in increments of 1/15 of the paddle
            {
            direction=direction-3;
            }
            if (distance<(-i*frameH)/600)//either adds or subtracts degrees based on whether the ball hits high or low on the paddle
            {
            direction=direction+3;
            }
            }
             */
            //for changing the direction of the ball based on movement of the paddle as well

            for (int i=1; i<=15; i++)
            {
                if (distance>(i*frameH)/600)
                {
                    direction=direction-2;
                }
                if (distance<(-i*frameH)/600)
                {
                    direction=direction+2;
                }
            }

            for (int i = 0; i<(PaddleComponent.getKeys()).size(); i++)
            {
                if ((PaddleComponent.getKeys()).get(i)==KeyEvent.VK_W)
                {
                    direction=direction-20;
                }
                if ((PaddleComponent.getKeys()).get(i)==KeyEvent.VK_S)
                {
                    direction=direction+20;
                }
            }

        }
        if (x>0)//right side
        {
            centerPos=(PaddleComponent.getPaddle2().getY())+frameH/40;
            distance=y+centerPos;

            direction=-90;

            /**
            for (int i=1; i<=15; i++)
            {
            if (distance>(i*frameH)/600)
            {
            direction=direction+3;
            }
            if (distance<(-i*frameH)/600)
            {
            direction=direction-3;
            }
            }
            */
             

            for (int i=1; i<=15; i++)
            {
                if (distance>(i*frameH)/600)
                {
                    direction=direction+2;
                }
                if (distance<(-i*frameH)/600)
                {
                    direction=direction-2;
                }
            }

            for (int i = 0; i<(PaddleComponent.getKeys()).size(); i++)
            {
                if ((PaddleComponent.getKeys()).get(i)==KeyEvent.VK_UP)
                {
                    direction=direction+20;
                }
                if ((PaddleComponent.getKeys()).get(i)==KeyEvent.VK_DOWN)
                {
                    direction=direction-20;
                }
            }

        }
        while(direction<0)
        {
            direction=direction+360;//always makes sure direction is a position number, easier for calculations
        }

        try
        {
            InputStream in3 = new FileInputStream("ping_pong_8bit_beeep.wav");//gets the file location, sound for bouncing off a wall
            audioStream3 = new AudioStream(in3);//audioStream uses the file location for that sound
        }
        catch (Exception e)//if the file location cannot be found
        {
            //do nothing
        }
        AudioPlayer.player.start(audioStream3);//plays the sound
        countHold=count;//keeps track of the time at which the ball bounced off the paddle
    }

    public boolean getPoint()
    {
        return point;//whether or not a point should be given to someone
    }

    public int whosePoint()//returns who the point is going to
    {
        if (point1==true)
        {
            return 1;
        }
        else if (point2==true)
        {
            return 2;
        }
        else if (point1==true)//done twice because it misses some points if it doesn't check twice
        {
            return 1;
        }
        else if (point2==true)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    public int getCount()
    {
        return count;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (point==false)//the ball is still in play
        {
            if (count>100)
            {
                if (((direction%360)>=0)&&((direction%360)<180))//move left or right depending on whether the angle points to the left or right direction
                {
                    x=x+(frameW/(speed*2));//move right, the x movement is always the same speed
                }
                if (((direction%360)>=180)&&((direction%360)<360))
                {
                    x=x-(frameW/(speed*2));//move left
                }
                y=y+(Math.cos(direction*(Math.PI/180)))*(frameW/speed);//the y changes based on the direction of the ball, a constant x but changing y will cause the ball to speed up and be harder to hit on wide angles while still giving the same amount of reaction time
                this.checkBounce();//checks to see if the ball needs to bounce off anything, changes the angle if necessary

                this.repaint();//repaints with the updated position of the ball
            }

        }
        count=count+1;//keeps time

    }
}
