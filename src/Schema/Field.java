package Schema;

import Constants.DataTypes;

/**
 * Created by shiva on 3/1/18.
 */
public abstract class Field {
    private int position;
    private DataTypes.DataType type;
    private String name;
    private boolean declared;
    private Optional optional;
    private Nullable nullable;
    //Range & Domain

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public Optional getOptional() {
        return optional;
    }

    public void setOptional(Optional optional) {
        this.optional = optional;
    }

    public Nullable getNullable() {
        return nullable;
    }

    public void setNullable(Nullable nullable) {
        this.nullable = nullable;
    }

}

