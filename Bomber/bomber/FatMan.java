/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomber;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * CSC 1351 Final Project
 * Instructor: Steven R Brandt
 * @since 3/30/17
 * @author Alyssa N. Smith
 */
public class FatMan extends Sprite 
{
    
    /**
     * The initial Picture for this Sprite.
     */
    public Picture initialPic;
    
    /**
     * The SpriteComponent that this Sprite belongs to.
     */
    SpriteComponent sc;
    
    /**
     * The BasicFrame to use.
     */
    BasicFrame bf;
    
    /**
     * The Bockscar that this bomb comes from.
     */
    public Bockscar bockscar;
    
    /**
     * Initializes the sprite, setting its picture,
     * position, and speed. It also adds it to the
     * SpriteComponent.
     * 
     * @param bf the BasicFrame to use
     * @param sc the SpriteComponent to add this Sprite to
     * @throws IOException 
     */
    public void init(BasicFrame bf, SpriteComponent sc) throws IOException 
    {
        initialPic = new Picture("Fat Man.jpg");
        setPicture(initialPic);
        
        setX(bockscar.getX()+bockscar.getWidth()/3);
        setY(bockscar.getY()+bockscar.getHeight()*1.3);
        setVelX(-0.5);                                                       
        setVelY(2.5);
        
        this.sc = sc;
        this.bf = bf;
        sc.addSprite(this);
    }
    
    /**
     * Displays a message when the bomb hits the bottom of the screen.
     * 
     * @param se the SpriteCollisionEvent
     */
    @Override
    public void processEvent(SpriteCollisionEvent se) 
    {
        sc.removeSprite(this);
        sc.removeSprite(bockscar);
        
        if (se.yhi) 
        {
            String[] buttons = { "OK" };
            JOptionPane.showOptionDialog(null, "GREAT JOB!\n"
                    + "You have successfully completed your mission.",
                    "BOMBER", 0, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
            Bomber.displayGoodbye(bf, sc);
        }
    }
}