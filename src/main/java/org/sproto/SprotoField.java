package org.sproto;

public class SprotoField {

    private String name;

    private int number;

    private SprotoType type;

    private SprotoList list;

    private SprotoStruct sprotoStruct;


    private Object defaultValue;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getNumber() {

        return number;
    }

    public void setNumber(int number) {

        this.number = number;
    }

    public SprotoType getType() {

        return type;
    }

    public void setType(SprotoType type) {

        this.type = type;
    }

    public Object getDefaultValue() {

        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {

        this.defaultValue = defaultValue;
    }

    public SprotoList getList() {

        return list;
    }

    public void setList(SprotoList list) {

        this.list = list;
    }

    public SprotoField(String name, int number, SprotoType type) {

        this.name = name;
        this.number = number;
        this.type = type;
    }

    public SprotoField(String name, int number, SprotoList list) {

        this.name = name;
        this.number = number;
        this.type = SprotoType.LIST;
        this.list = list;
    }

    public SprotoStruct getSprotoStruct() {

        return sprotoStruct;
    }


}
