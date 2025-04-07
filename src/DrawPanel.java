import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel extends JPanel implements MouseListener {
    private int[][] grid;
    Random rand;
    long originalTime;
    BrickLayout b;

    public DrawPanel() {
        grid = new int[30][40];
        rand = new Random();
        this.addMouseListener(this);
        randomizeGrid();
        originalTime = System.currentTimeMillis();

        // test
        b = new BrickLayout("src/bricks", false);
        b.printBrickLayout();
        grid = b.returnLayout();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long time = System.currentTimeMillis();
        System.out.println(time - originalTime);

        if ((time - originalTime) > 1000) {
            b.doOneBrick();
            grid = b.returnLayout();
            originalTime = System.currentTimeMillis();
        }

        Graphics2D g2 = (Graphics2D) g;

        int x = 10;
        int y = 10;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                g.drawRect(x, y, 20, 20);
                if (grid[row][col] == 1) {
                    g2.setColor(Color.RED);
                    g2.fillRect(x,y,20,20);
                    g2.setColor(Color.BLACK);
                }
                x += 25;
            }
            y += 25;
            x = 10;
        }
    }

    protected void randomizeGrid() {
        resetEmptyGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                int random = rand.nextInt(0, 10);
                if (random <= 2) {
                    grid[row][col] = 1;
                }

            }
        }
    }

    protected void resetEmptyGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

//        randomizeGrid();
        b.doOneBrick();
        grid = b.returnLayout();

    }

    @Override
    public void mousePressed(MouseEvent e) {

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

}