package org.sproto;

public enum SprotoType {

    STRING,
    INTEGER,
    BINARY,
    DOUBLE,
    BOOLEAN,
    STRUCT;

    public static SprotoType typeOf(String str){
        if(str.equals("string")){
            return SprotoType.STRING;
        }
        if(str.equals("integer")){
            return SprotoType.INTEGER;
        }
        if(str.equals("binary")){
            return SprotoType.BINARY;
        }
        if(str.equals("double")){
            return SprotoType.DOUBLE;
        }
        if(str.equals("boolean")){
            return SprotoType.BOOLEAN;
        }
        return STRUCT;
    }
}
