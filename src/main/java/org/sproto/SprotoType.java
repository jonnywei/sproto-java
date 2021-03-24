package org.sproto;

public enum SprotoType {

    INTEGER,
    BOOLEAN,
    STRING,
    BINARY,
    DOUBLE,
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

    /**
     * local buildin_types = {
     * 	integer = 0,
     * 	boolean = 1,
     * 	string = 2,
     * 	binary = 2,
     * 	double = 3,
     * }
     * @param type
     * @return
     */
    public static int buildin(SprotoType type){
        if(type == SprotoType.INTEGER){
            return 0;
        }
        if(type == SprotoType.BOOLEAN){
            return 1;
        }
        if(type == SprotoType.STRING){
            return 2;
        }
        if(type == SprotoType.BINARY){
            return 2;
        }
        if(type == SprotoType.DOUBLE){
            return 3;
        }
        return -1;
    }
}
