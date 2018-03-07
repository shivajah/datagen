package schema;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiva on 3/1/18.
 */
public class Schema {
    private String datasetName;
    private List<Field> fields;
    private int cardinality;
    private String fileName;
    private int numOfPartitions;



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

    public int getCardinality() {
        return cardinality;
    }

    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNumOfPartitions() {
        return numOfPartitions;
    }

    public void setNumOfPartitions(int numOfPartitions) {
        this.numOfPartitions = numOfPartitions;
    }



}
