package Generators;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 2/22/18.
 */
public class IntegerGenerator extends NumberGenerator {

    public IntegerGenerator(Map<String, List<String>> parameters, int index) {
        super(parameters, index);
    }

    @Override
    public long getMin() {
        return Integer.MIN_VALUE;
    }

    @Override
    public long getMax() {
        return Integer.MAX_VALUE;
    }
}
