package Main;

import javax.swing.*;
import java.awt.*;

public class board extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 20;
    Thread gameThread;
    playManager pm;

    public board() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        pm = new playManager();
    }

    public void launch() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        //Game loop
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }

    }

    public void update() {
        pm.update();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        pm.draw(g2d);
    }
}
