import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private int[][] brickLayout;
    private int rows;
    private int cols;
    ArrayList<String> fileData;

    public BrickLayout(String fileName, boolean dropAllBricks) {
        fileData = getFileData(fileName);
        bricks = new ArrayList<Brick>();
        int range = findEndOfRange();
        int height = findHeight();

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
        System.out.println();


        // find height for all bricks
        findBrickHeightsLocation();

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


    public void findBrickHeightsLocation() {
        ArrayList<Integer> instructions = new ArrayList<>();
        for (int brickN = 0; brickN < bricks.size(); brickN++) {
            // getting latest brick
            Brick b = bricks.get(brickN);
            int start = b.getStart();
            int end = b.getEnd();

            //tryna get a top to bottom approach now
            for (int row = 0; row < brickLayout.length; row++) {
                boolean allZero = checkBrickRowEmpty(row, start, end);
                if (allZero && row == brickLayout.length - 1) {
                    // set row length
                    for (int col = start; col <= end; col++) {
                        bricks.get(brickN).setHeight(row);
//                        brickLayout[row][col] = 1;
                    }

                    // reset
                    row = brickLayout.length;
                }
                else if (!allZero) {
                    for (int col = start; col <= end; col++) {
                        bricks.get(brickN).setHeight(row);
//                        brickLayout[row - 1][col] = 1;
                    }
                    // reset
                    row = brickLayout.length;
                }


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

    public void printBrickLayout() {
        for (int r = 0; r < brickLayout.length; r++) {
            for (int c = 0; c < brickLayout[0].length; c++) {
                System.out.print(brickLayout[r][c] + " ");
            }
            System.out.println();
        }
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