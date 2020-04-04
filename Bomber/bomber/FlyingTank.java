/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bomber;

import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Random;

/**
 * CSC 1351 Final Project
 * Instructor: Steven R Brandt
 * @since 3/30/17
 * @author Alyssa N. Smith
 */
public class FlyingTank extends Sprite 
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
     * The Random generator used to determine the position of this Sprite.
     */
    final Random RAND = new Random();
    
    /**
     * Initializes the sprite, setting its picture,
     * position, and speed. It also adds it to the
     * SpriteComponent.
     * 
     * @param sc the SpriteComponent to add this Sprite to
     * @throws IOException 
     */
    public void init(SpriteComponent sc) throws IOException 
    {
        initialPic = new Picture("JapaneseFlyingTankWorldWarII.PNG");
        setPicture(initialPic);
        
        Dimension d = sc.getSize();
        setX(d.width - getWidth());
        setY(RAND.nextInt(d.height - (int) getHeight()));
        setVelX(-2.5);
        setVelY(1);
        
        this.sc = sc;
        sc.addSprite(this);
    }
    
    /**
     * This method resets the position of this flying tank
     * if it collides with the left border or bottom border
     * of the screen.
     * @param se the SpriteCollisionEvent
     */
    @Override
    public void processEvent(SpriteCollisionEvent se) 
    {    
        if (se.xlo || se.yhi) 
        {
            Dimension d = sc.getSize();
            setX(d.width - getWidth());
            setY(RAND.nextInt(d.height - (int) getHeight()));
        } 
    }
}