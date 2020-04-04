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
import java.io.IOException;

/**
 * CSC 1351 Final Project
 * Instructor: Steven R Brandt
 * @since 3/30/17
 * @author Alyssa N. Smith
 */
public class Missile extends Sprite 
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
     * The enemy plane that this missile comes from.
     */
    RegularJapanesePlane jp;
    
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
        initialPic = new Picture("missile.jpg");
        setPicture(initialPic);
        
        setX(jp.getX() - getWidth()/1.5);
        setY(jp.getY() + jp.getHeight()/2);
        setVelX(2*jp.getVelX());                                                       
        setVelY(0);
        
        this.sc = sc;
        sc.addSprite(this);
    }
    
    /**
     * Resets the missile when it flies off the screen to the left,
     * or displays a menu if it hits your plane.
     * 
     * @param se the SpriteCollisionEvent
     */
    @Override
    public void processEvent(SpriteCollisionEvent se) 
    {
        if (se.xlo) 
        {
            if (jp.getX()>=sc.getWidth()/5) 
            {
               setX(jp.getX() - getWidth()/1.5);
               setY(jp.getY() + jp.getHeight()/2); 
            }
        }
    }
}