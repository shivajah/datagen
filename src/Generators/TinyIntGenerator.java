package Generators;

import java.util.List;
import java.util.Map;

/**
 * Created by shiva on 2/23/18.
 */
public class TinyIntGenerator extends NumberGenerator {

    public TinyIntGenerator(Map<String, List<String>> parameters, int index) {
        super(parameters, index);
    }

    @Override public long getMin() {
        return Byte.MIN_VALUE;
    }

    @Override public long getMax() {
        return Byte.MAX_VALUE;
    }
}
