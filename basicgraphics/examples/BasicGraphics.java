/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics.examples;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Foo implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Click!");
    }
}
/**
 *
 * @author sbrandt
 */
public class BasicGraphics {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
      BasicFrame f = new BasicFrame("Fish");
      final JButton refac = new JButton("Button 1");
      refac.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              JOptionPane.showMessageDialog(refac, "Button 1 was pressed!");
          }
      });
      refac.addActionListener(new Foo());
      f.add("topl",refac,0,0,1,1);
      f.add("topm",new JButton("Button 2"),1,0,1,1);
      f.add("topr",new JButton("Button 3"),2,0,1,1);
      f.add("row2", new JLabel("Row 2"), 0, 1, 3, 1);
      //f.add("row3",jc,0,2,3,1);
      f.add("row4",new Picture("sarah.png").makeButton(),0,3,2,1);
      
      Sprite bat = new Sprite() {
          @Override
          public void processEvent(SpriteCollisionEvent ce) {
                if(ce.xlo)
                    setVelX( Math.abs(getVelX()));
                if(ce.xhi)
                    setVelX(-Math.abs(getVelX()));
                if(ce.ylo)
                    setVelY( Math.abs(getVelY()));
                if(ce.yhi)
                    setVelY(-Math.abs(getVelY()));
          }
      };
      
      bat.setPicture(new Picture("bat.jpg"));
      bat.setVelX(1);
      bat.setVelY(1);
      
      SpriteComponent sc = new SpriteComponent();
      Dimension d = new Dimension(800,400);
      sc.setPreferredSize(d);
      
      final int nballs = 30;
      final int ballSize = 20;
      Image im = f.createImage(ballSize,ballSize);
      Graphics imgr = im.getGraphics();
      imgr.setColor(Color.red);
      imgr.fillOval(0, 0, ballSize, ballSize);
      Picture ballPicture = new Picture(im);
      Random rand = new Random();
      KeyListener kl = new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
          }

          @Override
          public void keyPressed(KeyEvent e) {
              System.out.println("pressed: "+e.toString());
          }

          @Override
          public void keyReleased(KeyEvent e) {
         }
      };
      f.addKeyListener(kl);
      MouseListener ml = new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
              System.out.println("click");
          }

          @Override
          public void mousePressed(MouseEvent e) {
          }

          @Override
          public void mouseReleased(MouseEvent e) {
          }

          @Override
          public void mouseEntered(MouseEvent e) {
          }

          @Override
          public void mouseExited(MouseEvent e) {
          }
      };
      sc.addMouseListener(ml);
      for(int i=0;i<nballs;i++) {
          Sprite sball = new Sprite() {
              @Override
              public void processEvent(SpriteCollisionEvent ce) {
                if(ce.xlo)
                    setVelX( Math.abs(getVelX()));
                if(ce.xhi)
                    setVelX(-Math.abs(getVelX()));
                if(ce.ylo)
                    setVelY( Math.abs(getVelY()));
                if(ce.yhi)
                    setVelY(-Math.abs(getVelY()));
                if(ce.sprite2 != null)
                    setActive(false);
              }
          };
          sball.setPicture(ballPicture);
          sball.setVelX(2*rand.nextDouble()-1);
          sball.setVelY(2*rand.nextDouble()-1);
          sball.setX(rand.nextInt(d.width));
          sball.setY(rand.nextInt(d.height));
          sc.addSprite(sball);
      }
      
      sc.addSprite(bat);
      sc.start(0,10);
      
      f.add("row3",sc,0,2,3,1);
      f.show();
    }
    
}
