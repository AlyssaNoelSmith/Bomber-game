/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics.images;

import basicgraphics.BasicFrame;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * This class encapsulates the creation and
 * use of images.
 * @author sbrandt
 */
public class Picture extends JComponent {
    
    private BufferedImage image;
    private int width, height;
    
    /**
     * Get the raw image stored by this class.
     * @return 
     */
    public Image getImage() { return image; }
    
    /** You should store your images
     * in the same directory as the source for
     * this class (i.e. the same directory as
     * Picture.java). That will enable you to
     * load them either from the working directory
     * in Netbeans, or in the jar file you
     * distribute.
     * @param name
     * @throws IOException 
     */
    public Picture(String name) throws IOException {
        URL src = getClass().getResource(name);
        if(src == null) throw new IOException("No such image: "+name);
        image = ImageIO.read(src);
        width = image.getWidth();
        height = image.getHeight();
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(getPreferredSize());
    }
    /**
     * You can also create a picture from an image
     * directly (using basicgraphics.BasicFrame.createImage())
     * and drawing on it.
     * @param im 
     */
    public Picture(Image im) {
        this.image = (BufferedImage) im;
        width = image.getWidth();
        height = image.getHeight();
        setPreferredSize(new Dimension(width,height));
        setMinimumSize(getPreferredSize());
    }
    
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    
    /**
     * Create a new copy of the picture
     * object that's rotated by the specified
     * angle (measured in radians).
     * @param angle
     * @return 
     */
    public Picture rotate(double angle) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage bi = BasicFrame.createImage(w,h);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        double tx = w/2;
        double ty = h/2;
        g2.translate(tx,ty);
        g2.rotate(angle);
        g2.translate(-tx,-ty);
        g2.drawImage(image, 0, 0, this);
        return new Picture(bi);
    }
    
    /**
     * Create a button that uses the same
     * image as the one stored in this Picture.
     * @return 
     */
    public JButton makeButton() {
        return new JButton(new ImageIcon(image));
    }
}
