package Main;

import java.awt.*;

/**
 * Manages the game state and logic for the Game of Life.
 */
public class playManager {

    int[][] cell = new int[64][36];  // Grid for cell states
    int[][] newCell = new int[64][36];  // Temporary grid for updates
    int generation = 0;  // Number of generations
    int population = 0;  // Number of live cells

    public playManager() {
        // Optionally, initialize the grid with some initial state
    }

    /**
     * Counts the number of live neighbors for a cell at (x, y).
     *
     * @param x X-coordinate of the cell
     * @param y Y-coordinate of the cell
     * @return Number of live neighbors
     */
    public int countNeighbours(int x, int y) {
        int count = 0;
        int cols = cell.length;  // Number of columns (64)
        int rows = cell[0].length;  // Number of rows (36)

        // Loop through the 8 neighbors
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;  // Skip the cell itself
                }
                int newX = (x + i + cols) % cols;  // Wrap around horizontally
                int newY = (y + j + rows) % rows;  // Wrap around vertically
                count += cell[newX][newY];  // Add the state of the wrapped cell
            }
        }
        return count;
    }


    /**
     * Updates the game state based on Game of Life rules.
     */
    public void update() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                int neighbours = countNeighbours(i, j);
                if (cell[i][j] == 0 && neighbours == 3) {
                    newCell[i][j] = 1;  // Cell becomes alive
                    population++;
                } else if (cell[i][j] == 1 && (neighbours > 3 || neighbours < 2)) {
                    newCell[i][j] = 0;  // Cell dies
                    population--;
                } else {
                    newCell[i][j] = cell[i][j];  // Keep current state
                }
            }
        }

        // Copy the new state back into the cell array
        for (int i = 0; i < 64; i++) {
            System.arraycopy(newCell[i], 0, cell[i], 0, 36);
        }

        generation++;  // Increment generation counter
    }

    /**
     * Draws the grid and cell states on the provided Graphics context.
     *
     * @param g Graphics context for drawing
     */
    public void draw(Graphics g) {
        g.setColor(Color.black);
        int cellSize = 20;  // Each cell is 20x20 pixels

        // Loop through the grid and draw cells
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                if (cell[i][j] == 1) {  // Alive cell
                    g.fillRect(i * cellSize, j * cellSize, cellSize - 1, cellSize - 1);  // Slightly smaller to show grid lines
                }
            }
        }

        // Draw generation and population count
        g.setColor(Color.green);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Generation: " + generation, 30, 30);
        g.drawString("Population: " + population, 30, 60);
    }

    /**
     * Sets the state of a cell based on mouse coordinates.
     *
     * @param mouseX X-coordinate from mouse event
     * @param mouseY Y-coordinate from mouse event
     * @param alive  True to set cell to alive, false to set to dead
     */
    public void setCellState(int mouseX, int mouseY, boolean alive) {
        int cellSize = 20;  // Each cell is 20x20 pixels

        // Convert mouse coordinates to grid coordinates
        int gridX = mouseX / cellSize;
        int gridY = mouseY / cellSize;

        // Check if the coordinates are within bounds of the grid
        if (gridX >= 0 && gridX < 64 && gridY >= 0 && gridY < 36) {
            int state = cell[gridX][gridY];
            cell[gridX][gridY] = alive ? 1 : 0;  // Set cell to alive or dead

            if (alive && state != cell[gridX][gridY]) {
                population++;
            } else if (!alive && state != cell[gridX][gridY]) {
                population--;
            }
        }
    }

    /**
     * Clears the board and resets generation and population counters.
     */
    public void clearBoard() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                cell[i][j] = 0;  // Set all cells to dead
            }
        }
        population = 0;  // Reset population count
        generation = 0;  // Reset generation count
    }
}
