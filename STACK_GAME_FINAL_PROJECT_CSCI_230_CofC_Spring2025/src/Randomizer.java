
import java.util.Random;

public class Randomizer
{
    public static int getRandomNumber(int min, int max)
    {
        Random random = new Random(); 
        int number = random.nextInt((max-min) + 1) + min;
        return number;
    }
}
