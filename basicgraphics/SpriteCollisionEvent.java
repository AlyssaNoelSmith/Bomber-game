/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics;

/**
 *
 * @author sbrandt
 */
public class SpriteCollisionEvent {
    public final Sprite sprite2;
    public final boolean xlo, xhi, ylo, yhi;
    public SpriteCollisionEvent(boolean xlo,boolean xhi,boolean ylo,boolean yhi) { 
        sprite2 = null; 
        this.xlo = xlo;
        this.xhi = xhi;
        this.ylo = ylo;
        this.yhi = yhi;
    }
    public SpriteCollisionEvent(Sprite s2) {
        sprite2 = s2; 
        xlo = false;
        xhi = false;
        ylo = false;
        yhi = false;
    }
}
