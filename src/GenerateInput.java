import java.util.Random;

public class GenerateInput {
    public static void main(String[] args) {
        Random rand = new Random();
        int numberOfBricks = 1000;


        for (int bricks = 0; bricks < numberOfBricks; bricks++) {
            int length = rand.nextInt(2,7); // 2-6
            int upper = rand.nextInt(length, 101);
            int lower = upper - length;
            System.out.println("" + lower + "," + upper);
        }
    }
}
