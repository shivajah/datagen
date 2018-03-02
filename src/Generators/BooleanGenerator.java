package Generators;
import java.util.Random;

/**
 * Created by shiva on 2/22/18.
 */
public class BooleanGenerator implements IGenerator{
    private int trueCount;
    private int falseCount;
    private int numOfRows;
    private Random rand = new Random();
    public BooleanGenerator(int numOfRows) {
        this.trueCount = 0;
        this.falseCount = 0;
        this.numOfRows=numOfRows;
    }
    public Boolean next(){
        if(rand.nextInt(numOfRows) % 2 == 0) {
            trueCount++;
            return true;
        }
        falseCount++;
        return false;
    }

}
