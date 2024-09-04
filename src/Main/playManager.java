package Main;

import java.awt.*;
import java.util.Random;

public class playManager {

    int[][] cell = new int[64][36];
    int[][] newCell = new int[64][36];
    int generation = 0;
    int population = 0;

    public playManager() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                cell[i][j] = new Random().nextInt(2);
                if (cell[i][j] == 1) {
                    population++;
                }
            }
        }
    }

    public int countNeighbours(int x, int y) {
        int count = 0;

        try {
            count += cell[x - 1][y - 1];
            count += cell[x][y - 1];
            count += cell[x + 1][y - 1];
            count += cell[x - 1][y];
            count += cell[x + 1][y];
            count += cell[x - 1][y + 1];
            count += cell[x][y + 1];
            count += cell[x + 1][y + 1];
        } catch (Exception _) {
        }
        return count;
    }

    public void update() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                int neighbours = countNeighbours(i, j);
                if (cell[i][j] == 0 && neighbours == 3) {
                    newCell[i][j] = 1;
                    population++;
                } else if (cell[i][j] == 1 && (neighbours > 3 || neighbours < 2)) {
                    newCell[i][j] = 0;
                    population--;
                } else {
                    newCell[i][j] = cell[i][j];
                }
            }
        }

        for (int i = 0; i < 64; i++) {
            System.arraycopy(newCell[i], 0, cell[i], 0, 36);
        }

        generation++;
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                if (cell[i][j] == 1) {
                    int EDGE = 20;
                    g.fillRect(i * EDGE, j * EDGE, EDGE - 1, EDGE - 1);
                }
            }
        }

        g.setColor(Color.green);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Generation: " + generation, 30, 30);
        g.drawString("Population: " + population, 30, 60);
    }
}
