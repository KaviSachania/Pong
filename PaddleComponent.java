
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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class PaddleComponent extends BallComponent implements ActionListener, KeyListener//implements the ActionListener and KeyListener interfaces
{
    private int frameW;
    private int frameH;
    private int x;
    private int y;
    private int player;
    private int speed;
    private int size;
    private static Rectangle paddle1 = new Rectangle();
    private static Rectangle paddle2 = new Rectangle();
    private static ArrayList<Integer> keys = new ArrayList<Integer>();//arraylist to hold the keys that are being pressed, uses Integer as a wrapper class because primitive types cannot be stored in arraylists
    public PaddleComponent()
    {

    }

    public PaddleComponent(int frameSize, int player0)
    {
        frameW=frameSize;
        frameH=frameSize;
        player=player0;//sets whether the paddle will have characteristics of player 1 on the left or player 2 on the right
        size=20;//the bigger this number gets, the shorter the paddle becomes
        y=frameH/(size*2);//sets the paddle at the midpoint vertically
        speed=250;//the bigger this number gets, the slower the paddle becomes
        //speed=225;//at school
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.translate(frameW/2, frameH/2);//middle origin

        this.drawPaddle(g2);
    }

    public void drawPaddle(Graphics2D g2)
    {
        
        //sets the paddle at the left or right side of the screen depending on which paddle it is
        if (player==1)
        {
            x=-((frameW/2)-(frameW/12));
        }
        if (player==2)
        {
            x=(frameW/2)-(frameW/12);
        }

        Rectangle paddle = new Rectangle(x, -y, frameW/100, frameH/size);//paddle is a rectangle
        
        //two new object references are made so that a specific paddle can be returned in static accesor methods
        if (player==1)
        {
            paddle1=paddle;
        }
        if (player==2)
        {
            paddle2=paddle;
        }

        g2.setColor(Color.WHITE);
        g2.draw(paddle);
        g2.fill(paddle);

    }

    public static Rectangle getPaddle1()
    {
        return paddle1;
    }

    public static Rectangle getPaddle2()
    {
        return paddle2;
    }
    
    public static ArrayList<Integer> getKeys()
    {
        return keys;//returns the arraylist of pressed keys, used in BallComponent for adjusting angle based on movement of the paddle (may not be used)
    }

    public void keyPressed(KeyEvent e)//method runs when a key is pressed
    {
        Integer code = e.getKeyCode();//finds which key was pressed in order for the keyPressed method to run, assigns to code (Integer is a wrapper for int)

        boolean checkAdd=true;

        for (int i = 0; i<keys.size(); i++)//runs through all the keys that are pressed right now
        {
            if (code==keys.get(i))//if the pressed key is already in the arraylist then don't add to the arraylist
            {
                checkAdd=false;
            }
        }
        if (checkAdd==true)//if the pressed key was not found in the arraylist then add it
        {
            keys.add(code);
        }



    }

    public void movePaddle()
    {

        for (int i = 0; i<keys.size(); i++)//runs through all the they keys that are being pressed
        {
            if (player==1)//player 1 uses the W and S keys to move
            {
                if (y<((frameH/4)-(frameH/150)))
                {
                    if (keys.get(i)==KeyEvent.VK_W)
                    {
                        y=y+frameH/speed;//move up
                    }
                }
                if (y>-((frameH/4)-(frameH/size)))
                {
                    if (keys.get(i)==KeyEvent.VK_S)
                    {
                        y=y-frameH/speed;//move down
                    }
                }
            }

            if (player==2)//player 2 uses the UP and DOWN keys to move
            {
                if (y<((frameH/4)-(frameH/150)))
                {
                    if (keys.get(i)==KeyEvent.VK_UP)
                    {
                        y=y+frameH/speed;//move up
                    }
                }
                if (y>-((frameH/4)-(frameH/size)))
                {
                    if (keys.get(i)==KeyEvent.VK_DOWN)
                    {
                        y=y-frameH/speed;//move down
                    }
                }
            }
        }

    }

    public void keyReleased(KeyEvent e)
    {
        Integer code = e.getKeyCode();
        keys.remove(code);//the key has been released so therefore it does not need to be in the arraylist of pressed keys anymore

    }

    public void keyTyped(KeyEvent e)//not used, here just to fufill the requirements of interface KeyListener
    {

    }

    public void actionPerformed(ActionEvent e)
    {
        this.movePaddle();//moves the paddle based off what keys are being pressed
        this.repaint();

    }
}