//============================================================================
// Name        : rodgersFinalProject.java
// Author      : Tyler Rodgers
// Version     : 1.3
// Copyright   : 
// Description : Final Project for Java Course
//============================================================================
//
// Criteria:
// - Topic should cover the concepts we have learned in this course.
// - Several classes – inheritance, polymorphism
// - GUI
// - Data structures such as linked lists, stacks, ..
// - Collections Framework
// - Files
// - Exception handling
//
// Upload:
// - 1 or 2 pages of explanation of what the project is doing
// - Code
// - Sample results
// - Video of your presentation and showing how the code works
// - *Video, *Report, *Powerpoint
//
//============================================================================









package net.codejava.swing.hyperlink;

//package MyPackage;
import java.util.Collection;
import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.*;
import java.awt.*;


import java.awt.image.*;

import java.awt.event.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class rodgersFinalProject {

    public static void main(String[] args) {
        new rodgersFinalProject();
    }

    public rodgersFinalProject() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Tyler Rodgers – Java Final Project");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }

    public class TestPane extends JPanel {
        private List<String> menuItems;
        private String selectMenuItem;
        private String focusedItem;

        private MenuItemPainter painter;
        private Map<String, Rectangle> menuBounds;

        public TestPane() {
            setBackground(Color.BLACK);
            painter = new SimpleMenuItemPainter();
            menuItems = new ArrayList<>(25);
            menuItems.add("Launch Text Editor");
            menuItems.add("Video Link");
            menuItems.add("Source Code");

            //button.setOpaque(false);
			//button.setContentAreaFilled(false);
			//button.setBorderPainted(false);
            /*
            JButton b1 = new JButton();
            b1.setSize(400,400);
    		b1.setVisible(false);
    		b1.setText("HelloWorld");
    		menuItems.add(b1.toString());
			*/

            selectMenuItem = menuItems.get(0);

            MouseAdapter ma = new MouseAdapter() {

                    	
                @Override
                public void mouseClicked(MouseEvent e) { //when the mouse is clicked
 	
                	if(e.getY() >= 148 && e.getY() <= 172 && e.getX() >= 179 && e.getX() <= 280){ //mouse click on "Launch Project"                		
                			System.out.println("Launching Project...");
                	}

                	if(e.getY() >= 178 && e.getY() <= 206 && e.getX() >= 179 && e.getX() <= 280){ //mouse click on "Video Link"
                		try {
                    		System.out.println("Opening Youtube...");
                    		Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCz2X__Yk9x39HIOJQkK5qjw?view_as=subscriber"));
                    		
                		} 
                		catch (IOException | URISyntaxException e1) {
                    		e1.printStackTrace();
                		}
                	}

                	if(e.getY() >= 212 && e.getY() <= 236 && e.getX() >= 179 && e.getX() <= 280){ //mouse click on "Source Code"
                		try {
                    		System.out.println("Opening GitHub...");
                    		Desktop.getDesktop().browse(new URI("https://github.com/tilhr/Java-Project"));
                    		
                		} 
                		catch (IOException | URISyntaxException e1) {
                    		e1.printStackTrace();
                		}
                	}


                	

                	String newItem = null;
                    for (String text : menuItems) {
                        Rectangle bounds = menuBounds.get(text);
                        if (bounds.contains(e.getPoint())) {
                            newItem = text;
                            break;
                        }
                        //if (menuItems == "Source Code"){
                        //	System.out.println("You clicked source code");
                        //}
                    }
                    if (newItem != null && !newItem.equals(selectMenuItem)) {
                        selectMenuItem = newItem;
                        repaint();
                    }

              

        			//tracks mouseclicks & location
        			System.out.println("Number of click: " + e.getClickCount());
        			System.out.println("Click position (X, Y):  " + e.getX() + ", " + e.getY());


                    
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    focusedItem = null;
                    for (String text : menuItems) {
                        Rectangle bounds = menuBounds.get(text);
                        if (bounds.contains(e.getPoint())) {
                            focusedItem = text;
                            repaint();
                            break;
                        }
                    }
                }

            };

            addMouseListener(ma);
            addMouseMotionListener(ma);

            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "arrowDown");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "arrowUp");

            am.put("arrowDown", new MenuAction(1));
            am.put("arrowUp", new MenuAction(-1));

        }

        @Override
        public void invalidate() {
            menuBounds = null;
            super.invalidate();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(460, 380);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (menuBounds == null) {
                menuBounds = new HashMap<>(menuItems.size());
                int width = 0;
                int height = 0;
                for (String text : menuItems) {
                    Dimension dim = painter.getPreferredSize(g2d, text);
                    width = Math.max(width, dim.width);
                    height = Math.max(height, dim.height);
                }

                int x = (getWidth() - (width + 10)) / 2;

                int totalHeight = (height + 10) * menuItems.size();
                totalHeight += 5 * (menuItems.size() - 1);

                int y = (getHeight() - totalHeight) / 2;

                for (String text : menuItems) {
                    menuBounds.put(text, new Rectangle(x, y, width + 10, height + 10));
                    y += height + 10 + 5;
                }

            }
            for (String text : menuItems) {
                Rectangle bounds = menuBounds.get(text);
                boolean isSelected = text.equals(selectMenuItem);
                boolean isFocused = text.equals(focusedItem);
                painter.paint(g2d, text, bounds, isSelected, isFocused);
            }
            g2d.dispose();
        }

        public class MenuAction extends AbstractAction {

            private final int delta;

            public MenuAction(int delta) {
                this.delta = delta;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int index = menuItems.indexOf(selectMenuItem);
                if (index < 0) {
                    selectMenuItem = menuItems.get(0);
                }
                index += delta;
                if (index < 0) {
                    selectMenuItem = menuItems.get(menuItems.size() - 1);
                } else if (index >= menuItems.size()) {
                    selectMenuItem = menuItems.get(0);
                } else {
                    selectMenuItem = menuItems.get(index);
                }
                repaint();
            }

        }

    }

    public interface MenuItemPainter {

        public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isSelected, boolean isFocused);

        public Dimension getPreferredSize(Graphics2D g2d, String text);

    }

    public class SimpleMenuItemPainter implements MenuItemPainter {

        public Dimension getPreferredSize(Graphics2D g2d, String text) {
            return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
        }

        @Override
        public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isSelected, boolean isFocused) {
            FontMetrics fm = g2d.getFontMetrics();
            if (isSelected) {
                paintBackground(g2d, bounds, Color.GREEN, Color.WHITE);
            } else if (isFocused) {
                paintBackground(g2d, bounds, Color.RED, Color.BLACK);
            } else {
                paintBackground(g2d, bounds, Color.DARK_GRAY, Color.LIGHT_GRAY);
            }
            int x = bounds.x + ((bounds.width - fm.stringWidth(text)) / 2);
            int y = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();
            g2d.setColor(isSelected ? Color.BLACK : Color.WHITE);
            g2d.drawString(text, x, y);
        }

        protected void paintBackground(Graphics2D g2d, Rectangle bounds, Color background, Color foreground) {
            g2d.setColor(background);
            g2d.fill(bounds);
            g2d.setColor(foreground);
            g2d.draw(bounds);
        }

    }

}
 
/*
public class empty 
{
    public static void main(String[] args) 
    {       
        List<String> courseList = new LinkedList<>();
        courseList.add("Java");
        courseList.add("Python");
        courseList.add("DevOps");
        courseList.add("Hadoop");
        courseList.add("AWS");
        int size = courseList.size(); 
        System.out.println("Size of linked list = " + size);
        String[] numbers = new String[size];
         
        numbers = courseList.toArray(numbers);
        System.out.println("Elements of array are:");
        System.out.println(Arrays.toString(numbers));
 
    }
}
*/



