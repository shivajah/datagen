package schema;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiva on 3/1/18.
 */
public class Schema {
    private String datasetName;
    private List<Field> fields;
    public Schema(){
        this.fields = new LinkedList<>();
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }


}
