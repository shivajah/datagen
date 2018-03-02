package Generators;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by shiva on 2/23/18.
 */
public abstract class NumberGenerator implements IGenerator {
    Map<String,List<String>>parameters;
    protected long from;
    protected long to;
    protected Random rand = new Random();
    public abstract long getMin();
    public abstract long getMax();
    public NumberGenerator(Map<String,List<String>>parameters, int index){
        this.parameters = parameters;
        String to = parameters.get("to").get(index);
        String from = parameters.get("from").get(index);
        this.from = (from.equalsIgnoreCase("null"))?getMin():Long.parseLong(from);
        this.to=(to.equalsIgnoreCase("null"))?getMax():Long.parseLong(to);
    }

    public long next(){
        long i = rand.nextLong() % to;
        while(i<from){i = rand.nextLong() % to;}
        return i;
    }
}
