package Main;

import java.awt.*;
import java.util.Random;

public class playManager {

    final private int EDGE = 20;
    int[][] cell = new int[128][72];

    public playManager() {
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 72; j++) {
                cell[i][j] = new Random().nextInt(2);
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
        } catch (Exception _) {}
        return count;
    }

    public void update() {
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 72; j++) {
                int neighbours = countNeighbours(i, j);
                if (cell[i][j] == 0 && neighbours == 3) {
                    cell[i][j] = 1;
                } else if (cell[i][j] == 1 &&(neighbours > 3 || neighbours < 2)) {
                        cell[i][j] = 0;
                } else{
                    cell[i][j] = cell[i][j];
                }
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 72; j++) {
                if (cell[i][j] == 1) {
                    g.fillRect(i * EDGE, j * EDGE, EDGE - 1, EDGE - 1);
                }
            }
        }
    }
}
