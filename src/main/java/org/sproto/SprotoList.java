package org.sproto;

public class SprotoList {

    private SprotoType elemType;

    private SprotoStruct sprotoStruct;

    private SprotoList listElemSchema;

    public SprotoList(SprotoType elemType) {
        this.elemType = elemType;
    }

    public SprotoList( SprotoStruct sprotoStruct) {

        this.elemType = SprotoType.STRUCT;
        this.sprotoStruct = sprotoStruct;
    }

    public SprotoType getElemType() {

        return elemType;
    }

    public SprotoStruct getSprotoStruct() {

        return sprotoStruct;
    }

    public void setSprotoStruct(SprotoStruct sprotoStruct) {

        this.sprotoStruct = sprotoStruct;
    }

    public SprotoList getListElemSchema() {

        return listElemSchema;
    }

    public void setListElemSchema(SprotoList listElemSchema) {

        this.listElemSchema = listElemSchema;
    }
}
