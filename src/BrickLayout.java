import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {
    private ArrayList<Brick> bricks;
    private int[][] brickLayout;
    private int height; // how tall
    private int range; // column number
    private ArrayList<String> fileData;

    public BrickLayout(String fileName) {
        // find and init the empty brick layout
        fileData = getFileData(fileName);
        bricks = new ArrayList<Brick>();
        range = findEndOfRange();
        height = findHeight();
        brickLayout = new int[height][range+1];

        // get all the bricks
        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }

        // find the bricks height using a simulation
        // so later bricks can know the end point after the drop
        findFinalHeightsOfBricks();
    }

    // find how many collumsn there should be
    public int findEndOfRange() {
        String firstBrick = fileData.getFirst();
        String[] points = firstBrick.split(",");
        int end = Integer.parseInt(points[1]);

        for (String brick:fileData) {
            points = brick.split(",");
            int tempEnd = Integer.parseInt(points[1]);
            if (tempEnd > end) {
                end = tempEnd;
            }
        }
        return end;
    }

    // how many rows
    public int findHeight() {
        return fileData.size();
    }

    // find each final destination for each brick
    // it does this by dropping each premptively
    // and finding what row it end up in
    public void findFinalHeightsOfBricks() {

        for (int brick = 0; brick < bricks.size();brick++) {
            // getting latest brick
            Brick b = bricks.get(brick);
            int start = b.getStart();
            int end = b.getEnd();
            int height = 0;
            // tryna get a top to bottom approach now
            // check conditions to ensure brick is in proper placement
            for (int row = 0; row < brickLayout.length; row++) {
                boolean allZero = checkBrickRowEmpty(row, start, end);
                if (allZero && row == brickLayout.length - 1) {
                    for (int col = start; col <= end; col++) {
                        brickLayout[row][col] = 1;
                        height = row;
                    }
                    // set row to exit condition
                    row = brickLayout.length;
                }
                else if (!allZero) {
                    for (int col = start; col <= end; col++) {
                        brickLayout[row - 1][col] = 1;
                        height = row - 1;
                    }
                    // set row to exit condition
                    row = brickLayout.length;
                }
            }
            // set the height of the brick
            bricks.get(brick).setHeight(height);
        }
//        System.out.println(brickLayout); // this can be uncommented to find the final layout
        // reset brick layout for dropping, now that we've found the height
        resetBrickLayout();
    }

    // reinits layout
    public void resetBrickLayout() {
        for (int row = 0; row < brickLayout.length; row++) {
            for (int col = 0; col < brickLayout[0].length; col++) {
                brickLayout[row][col] = 0;
            }
        }
    }

    // going through each of the bricks.
    // find the first brick (if there is) and make it active.
    // this will activate during each interval for a drop
    public void activateOneBrick() {
        boolean doOnce = true;
        for (Brick b: bricks) {
            if (!b.isActive() && doOnce) {
                b.setActive();
                doOnce = false;
            }
        }
    }

    // self explanitory
    public ArrayList<String> getFileData(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }
        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        return fileData;
    }

    // going through every brick
    public void updateBrickLayout() {
        // make sure to reset the layout first
        resetBrickLayout();

        // if brick is not in it's final destination (if temp height is less than final height)
        for (int brick = 0; brick < bricks.size(); brick++) {

            Brick b = bricks.get(brick);
            // then make it move down by one
            if (!b.isFinished() && b.isActive()) {
                bricks.get(brick).setTempHeight(b.getTempHeight() + 1);
            }
            // or if it is at the destination
            if (b.isActive()) {
                // just print it statically
                for (int col = b.getStart(); col <= b.getEnd(); col++) {
                    brickLayout[b.getTempHeight()][col] = 1;
                }
            }
        }
    }

    // no duh
    public void printBrickLayout() {
        for (int r = 0; r < brickLayout.length; r++) {
            for (int c = 0; c < brickLayout[0].length; c++) {
                System.out.print(brickLayout[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Exit condition if we stop moving all bricks
    public boolean finished() {
        boolean finish = true;
        for (Brick b: bricks) {
            if (!b.isFinished() || !b.isActive()) {
                finish = false;
            }
        }
        return finish;
    }

    // is the specific piece a 1?
    public boolean checkBrickSpot(int r, int c) {
        if (brickLayout[r][c] == 1) {
            return true;
        } else {
            return false;
        }
    }

    // using method above, is the row from a certain range empty or have 1's
    public boolean checkBrickRowEmpty(int r, int start, int end) {
        boolean allEmpty = true;
        for (int n = start; n <= end; n++) {
            if (checkBrickSpot(r, n)) {
                allEmpty = false;
            }
        }
        return allEmpty;

    }

    // return the brick layout for the game
    public int[][] returnLayout() {
        return brickLayout;
    }


}