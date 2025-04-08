import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;

public class DrawPanel extends JPanel implements MouseListener {
    // Init instance variables
    private int[][] grid;
    private Random rand;
    private long originalTime;
    private BrickLayout b;

    public DrawPanel() {
        rand = new Random();
        this.addMouseListener(this);
        originalTime = System.currentTimeMillis();

        // grid will only depend of CLI of brick layout
        b = new BrickLayout("src/bricks");
        grid = b.returnLayout();
    }

    protected void paintComponent(Graphics g) {
        // timing
        super.paintComponent(g);
        long time = System.currentTimeMillis();
        System.out.println(time - originalTime);

        // if time has passed and the bricks are not done falling
        if ((time - originalTime) > 100 && !b.finished()) {
            // make one more brick active if possible
            b.activateOneBrick();
            // update grid based on bricks active
            b.updateBrickLayout();
            // reset the time
            originalTime = System.currentTimeMillis();
            // set the grid to the new layout
            grid = b.returnLayout();
        }

        // now using the grid
        Graphics2D g2 = (Graphics2D) g;

        // create visual representation, with 1's being red and 0's being normal
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

    // leftover code
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

    // leftover code
    protected void resetEmptyGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = 0;
            }
        }
    }
    // not really implemented huh
    // maybe i could do it so that every click
    // the input file changes
    // and it drops again
    @Override
    public void mouseClicked(MouseEvent e) {
        b.findFinalHeightsOfBricks();
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