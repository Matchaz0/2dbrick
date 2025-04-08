import java.util.Random;

public class GenerateInput {
    public static void main(String[] args) {
        Random rand = new Random();
        int numberOfBricks = 20;


        for (int bricks = 0; bricks < numberOfBricks; bricks++) {
            int length = rand.nextInt(2,10); // 2-6
            int upper = rand.nextInt(length, 21);
            int lower = upper - length;
            System.out.println("" + lower + "," + upper);
        }
    }
}
