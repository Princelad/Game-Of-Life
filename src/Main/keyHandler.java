package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Handles keyboard and mouse events for the Game of Life.
 */
public class keyHandler implements KeyListener, MouseListener, MouseMotionListener {

    public static boolean pausePressed = false;  // Indicates if the game is paused
    public static boolean enterPressed = true;  // Indicates if the game should be running
    private boolean mouseHeldLeft = false;  // Track if left mouse button is held
    private boolean mouseHeldRight = false; // Track if right mouse button is held
    public static boolean clearPressed = false;  // Indicates if the clear command is pressed

    private final playManager pm;  // Reference to playManager for grid updates

    /**
     * Constructs a keyHandler with a reference to playManager.
     *
     * @param pm The playManager instance used to manage the game state
     */
    public keyHandler(playManager pm) {
        this.pm = pm;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> pausePressed = !pausePressed;  // Toggle pause on ESC
            case KeyEvent.VK_ENTER -> {
                enterPressed = false;  // Resume the game when ENTER is pressed
                clearPressed = false;  // Reset clearPressed status
            }
            case KeyEvent.VK_R -> {
                clearPressed = true;  // Indicate that the clear command is active
                pausePressed = true;  // Pause the game while clearing
                enterPressed = true;  // Ensure game is not running during clear
                pm.clearBoard();  // Clear the game board
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            clearPressed = false;  // Reset clearPressed status
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> {
                mouseHeldLeft = true;  // Track left button press

                pm.setCellState(e.getX(), e.getY(), true);  // Set cell to alive on left-click
            }
            case MouseEvent.BUTTON3 -> {
                mouseHeldRight = true;  // Track right button press

                pm.setCellState(e.getX(), e.getY(), false);  // Set cell to dead on right-click
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1 -> mouseHeldLeft = false;  // Reset when left mouse is released
            case MouseEvent.BUTTON3 -> mouseHeldRight = false;  // Reset when right mouse is released
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseHeldLeft) {  // If left mouse button is held, set cell alive
            pm.setCellState(e.getX(), e.getY(), true);
        } else if (mouseHeldRight) {  // If right mouse button is held, set cell dead
            pm.setCellState(e.getX(), e.getY(), false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
