package Generators;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 2/23/18.
 */
public class SmallIntGenerator extends NumberGenerator {

    public SmallIntGenerator(Map<String, List<String>> parameters, int index) {
        super(parameters, index);
    }

    @Override public long getMin() {
        return Short.MIN_VALUE;
    }

    @Override public long getMax() {
        return Short.MAX_VALUE;
    }
}
