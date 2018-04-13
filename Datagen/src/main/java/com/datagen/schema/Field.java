package com.datagen.schema;

import com.datagen.Constants.DataTypes;
import com.datagen.Constants.Order;
import com.datagen.schema.Domain.Domain;

import java.io.InvalidClassException;

/**
 * Created by shiva on 3/1/18.
 */
public class Field {
    private Schema schema;
    private DataTypes.DataType type;
    private String name;
    private boolean declared;
    private boolean optional;
    private boolean nullable;
    private int nulls;
    private int missings;
    private Domain domain;
    private Order.order order;
    private int stringLength;
    private int range;
    private boolean even;
    private boolean odd;
    public int getSizeInBytes(int cardinality){
        if(range > 0){
            cardinality = range;
        }
        if(type== DataTypes.DataType.INTEGER){
            if(cardinality <= 127 && cardinality >= -128){//tinyint
                return 1; //Byte
            }
            else if(cardinality <= 32767 && cardinality >= -32768){//smallint
                return 2;
            }
            else if(cardinality <= 2147483647 && cardinality >= -2147483648){//int
                return 4;
            }
            else{//bigint
                return 8;
            }
        }
        else if(type==DataTypes.DataType.STRING){
            return stringLength * Character.BYTES;
        }
     return 0;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public DataTypes.DataType getType() {
        return type;
    }

    public void setType(DataTypes.DataType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeclared() {
        return declared;
    }

    public void setDeclared(boolean declared) {
        this.declared = declared;
    }


    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }


    public Order.order getOrder() {
        return order;
    }

    public void setOrder(Order.order order) {
        this.order = order;
    }

    public int getStringLength() {
        return stringLength;
    }

    public void setStringLength(int stringLength) {
        if(stringLength !=0 )
        this.stringLength = stringLength;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getNulls() {
        return nulls;
    }

    public void setNulls(int nulls) {
        this.nulls = nulls;
    }

    public int getMissings() {
        return missings;
    }

    public void setMissings(int missings) {
        this.missings = missings;
    }

    public boolean isEven() {
        return even;
    }

    public void setEven(boolean even) {
        this.even = even;
    }

    public boolean isOdd() {
        return odd;
    }

    public void setOdd(boolean odd) {
        this.odd = odd;
    }

}

