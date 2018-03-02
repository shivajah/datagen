package Generators;

import Constants.DataTypes;
import Output.OutputGenerator;

import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.Random;

/**
 * Created by shiva on 2/24/18.
 */
public class GeneratorManager {
   private Map<String, List<String>> parameters;
   private int rowNumbers ;
    private Random ran;
    public GeneratorManager(Map<String, List<String>> parameters){
        this.parameters = parameters;
        ran =  new Random();
    }

    public void generateRows(){
        if(!parameters.isEmpty()){
            String rows = parameters.get("noRows").get(0);
            rowNumbers = (rows == null)? 1000 : Integer.valueOf(rows);
            OutputGenerator output = new OutputGenerator(parameters);
            List<IGenerator> generators = getGenerators();
            for(int j=0;j<rowNumbers;j++) {
                String st = "{";
                for (int i = 0; i < generators.size(); i++) {
                    String value;
                    if(i>0){
                        st +=", ";
                    }
                    IGenerator generator = generators.get(i);
                    if (generator instanceof StringGenerator) {
                        int  k  = ran.nextInt() & Integer.MAX_VALUE;
                        value = "\"" + ((StringGenerator) generator).next(k) + "\"";
                    } else {
                        value = String.valueOf(((NumberGenerator) generator).next());
                    }
                    st += "\"" + parameters.get("id").get(i) + "\"" + " : " + value;
                }
                st += "}\n";
                output.write(st);
            }
            output.close();
        }

    }

private List<IGenerator> getGenerators(){
        List<IGenerator> generators = new LinkedList<>();
        List<String> types = parameters.get("type");
        int index  = 0;
        for(String type: types){
            DataTypes.DataType t = DataTypes.DataType.valueOf(type);
            IGenerator generator = null;
            switch (t){
                case BOOLEAN:
                    generator = new BooleanGenerator(rowNumbers);
                    break;
                case STRING:
                    generator = new StringGenerator();
                    break;
                case INTEGER:
                    generator = new IntegerGenerator(parameters,index);
                    break;
                case TINYINT:
                    generator = new TinyIntGenerator(parameters,index);
                    break;
                case BIGINT:
                    generator = new BigIntGenerator(parameters,index);
                    break;
                case SMALLINT:
                    generator = new SmallIntGenerator(parameters,index);
                    break;
                default:
                    System.out.println("Under Construction!");
                    break;
            }
            if(generator != null){
                generators.add(generator);
            }
            index++;
        }

        return generators;
}
}
