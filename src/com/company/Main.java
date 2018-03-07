package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Main extends JDialog {

    private enum Direction {
        LEFT(-5),
        RIGHT(5),
        UP(-5),
        DOWN(5);

        private int speed;

        Direction(int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }

        public Direction getOpposite() {
            switch (this) {
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                case UP:
                    return DOWN;
                case DOWN:
                    return UP;

            }
            return LEFT;
        }
    }


    private ClearImageIcon penguin;
    private Point startClick;

    private class Ticker implements Runnable {

        Random random = new Random();
        private final int SCREEN_WIDTH;
        private final int SCREEN_HEIGHT;
        private JDialog dialog;
        private Direction direction;

        public Ticker(JDialog dialog) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            SCREEN_WIDTH = screenSize.width;
            SCREEN_HEIGHT = screenSize.height;
            this.dialog = dialog;
        }

        @Override
        public void run() {
            setRandomDirection();
            setLocation(dialog.getLocation().x, getRandomHeight());
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isColision() == 1) {
                    setRandomDirection();
                    setVisible(false);
                    waitRandomTime();
                    setVisible(true);
                    setLocation(dialog.getLocation().x, getRandomHeight());
                }
                move();
                //System.out.println(String.format("x:%s y:%s", dialog.getLocation().getX(), dialog.getLocation().getY()));
            }
        }

        private void move() {
            dialog.setLocation(dialog.getLocation().x + direction.getSpeed(), dialog.getLocation().y);
        }

        private void waitRandomTime() {
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private int getRandomHeight() {
            return random.nextInt(SCREEN_HEIGHT - 200);
        }

        private void setRandomDirection() {
            if (random.nextInt(2) == 1) {
                direction = Direction.RIGHT;
                setLocation(-195, dialog.getLocation().y);
            } else {
                direction = Direction.LEFT;
                setLocation(SCREEN_WIDTH - 1, dialog.getLocation().y);
            }
        }

        private int isColision() {
            if ((dialog.getLocation().x < -200 && direction == Direction.LEFT) ||
                    (dialog.getLocation().x > SCREEN_WIDTH && direction == Direction.RIGHT)) {
                return 1;
            }
            return 0;
        }
    }

    class ClearImageIcon extends ImageIcon {

        public ClearImageIcon(URL location) {
            super(location);
        }

        public ClearImageIcon(String filename) {
            super(filename);
        }

        @Override
        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
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
        setAlwaysOnTop(true);
        setBackground(new Color(1, 1, 1, 1));
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JLabel label = new JLabel();
        label.setIcon(penguin);

        JPanel panel = new JPanel();
        panel.add(label);
        add(label);
        //setContentPane(new MyPanel());
        //setPreferredSize(new Dimension(160, 160));
        pack();
        setVisible(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F11){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - startClick.x;
                int deltaY = e.getY() - startClick.y;

                //System.out.println(String.format("x:%s y:%s",e.getX(),e.getY()));
                //setLocation(e.getX(),e.getY());
                setLocation(getLocation().x + deltaX, getLocation().y + deltaY);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                startClick = e.getPoint();
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
        });
        Ticker ticker = new Ticker(this);
        Thread thread = new Thread(ticker);
        thread.start();

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
