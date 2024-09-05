package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Panel class for the Game of Life.
 * Manages the game loop and rendering of the game state.
 */
public class board extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 10;  // Frames per second
    Thread gameThread;
    playManager pm;

    public board() {
        // Set up the board panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        // Initialize playManager
        pm = new playManager();

        // Set up key and mouse listeners
        keyHandler kh = new keyHandler(pm);
        this.addKeyListener(kh);
        this.addMouseListener(kh);
        this.addMouseMotionListener(kh);
        this.setFocusable(true);
    }

    /**
     * Starts the game thread.
     */
    public void launch() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Game loop
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

    /**
     * Updates the game state if not paused and if ENTER has been pressed.
     */
    public void update() {
        if (!keyHandler.pausePressed && !keyHandler.enterPressed) {
            pm.update();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        pm.draw(g2d);
    }
}
