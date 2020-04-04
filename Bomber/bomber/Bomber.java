/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomber;

/**
 * CSC 1351 Final Project
 * Instructor: Steven R Brandt
 * @since 3/30/17
 * @author Alyssa N. Smith
 */

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane; 

public class Bomber 
{
    /**
     * The first part of the background picture.
     */
    final static MainMenuBackgroundPic MMBP1 = new MainMenuBackgroundPic();
    
    /**
     * The second part of the background picture.
     */
    final static MainMenuBackgroundPic MMBP2 = new MainMenuBackgroundPic();
    
    /**
     * The bomber plane.
     */
    final static Bockscar BOCKSCAR = new Bockscar();
    
    /**
     * The first regular Japanese plane.
     */
    final static RegularJapanesePlane JAPPLANE1 = new RegularJapanesePlane();
    
    /**
     * The second regular Japanese plane.
     */
    final static RegularJapanesePlane JAPPLANE2 = new RegularJapanesePlane();
    
    /**
     * The kamikaze plane.
     */
    final static KamikazePlane KAMIKAZEPLANE = new KamikazePlane();
    
    /**
     * The Japanese balloon bomb.
     */
    final static BalloonBomb BBOMB = new BalloonBomb();
    
    /**
     * The interceptor aircraft.
     */
    final static MitsubishiJ8M MITSUBISHI = new MitsubishiJ8M();
    
    /**
     * The flying tank.
     */
    final static FlyingTank TANK = new FlyingTank();
    
    /**
     * The fatman bomb.
     */
    final static FatMan FATMAN = new FatMan();
    
    public static void main(String[] args) throws IOException, InterruptedException
    {
        BOCKSCAR.collisionBufferFactor = 0.85;
        
        BasicFrame bf = new BasicFrame("Bomber");
        
        final SpriteComponent sc = new SpriteComponent() 
        {
            @Override
            public void paintBackground(Graphics g) 
            {
                Dimension d = getSize();
                g.setColor(new Color(167, 192, 223));
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        sc.setPreferredSize(new Dimension(2000,1000));
        
        bf.add("Bomber",sc,0,0,1,1);
        bf.show();
        
        final Bomber DUMMY = new Bomber();
        
        MMBP1.init1(sc);
        MMBP2.init2(sc);
        
        sc.start(0,10);
        
        DUMMY.displayInitialMenu(bf, sc);
    }
    
    /**
     * Displays the initial menu.
     * Allows the user to start the game,
     * exit the game, or read the game's
     * instructions.
     * @param bf the BasicFrame to use
     * @param sc the SpriteComponent to use
     * @throws IOException
     * @throws InterruptedException 
     */
    public void displayInitialMenu(BasicFrame bf, SpriteComponent sc) throws IOException, InterruptedException
    {
        String[] buttons = { "Start Game", "Exit Game", "Instructions"};
        int rc = JOptionPane.showOptionDialog(null, "CHOOSE AN OPTION BELOW", "BOMBER",
            0, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        
        if (rc == 0)
        {
            sc.removeSprite(MMBP1);
            sc.removeSprite(MMBP2);
            new Bomber().startGame(bf, sc);
        } else if (rc == 1)
        {
            System.exit(0);
        } else if (rc == 2)
        {
            displayInstructions(bf, sc, "initialMenu");
        } else 
        {
            System.exit(0);
        }
    }
    
    /**
     * Displays the exiting message.
     * @param bf the BasicFrame to use
     * @param sc the SpriteComponent to use
     */
    public static void displayGoodbye(BasicFrame bf, SpriteComponent sc)
    {
        String[] buttons = {"Exit Game"};
        int rc = JOptionPane.showOptionDialog(null, "", "BOMBER",
            0, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        
        String[] buttons2 = {"OK"};
        JOptionPane.showOptionDialog(null, "GOODBYE!", "BOMBER",
            0, JOptionPane.PLAIN_MESSAGE, null, buttons2, buttons2[0]);
        
        System.exit(0);
    }
    
    /**
     * Displays the game's instructions.
     * @param bf the BasicFrame to use
     * @param sc the SpriteComponenet to use
     * @param menuType the type of menu
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public void displayInstructions(BasicFrame bf, SpriteComponent sc, String menuType) throws IOException, InterruptedException 
    {
        String[] buttons = { "Main Menu" };
        JOptionPane.showOptionDialog(null, "Move your plane up and down with the arrow keys to dodge enemy planes and missiles.\n"
                + "Survive for 45 seconds to make it to your landing site, and drop the bomb to successfully complete your mission.\n"
                + "GOOD LUCK!" , "INSTRUCTIONS", 0, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        displayInitialMenu(bf, sc);
        
    }
    
    /**
     * Starts the game.
     * @param bf the BasicFrame to use
     * @param sc the SpriteComponent to use
     * @throws IOException
     * @throws InterruptedException 
     */
    public void startGame(final BasicFrame bf, final SpriteComponent sc) throws IOException, InterruptedException 
    {
        bf.addKeyListener(new KeyAdapter() 
        {          
            @Override                                    
            public void keyPressed(KeyEvent ke) 
            {
                if (BOCKSCAR.canMove) 
                {
                    if(ke.getKeyCode() == KeyEvent.VK_UP) 
                    {   
                        BOCKSCAR.setVelY(BOCKSCAR.getVelY()-1.5);                            
                    } else if(ke.getKeyCode() == KeyEvent.VK_DOWN) 
                    {
                        BOCKSCAR.setVelY(BOCKSCAR.getVelY()+1.5);
                    }
                }
            }
            
            @Override
            public void keyReleased(KeyEvent ke) 
            {
                if(ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyCode() == KeyEvent.VK_DOWN) 
                {   
                    BOCKSCAR.setVelY(0);                            
                }
            }
        });
        
        BOCKSCAR.init(bf, sc);
        
        TimeUnit.SECONDS.sleep(2);
        
        JAPPLANE1.init(sc);
        
        KAMIKAZEPLANE.init(sc);    
        
        TimeUnit.SECONDS.sleep(2);
        
        JAPPLANE2.init(sc);
        
        TimeUnit.SECONDS.sleep(6);
        
        BBOMB.init(sc);
        
        TimeUnit.SECONDS.sleep(3);
        
        MITSUBISHI.init(sc);
        
        TimeUnit.SECONDS.sleep(5);
        
        TANK.init(sc);
        
        TimeUnit.SECONDS.sleep(27);
        
        Dimension d = sc.getSize();
        BOCKSCAR.setX(d.width/2);
        BOCKSCAR.setY(d.height/2);
        BOCKSCAR.setVelX(0);                                                   
        BOCKSCAR.setVelY(0);
        
        sc.removeSprite(JAPPLANE1.m);
        sc.removeSprite(JAPPLANE2.m);
        sc.removeSprite(JAPPLANE1);
        sc.removeSprite(JAPPLANE2);
        sc.removeSprite(KAMIKAZEPLANE);
        sc.removeSprite(BBOMB);
        sc.removeSprite(MITSUBISHI);
        sc.removeSprite(TANK);
        
        BOCKSCAR.canMove = false;
        
        if (!BOCKSCAR.hit) 
        {
            String[] buttons = { "OK" };
            JOptionPane.showOptionDialog(null, "You made it to your target site!\n To drop Fat Man, press the space bar.", "INSTRUCTIONS", 0, 
                    JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
        

            
            bf.addKeyListener(new KeyAdapter() 
            {          
                @Override                                    
                public void keyPressed(KeyEvent ke) 
                {
                    if(ke.getKeyChar() == ' ') 
                    {   
                        FATMAN.bockscar = BOCKSCAR;
                        try 
                        {
                            FATMAN.init(bf, sc);
                                                       
                        } catch (IOException ex) 
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            });           
        }
    }
}
    
class MainMenuBackgroundPic extends Sprite
{   
    
    /**
     * The SpriteComponent that this sprite belongs to.
     */
    SpriteComponent sc;
    
    /**
     * Initializes the sprite, setting its picture
     * and position. It also adds it to the
     * SpriteComponent.
     * 
     * @param sc the SpriteComponent to add this Sprite to
     * @throws IOException 
     */
    public void init1(SpriteComponent sc) throws IOException 
    {
        Picture pic = new Picture("mainlandAsia.png");
        setPicture(pic);
        
        Dimension d = sc.getSize();
        setX(0);
        setY(0);
        
        this.sc = sc;
        sc.addSprite(this);
    }   
    
    /**
     * Initializes the sprite, setting its picture
     * and position. It also adds it to the
     * SpriteComponent.
     * 
     * @param sc the SpriteComponent to add this Sprite to
     * @throws IOException 
     */
    public void init2(SpriteComponent sc) throws IOException 
    {
        Picture pic = new Picture("explosionJapan.png");
        setPicture(pic);
        
        Dimension d = sc.getSize();
        setX(d.getWidth()/4);
        setY(d.getHeight()/8);
        
        this.sc = sc;
        sc.addSprite(this);
    }
}