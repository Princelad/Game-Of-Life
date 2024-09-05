package Main;

import javax.swing.*;

/**
 * Main class to launch the Game of Life application.
 */
public class Main {
    public static void main(String[] args) {
        // Create the main window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game Of Life");
        window.setResizable(false);

        // Create and add the board to the window
        board board = new board();
        window.add(board);
        window.pack();

        // Center the window and make it visible
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the game loop
        board.launch();
    }
}
