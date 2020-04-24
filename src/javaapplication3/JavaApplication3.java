/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author abdo
 */
public class JavaApplication3 extends JFrame {

    private int xrect = 50;
    private final int yrect = 450;
    private int xball = 370;
    private int yball = 353;
    int xwall[] = {50, 130, 210, 290, 370, 450, 530, 610, 690, 50, 130, 210, 290, 370, 450, 530, 610, 690, 180, 280, 380, 480, 580};
    int ywall[] = {90, 90, 90, 90, 90, 90, 90, 90, 90, 150, 150, 150, 150, 150, 150, 150, 150, 150, 210, 210, 210, 210, 210};
   // int xwall2[] = {50, 130, 210, 290, 370, 450, 530, 610, 690, 50, 130, 210, 290, 370, 450, 530, 610, 690, 180, 280, 380, 480, 580};
   // int ywall2[] = {90, 90, 90, 90, 90, 90, 90, 90, 90, 150, 150, 150, 150, 150, 150, 150, 150, 150, 210, 210, 210, 210, 210};
   // boolean level = false;
    int score = 0;
    Date start;
    Image image;
    ImageIcon icon = new ImageIcon("E:\\project java\\games\\break ball\\JavaApplication3\\src\\images\\sa.png");

    public JavaApplication3() throws InterruptedException {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        Random r = new Random();
        int x = r.nextInt(3) + 9;
        panel p = new panel(x, -4);
        add(p);
        addKeyListener(p);
        setFocusable(true);
        addMouseMotionListener(p);

        while (true) {
            if (score <= 23 && p.gettime() <= 60 && p.lose == false) {
                p.update();
                p.repaint();
                Thread.sleep(15);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JavaApplication3 g = new JavaApplication3();
    }

    public final class panel extends JLabel implements KeyListener, MouseMotionListener {

        int xspeed;
        int yspeed;
        AudioClip sound, sound2;
        boolean lose = false;

        public panel(int xspeed, int yspeed) {
            setBounds(0, 0, 800, 600);
            this.xspeed = xspeed;
            this.yspeed = yspeed;
            URL uri = getClass().getResource("Ball_Bounce-Popup_Pixels-172648817.wav");
            sound = Applet.newAudioClip(uri);
            URL uri2 = getClass().getResource("");
            sound2 = Applet.newAudioClip(uri2);
            start();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);
            if (xrect <= 0) {
                xrect = 1;
            }
            if (xrect + 200 >= 800) {
                xrect = 580;
            }
            g.setColor(Color.red);
            g.fillRoundRect(xrect, yrect, 200, 30, 45, 45);

            g.setColor(Color.BLUE);
            g.fillOval(xball, yball, 25, 25);

           // g.setColor(Color.orange);

            image = icon.getImage();
            for (int i = 0; i < 23; i++) {
                g.drawImage(image, xwall[i], ywall[i], this);
                if (xball >= xwall[i] && xball <= xwall[i] + image.getWidth(this) && yball >= ywall[i] && yball <= ywall[i] + image.getHeight(this)+20) {
                    sound.play();
                    yspeed *= -1;
                    xwall[i] = -50;
                    ywall[i] = -50;
                    
                    score += 1;
                    
                    repaint();

                }

            }

            if (score == 23 && gettime() <= 60) {
                g.setColor(Color.WHITE);
                g.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 43));
                g.drawString("GOOD LUCK ", getWidth() / 3, getHeight() / 2);

            } else if (gettime() > 60 && score < 23 || lose == true) {
                g.setColor(Color.WHITE);
                g.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 43));
                g.drawString(" HARD LUCK ", getWidth() / 3, getHeight() / 2);
                lose = true;
            }
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.HANGING_BASELINE, 23));
            g.drawString(" Score : " + score, 290, 550);
            g.drawString(" Time : " + gettime(), 400, 550);

        }

        public void update() {
            xball += xspeed;
            yball += yspeed;
            if (xball <= 0) {
                sound2.play();
                xball = 25;
                xspeed *= -1;

            }
            if (yball <= 0) {
                sound2.play();
                yball = 25;
                yspeed *= -1;
            }
            if (xball + 25 >= 780) {
                sound2.play();
                xspeed *= -1;
            }
            if (yball + 25 >= 785) {
                lose = true;
            }
            if (xball >= xrect - 20 && xball <= xrect + 180 && yball == yrect - 25) {
                sound2.play();
                yspeed *= -1;
            }

        }

        public void start() {
            start = new Date();
        }

        public long gettime() {
            Date ti = new Date();
            long time = ti.getTime() - start.getTime();
            return time / 1000;
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                xrect -= 30;
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                xrect += 30;
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent me) {
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            xrect = me.getX();
        }

    }
}
