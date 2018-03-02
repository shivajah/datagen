package Constants;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by shiva on 2/22/18.
 */
public class DataTypes {

    public static enum DataType{
        BOOLEAN,STRING,TINYINT,SMALLINT,INTEGER,BIGINT,FLOAT,
        DOUBLE,BINARY,POINT,LINE,RECTANGLE,CIRCLE,POLYGON,DATE,
        TIME,DATETIME,DURATION,INTERVAL,UUID,OBJECT,
        ARRAY,MULTISET
    }

public static Map<String,String> toMap() {
        Map<String,String> map = new LinkedHashMap<>();
        for(DataType d: DataType.values()){
            map.put(d.name(),d.name());
        }
        return map;
    }
}
