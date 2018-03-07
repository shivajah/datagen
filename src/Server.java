/**
 * Created by shiva on 2/21/18.
 */

import Constants.DataTypes;
import Generators.NumberGenerator;
import Generators.StringGenerator;
import schema.Field;
import schema.Schema;

public class Server {
public static final String wisconsin = "WISCONSIN";

public static String algo="shiva's";
    public static void main(String[] args) throws Exception {
        if(args.length>0  ){
            if (args[0].equalsIgnoreCase(wisconsin))
                algo = wisconsin;
            throw new IllegalArgumentException("Unknown argument was sent to the program.");
        }
            Parser parser = new Parser();
            Schema schema = parser.parseConfigFile();
            for(Field field: schema.getFields()){
                if(field.getType()== DataTypes.DataType.INTEGER){
                    NumberGenerator ng = new NumberGenerator(field);
                    int len= ng.getNumberOfValuesInDomain();
                    for(int i =0; i<len;i++){
                        System.out.println(ng.nextNumber());
                    }
                }
                else if(field.getType()== DataTypes.DataType.STRING){
                    StringGenerator sg = new StringGenerator(field);
                    int len= sg.getNumberOfValuesInDomain();
                    for(int i =0; i<len;i++){
                        System.out.println(sg.nextString());
                    }
                }

            }
    }
}
