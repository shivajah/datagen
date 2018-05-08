package com.datagen.Generators.wisconsingenerators;

import com.datagen.Constants.Order;
import com.datagen.schema.Field;
import com.datagen.schema.Schema;

/**
 * Created by shiva on 3/7/18.
 */
public class WisconsinNumberGenerator extends WisconsinGenerator{
    public WisconsinNumberGenerator(Schema schema, int fieldId){
        super(schema,fieldId);
    }


    @Override
    public Object next(long seed){
        Field field = schema.getFields().get(fieldId);
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
            return Long.MAX_VALUE;
        }
        long result;
            if(field.getOrder()== Order.order.RANDOM)
                result = rand(seed, schema.getCardinality());
            else
                result= seed;
            if(field.getRange()>0){
                result = result %field.getRange();
            }
            if(field.isEven()){
                result *=2;
            }
            if (field.isOdd()){
                result = result*2+1;
            }
            return result;
    }

}
