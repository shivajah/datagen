package schema;

import Constants.DataTypes;
import Constants.Order;
import schema.Domain.Domain;

/**
 * Created by shiva on 3/1/18.
 */
public class Field {
    private int position;
    private DataTypes.DataType type;
    private String name;
    private boolean declared;
    private Optional optional;
    private Nullable nullable;
    private Domain domain;
    private Order.order order;

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


}

