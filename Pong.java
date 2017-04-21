
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.util.Random;
public class Pong
{
    private int frameSize;
    private Random gen = new Random();
    private JFrame frame = new JFrame("Intersection");
    private BallComponent startBall = new BallComponent(900);//this BallComponent will only be used for the purpose of being able to construct a timer with all the necessary parameter variables
    private Timer tBall = new Timer(1, startBall);//fires every millisecond, will eventually have action listeners from other objects than those of BallComponent
    private int scoreP1;//player scores
    private int scoreP2;
    //instance field variables declared
    public Pong()//constructor for Pong
    {
        frameSize=900;
        scoreP1=0;
        scoreP2=0;
    }

    public void drawStuff()
    {

        tBall.removeActionListener(startBall);//the startBall is not needed anymore and therefore doesn't need tBall to point to it

        frame.setSize(frameSize, frameSize);//setsize method invoked on JFrame object frame, square
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tBall.start();//lets the timer start calling actionPerformed methods

    
        while ((scoreP1<11)&&(scoreP2<11))
        {
            
            PaddleComponent pC1 = new PaddleComponent(frameSize, 1);//left side paddle
            frame.addKeyListener(pC1);//can respond to key presses
            tBall.addActionListener(pC1);//will have its actionPerformed method called every millisecond by the timer
            frame.add(pC1);//paddle added to the JFrame
            frame.setVisible(true);//can be seen

            PaddleComponent pC2 = new PaddleComponent(frameSize, 2);//right side paddle
            frame.addKeyListener(pC2);
            tBall.addActionListener(pC2);
            frame.add(pC2);
            frame.setVisible(true);

            NumberComponent nC = new NumberComponent(frameSize, scoreP1, scoreP2);
            frame.add(nC);
            frame.setVisible(true);

            BorderComponent borderC = new BorderComponent(900);
            frame.add(borderC);
            frame.setVisible(true);

            BallComponent ballC = new BallComponent(frameSize);
            tBall.addActionListener(ballC);
            frame.add(ballC);
            frame.setVisible(true);

            BackgroundComponent bC = new BackgroundComponent(frameSize);
            tBall.addActionListener(bC);
            frame.add(bC);
            frame.setVisible(true);

            boolean check=false;

            while (check==false)//escapes when one player makes a point
            {
                if (ballC.getPoint()==true)
                {
                    check=true;
                    //do nothing
                    //waiting for a point to be made
                }
            }
            //a point has been made now
            if (ballC.whosePoint()==1)
            {
                scoreP1=scoreP1+1;//assigns the point the player
            }
            if (ballC.whosePoint()==2)
            {
                scoreP2=scoreP2+1;
            }
            
            //removing all the components because they reset at every point
            //the border and background are removed but then repainted at the beginning of the while loop to make sure they stay behind the ball and paddles
            frame.remove(nC);
            frame.remove(borderC);
            
            tBall.removeActionListener(ballC);
            frame.remove(ballC);

            tBall.removeActionListener(bC);
            frame.remove(bC);

            if ((scoreP1<11)&&(scoreP2<11))//if no one has won yet
            {
                //the paddles are not to be reset to the middle if the game has ended, they should just stay where they are
                frame.removeKeyListener(pC1);//the paddle does not respond to the keys anymore
                tBall.removeActionListener(pC1);
                frame.remove(pC1);

                frame.removeKeyListener(pC2);
                tBall.removeActionListener(pC2);
                frame.remove(pC2);

            }

        }
        
        //the game is over, repaint these components so that they still visible, they are here so that they are painted over the ball that should not be visible now
        
        BorderComponent borderC = new BorderComponent(900);
        frame.add(borderC);
        frame.setVisible(true);

        NumberComponent nC = new NumberComponent(frameSize, scoreP1, scoreP2);
        frame.add(nC);
        frame.setVisible(true);

        BackgroundComponent bC = new BackgroundComponent(frameSize);
        frame.add(bC);
        frame.setVisible(true);

        tBall.stop();//stops the timer from calling actionPerformed methods
    }
}
