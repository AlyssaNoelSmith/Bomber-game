/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics;

import basicgraphics.images.Picture;
import java.awt.Dimension;

/**
 * This class implements an animated object that moves
 * across the screen. It needs a picture, a position,
 * and a velocity. One can override it to tell it how
 * to react to collisions.
 * @author sbrandt
 */
public class Sprite {
    
    static boolean overlapx(Sprite sp1,Sprite sp2) {
        return sp1.getX() <= sp2.getX()+sp2.getWidth () && sp1.getX()+sp1.getWidth () >= sp2.getX();
    }
    
    static boolean overlapy(Sprite sp1,Sprite sp2) {
        return sp1.getY() <= sp2.getY()+sp2.getHeight() && sp1.getY()+sp1.getHeight() >= sp2.getY();
    }

    static boolean overlap(Sprite sp1, Sprite sp2) {
        boolean inx = overlapx(sp1,sp2);
        boolean iny = overlapy(sp1,sp2);
        return inx && iny;
    }
    
    private Picture p;
    private double velx, vely, x, y;
    private boolean active = true;
    private int drawingPriority;
    
    public int getDrawingPriority() {
        return drawingPriority;
    }
    public void setDrawingPriority(int dp) {
        drawingPriority = dp;
    }

    /**
     * Used for detecting collisions
     */
    int yindex;
    
    public boolean isActive() { return active; }
    public void setActive(boolean a) { active = a; }
    
    public void setPicture(Picture p) {
        if(this.p != null) {
            int delx = p.getWidth()-this.p.getWidth();
            int dely = p.getHeight()-this.p.getHeight();
            setX(getX()-delx/2);
            setY(getY()-dely/2);
        }
        this.p = p;
    }
    public Picture getPicture() { return p; }
    public void setX(double x) { this.x = x; }
    public double getX() { return x; }
    public void setY(double y) { this.y = y; }
    public double getY() { return y; }
    public void setVelX(double velx) { this.velx = velx; }
    public double getVelX() { return velx; }
    public void setVelY(double vely) { this.vely = vely; }
    public double getVelY() { return vely; }

    public double getWidth() {
        return p.getWidth();
    }
    public double getHeight() {
        return p.getHeight();
    }

    void move(Dimension d) {
        x += velx;
        y += vely;
        boolean xlo=false, xhi=false, ylo=false, yhi=false;
        if(x < 0 && velx < 0)
            xlo = true;
        else if(x < -getWidth())
            xlo = true;
        
        if(x+getWidth() > d.width && velx > 0)
            xhi = true;
        else if(x > d.width)
            xhi = true;   
        
        if(y < 0 && vely < 0)
            ylo = true;
        else if(y < -getHeight())
            ylo = true;
        
        if(y+getHeight() > d.height && vely > 0)
            yhi = true;
        else if(y > d.height)
            yhi = true;
        
        if(xlo || xhi || ylo || yhi)
            processEvent(new SpriteCollisionEvent(xlo,xhi,ylo,yhi));
    }

    /**
     * Override this method to determine how the sprite
     * will react when it collides with another sprite
     * or with the boundaries of the screen.
     * @param ev 
     */
    public void processEvent(SpriteCollisionEvent ev) {
    }

    /**
     * Override this method to determine what a sprite
     * will do before its move.
     */
    public void preMove() {
    }

    /**
     * Override this method to determine what a sprite
     * will do after its move.
     */
    public void postMove() {
    }
}
