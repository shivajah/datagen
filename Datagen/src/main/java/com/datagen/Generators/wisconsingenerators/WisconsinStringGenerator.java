package com.datagen.Generators.wisconsingenerators;

import com.datagen.Constants.Order;
import com.datagen.schema.Field;
import com.datagen.schema.Schema;
import java.util.Stack;

/**
 * Created by shiva on 3/7/18.
 */
public class WisconsinStringGenerator extends WisconsinGenerator {

        public WisconsinStringGenerator(){
        super();
    }
        public WisconsinStringGenerator(Schema schema,int fieldId){
    super(schema,fieldId);
}

        public String next(long seed){
            Field f = schema.getFields().get(fieldId);
            if(nextNull<seed){
                nextNull=nextNull(Math.toIntExact(seed));
                schema.getFields().get(fieldId).setNulls(schema.getFields().get(fieldId).getNulls()+1);
            }
            if(nextMissing<seed){
                nextMissing=nextMissing(Math.toIntExact(seed));
                schema.getFields().get(fieldId).setMissings(schema.getFields().get(fieldId).getMissings()+1);
            }
            if( nextNull<= nextMissing && nextNull==seed){
                nextNull=nextNull(Math.toIntExact(seed)+1);
                return null;
            }
            if(nextMissing<=nextNull && nextMissing==seed){
                nextMissing = nextMissing(Math.toIntExact(seed)+1);
                return "";
            }
            if(f.getOrder()== Order.order.RANDOM){
                seed = rand(seed,schema.getCardinality());
            }
            if(f.getRange()>0){
                seed = seed % f.getRange();
            }
            Stack<Character> temp = new Stack<>();
            char[] field = {'A','A','A','A','A','A','A'};
            if(seed==0){
                long rem = seed % 26;
                temp.push ((char) ('A'+rem));
            }
            while(seed > 0){
                long rem = seed % 26;
                temp.push ((char) ('A'+rem));
                seed = seed/26;
            }
            int i=0;
            while(!temp.empty() && i<field.length){
                field[i]=temp.pop();
                i++;
            }
            return addPaddingX(String.valueOf(field));
        }

        private String addPaddingX(String value){
            int stLen=schema.getFields().get(fieldId).getStringLength();
            int diff = stLen-value.length();
            for(int i=0;i<diff;i++){
                value = value+'X';
            }
            return value;
        }

}
