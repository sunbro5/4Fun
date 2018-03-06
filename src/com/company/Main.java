package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Main extends JDialog {

    private ClearImageIcon penguin;

    class ClearImageIcon extends ImageIcon{

        public ClearImageIcon(URL location) {
            super(location);
        }

        public ClearImageIcon(String filename){super(filename);}

        @Override
        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setBackground(new Color(1, 1, 1, 1));
            g2.clearRect(0, 0, getIconWidth(), getIconHeight());
            super.paintIcon(c, g2, x, y);
        }
    }

    private Main() throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.penguin = loadGif("penguin_53467.gif");//TODO set fucking URL
        setUndecorated(true);
        setBackground(new Color(1, 1, 1, 1));
        setLocation(0,0);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JLabel label = new JLabel();
        label.setIcon(penguin);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,0,0,0));
        panel.add(label);
        add(label);
        //setContentPane(new MyPanel());
        //setPreferredSize(new Dimension(160, 160));
        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Oh Dang !!!!");
        }
    }

    private ClearImageIcon loadGif(String imagePath) throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(imagePath);
        ClearImageIcon imageIcon = new ClearImageIcon(url);
        return imageIcon;
    }
}
