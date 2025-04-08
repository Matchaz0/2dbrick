import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private int[][] brickLayout;
    private int height; // how tall
    private int range; // collumn number
    private ArrayList<String> fileData;

    public BrickLayout(String fileName, boolean dropAllBricks) {
        fileData = getFileData(fileName);
        bricks = new ArrayList<Brick>();
        range = findEndOfRange();
        height = findHeight();

        for (String line : fileData) {
            String[] points = line.split(",");
            int start = Integer.parseInt(points[0]);
            int end = Integer.parseInt(points[1]);
            Brick b = new Brick(start, end);
            bricks.add(b);
        }

        brickLayout = new int[height][range+1]; // add one to range because the ending index is one less than needed
        System.out.println(range);
        System.out.println(height);

        // find the bricks height using a simulation
        findFinalHeightsOfBricks();
        // find the new array height
    }

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

    public int findHeight() {
        return fileData.size();
    }

    public int findActualHeight(int[][] finalLayout) {
        for (int row = 0; row < finalLayout.length; row++) {
            if (checkBrickRowEmpty(row, 0, finalLayout[0].length)) {
                return row;
            }
        }
        return 0;
    }

    public void findFinalHeightsOfBricks() {

        for (int brick = 0; brick < bricks.size();brick++) {
            // getting latest brick
            Brick b = bricks.get(brick);
            int start = b.getStart();
            int end = b.getEnd();
            int height = 0;
            //tryna get a top to bottom approach now
            for (int row = 0; row < brickLayout.length; row++) {
                boolean allZero = checkBrickRowEmpty(row, start, end);
                if (allZero && row == brickLayout.length - 1) {
                    for (int col = start; col <= end; col++) {
                        brickLayout[row][col] = 1;
                        height = row;
                    }
                    row = brickLayout.length;
                }
                else if (!allZero) {
                    for (int col = start; col <= end; col++) {
                        brickLayout[row - 1][col] = 1;
                        height = row - 1;
                    }
                    row = brickLayout.length;
                }
            }
            bricks.get(brick).setHeight(height);
        }
        resetBrickLayout();
    }

    public void resetBrickLayout() {
        for (int row = 0; row < brickLayout.length; row++) {
            for (int col = 0; col < brickLayout[0].length; col++) {
                brickLayout[row][col] = 0;
            }
        }
    }

    public void activateOneBrick() {
        boolean doOnce = true;
        for (Brick b: bricks) {
            if (!b.isActive() && doOnce) {
                b.setActive();
                doOnce = false;
            }
        }
    }

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
    public void updateBrickLayout() {
        // reset make empty
        resetBrickLayout();


        for (int brick = 0; brick < bricks.size(); brick++) {
            Brick b = bricks.get(brick);

            if (!b.isFinished() && b.isActive()) {
                bricks.get(brick).setTempHeight(b.getTempHeight() + 1);
            }
            if (b.isActive()) {
                for (int col = b.getStart(); col <= b.getEnd(); col++) {
                    brickLayout[b.getTempHeight()][col] = 1;
                }
            }
        }
    }

    public void printBrickLayout() {
        for (int r = 0; r < brickLayout.length; r++) {
            for (int c = 0; c < brickLayout[0].length; c++) {
                System.out.print(brickLayout[r][c] + " ");
            }
            System.out.println();
        }
    }
    // Exit condition to stop moving bricks
    public boolean finished() {
        boolean finish = true;
        for (Brick b: bricks) {
            if (!b.isFinished() || !b.isActive()) {
                finish = false;
            }
        }
        return finish;
    }

    public boolean checkBrickSpot(int r, int c) {
        if (brickLayout[r][c] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBrickRowEmpty(int r, int start, int end) {
        boolean allEmpty = true;
        for (int n = start; n <= end; n++) {
            if (checkBrickSpot(r, n)) {
                allEmpty = false;
            }
        }
        return allEmpty;

    }
    public int[][] returnLayout() {
        return brickLayout;
    }


}