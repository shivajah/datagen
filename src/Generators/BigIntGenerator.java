package Generators;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 2/23/18.
 */
public class BigIntGenerator extends NumberGenerator {
    public BigIntGenerator(Map<String, List<String>> parameters, int index) {
        super(parameters, index);
    }

    @Override public long getMin() {
        return Long.MIN_VALUE;
    }

    @Override public long getMax() {
        return Long.MAX_VALUE;
    }
}
