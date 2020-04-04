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
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JOptionPane;


/**
 * CSC 1351 Final Project
 * Instructor: Steven R Brandt
 * @since 3/30/17
 * @author Alyssa N. Smith
 */
public class Bockscar extends Sprite 
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
     * The Bomber that this belongs to.
     */
    Bomber bomber;
    
    /**
     * Tells whether this plane has been hit or not.
     */
    public Boolean hit = false;
    
    /**
     * Tells whether the plane is allowed to move.
     */
    public Boolean canMove = true;
    
    /**
     * Initializes the sprite, setting its picture,
     * position, and speed. It also adds it to the
     * SpriteComponent.
     * 
     * @param sc the SpriteComponent to add this Sprite to
     * @throws IOException 
     */
    public void init(BasicFrame bf, SpriteComponent sc) throws IOException 
    {
        initialPic = new Picture("bockscar2-ww2.jpg");
        setPicture(initialPic);
        
        Dimension d = sc.getSize();
        setX(d.width/5);
        setY(d.height/2);
        setVelX(0);                                                   
        setVelY(0);
        
        this.sc = sc;
        this.bf = bf;
        sc.addSprite(this);
    }
    
    /**
     * This method reacts to collisions with the top and
     * bottom borders of the display region by stopping the
     * plane's movement, or prints a message to the screen
     * when the plane collides with another Sprite.
     * @param se the SpriteCollisionEvent
     */
    @Override
    public void processEvent(SpriteCollisionEvent se) 
    {
        if (se.ylo) 
        {
            setVelY(0);
            setY(0);
        } else
        if (se.yhi) 
        {
            //setY(0);
            setVelY(0);
            setY(sc.getSize().height - getHeight());
        } else
        if (se.sprite2 != null) 
        {
            hit = true;
            
            sc.removeSprite(Bomber.JAPPLANE1.m);
            sc.removeSprite(Bomber.JAPPLANE2.m);
            sc.removeSprite(Bomber.JAPPLANE1);
            sc.removeSprite(Bomber.JAPPLANE2);
            sc.removeSprite(Bomber.KAMIKAZEPLANE);
            sc.removeSprite(Bomber.BBOMB);
            sc.removeSprite(Bomber.MITSUBISHI);
            sc.removeSprite(Bomber.TANK);
            
            String[] buttons = { "OK" };
            JOptionPane.showOptionDialog(null, "You've been hit!\n GAME OVER!",
                    "BOMBER", 0, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[0]);
            
            Bomber.displayGoodbye(bf,sc);
            
        }    
    }
}