package com.datagen;

import com.datagen.Constants.DataTypes;
import com.datagen.Generators.wisconsingenerators.WisconsinGenerator;
import com.datagen.Generators.wisconsingenerators.WisconsinNumberGenerator;
import com.datagen.Generators.wisconsingenerators.WisconsinStringGenerator;
import com.datagen.Output.WisconsinOutputGenerator;
import com.datagen.Parser.Parser;
import com.datagen.schema.Field;
import com.datagen.schema.Schema;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by shiva on 2/21/18.
 */

public class Server {
    public static final String wisconsin = "WISCONSIN";


    public static String algo="shiva's";
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase(wisconsin))
                algo = wisconsin;
            else
                throw new IllegalArgumentException("Unknown argument was sent to the program.");
        }
        if (algo.equalsIgnoreCase(wisconsin)) {
            Parser parser = new Parser();
            List<Schema> schemas = parser.parseWisconsinConfigFile();
            for (Schema schema : schemas) {
                List<WisconsinGenerator> generators = new LinkedList<>();
                int fId = 0;
                WisconsinGenerator wg;
                for (Field field : schema.getFields()) {
                    if (field.getDomain() == null) {
                        //use id
                        if (field.getType() == DataTypes.DataType.INTEGER) {
                            wg = new WisconsinNumberGenerator(schema, fId);
                            generators.add(wg);
                        } else if (field.getType() == DataTypes.DataType.STRING) {
                            wg = new WisconsinStringGenerator(schema, fId);
                            generators.add(wg);
                        } else {
                            throw new IllegalArgumentException(
                                    "Invalid data type has been entered.Currently we only support String and Integer.");
                        }
                    }
                    fId++;
                }
                WisconsinOutputGenerator outputGenerators = new WisconsinOutputGenerator(schema, generators);
                outputGenerators.execute();
                System.out.println("maxRecordSize : "+outputGenerators.getMaxRecordLength()+" bytes");
            }
        }
//        else if (algo.equalsIgnoreCase("shiva's")) {
//            Parser parser = new Parser();
//            Schema schema = parser.parseConfigFile();
//            for (Field field : schema.getFields()) {
//                if (field.getType() == DataTypes.DataType.INTEGER) {
//                    NumberGenerator ng = new NumberGenerator(field);
//                    int len = ng.getNumberOfValuesInDomain();
//                    for (int i = 0; i < len; i++) {
//                        System.out.println(ng.nextNumber());
//                    }
//                } else if (field.getType() == DataTypes.DataType.STRING) {
//                    StringGenerator sg = new StringGenerator(field);
//                    int len = sg.getNumberOfValuesInDomain();
//                    for (int i = 0; i < len; i++) {
//                        System.out.println(sg.nextString());
//                    }
//                }
//
//            }
//        }
    }
}
