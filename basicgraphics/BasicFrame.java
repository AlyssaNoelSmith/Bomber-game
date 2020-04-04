/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgraphics;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Manages a JFrame object for you, hopefully
 * making it easier to get the gui layout you
 * want for your application.
 * 
 * You can find examples of how this package
 * can be used in @see basicgraphics.examples.BasicGraphics,
 * in @see basicflyer.Flyer, and @see basicshooter.Game.
 * 
 * @author sbrandt
 */
public class BasicFrame {

    public JFrame jf;
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    ArrayList<ArrayList<String>> cells = new ArrayList<>();

    /**
     * The title will be displayed on the top of the
     * main frame.
     * @param title 
     */
    public BasicFrame(String title) {
        jf = new JFrame(title);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(gbl);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        jf.setJMenuBar(new JMenuBar());
    }
    
    static class MenuHolder {
        final JMenu menu;
        final Map<String,JMenuItem> items = new HashMap<>();
        MenuHolder(String n) { menu = new JMenu(n); }
    }
    Map<String,MenuHolder> menuMap = new HashMap<>();
    
    /**
     * Add a menu to the menu bar, along with an action to be
     * carried out when that menu item is selected.
     * @param menuName
     * @param menuItem
     * @param action 
     */
    public void addMenuAction(String menuName,String menuItem,final Runnable action) {
        JMenuBar mb = jf.getJMenuBar();
        MenuHolder mh = menuMap.get(menuName);
        if(mh == null) {
            mh = new MenuHolder(menuName);
            menuMap.put(menuName,mh);
            mb.add(mh.menu);
        }
        JMenuItem jmi = mh.items.get(menuItem);
        if(jmi == null) {
            jmi = new JMenuItem(menuItem);
            mh.items.put(menuItem,jmi);
            mh.menu.add(jmi);
            final Runnable catchRun = new Runnable() {
                public void run() {
                    try {
                        action.run();
                    } catch(Exception ex) {
                        TaskRunner.report(ex,jf);
                    }
                }
            };
            jmi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /**
                     * Call on the graphics thread to avoid
                     * contention issues
                     */
                    SwingUtilities.invokeLater(catchRun);
                }
            });
        }
    }

    private Map<String, Component> components = new HashMap<>();

    /**
     * Each "name" should uniquely identify a display element.
     * Apart from that, it can be anything.
     * 
     * Each "c" identifies a JButton, JLabel, SpriteComponent, etc.
     * to be added to the display area.
     * 
     * The values x, y, xw, yw provide logical coordinates and sizes
     * for the display item. Thus, this example:
     * <pre>
     *   add("j1",new JLabel("j1"),0,0,1,1);
     *   add("j2",new JLabel("j2"),1,0,1,1);
     *   add("j3",new JLabel("j3"),0,1,2,1);
     * </pre>
     * creates three JLabels. The first two are on the top row, and
     * the third is on the second, but the third is the same width
     * as the top two put together (because the widths add to the
     * same value).
     * @param name
     * @param c
     * @param x
     * @param y
     * @param xw
     * @param yw 
     */
    public void add(String name, Component c, int x, int y, int xw, int yw) {
        if (components.containsKey(name)) {
            throw new GuiException("Two components with the same name: " + name);
        }
        if (jf.isVisible()) {
            throw new GuiException("add() called after show()");
        }

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = xw;
        gbc.gridheight = yw;

        // Ensure the number of cells
        while (cells.size() < x + xw) {
            cells.add(new ArrayList<String>());
        }
        for (int i = 0; i < xw; i++) {
            for (int j = 0; j < yw; j++) {
                int xv = x + i;
                int yv = y + j;
                ArrayList<String> row = cells.get(xv);
                while (row.size() <= yv) {
                    row.add(null);
                }
                String owner = row.get(yv);
                if (owner != null) {
                    throw new GuiException("Conflict of ownership at x=" + xv + " y=" + yv + " owner1=" + owner + " owner2=" + name);
                }
                row.set(yv, name);
            }
        }
        components.put(name, c);
        jf.add(c, gbc);
    }

    /**
     * Internal method to manage the
     * arrays which keep track of the
     * display.
     */
    void fill() {
        int xsize = cells.size();
        int ysize = 0;
        for (int i = 0; i < xsize; i++) {
            int ys = cells.get(i).size();
            if (ys > ysize) {
                ysize = ys;
            }
        }
        for (int i = 0; i < xsize; i++) {
            ArrayList<String> row = cells.get(i);
            while (row.size() < ysize) {
                row.add(null);
            }
        }
    }

    /**
     * Prints an ascii representation of the display on
     * standard output.
     */
    void print() {
        fill();
        if(cells.size()==0)
            return;
        int ssize = 0;
        for (String key : components.keySet()) {
            if (key.length() > ssize) {
                ssize = key.length();
            }
        }
        String fmt = "[%" + ssize + "s]";
        for (int i = 0; i < cells.get(0).size(); i++) {
            for (int j = 0; j < cells.size(); j++) {
                ArrayList<String> row = cells.get(j);
                String val = row.get(i);
                if (val == null) {
                    val = "";
                }
                System.out.printf(fmt, val);
            }
            System.out.println();
        }
    }

    /**
     * Make the frame visible. Until this method
     * is called, sizes of various components will
     * be unknown. At this time the display will
     * be checked for parts of the gui which have
     * not been filled in. Red boxes with an X
     * will be placed in each.
     */
    public void show() {
        fill();
        for (int i = 0; i < cells.size(); i++) {
            ArrayList<String> row = cells.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (cells.get(i).get(j) == null) {
                    ErrorComponent ec = new ErrorComponent();
                    add("*" + i + "," + j, ec, i, j, 1, 1);
                }
            }
        }
        print();
        jf.pack();
        jf.setVisible(true);
        jf.requestFocus();
    }

    /**
     * Call this method to request 
     * repainting of the named
     * component. The name is the
     * one supplied when the component
     * was added.
     */
    public void repaint(String name) {
        components.get(name).repaint();
    }

    /**
     * A utility to create images. Once you have an image, you
     * can call the getGraphics() method in order to draw on it.
     * @param w
     * @param h
     * @return 
     */
    public static BufferedImage createImage(int w, int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    public Container getContentPane() {
        return jf.getContentPane();
    }

    /**
     * Usually you subclass a KeyAdapter to handle keyboard
     * events. It should make things simpler for you if you
     * give your KeyAdapters to the BasicFrame rather than
     * trying to give it to a component.
     * @param kl 
     */
    public void addKeyListener(KeyListener kl) {
        jf.addKeyListener(new KeyWrapper(kl));
    }
    
    /**
     * Make it go away.
     */
    public void dispose() {
        jf.dispose();
    }
}
