package org.sproto;

/**
 * dump sproto file to spb
 */
public class SprotoDump {

    private static String protoSchema =
            ".type {\n" + "    .field {\n" + "        name 0 : string\n"
            + "        buildin\t1 : integer\n"
            + "        type 2 : integer\t# type is fixed-point number precision when buildin is SPROTO_TINTEGER; When buildin is SPROTO_TSTRING, it means binary string when type is 1.\n"
            + "        tag 3 : integer\n" + "        array 4\t: boolean\n"
            + "        key 5 : integer # If key exists, array must be true, and it's a map.\n" + "   "
            +"         map 6 : boolean "
                     + " }\n"
            + "    name 0 : string\n"
            + "    fields 1 : *field\n"
            + "}\n" + "\n" + ".protocol {\n"
            + "    name 0 : string\n" + "    tag 1 : integer\n" + "    request 2 : integer # index\n"
            + "    response 3 : integer # index\n"
             + "}\n" + "\n" + ".group {\n"
            + "    type 0 : *type\n" + "    protocol 1 : *protocol\n" + "}";

    private static SprotoStruct group = null;
    static {
            SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(protoSchema);
            for(SprotoStruct struct : sprotoSchema.getTypes()){
                if(struct.getName().equals("group")){
                    group = struct;
                }
            }
    }

    public static byte[] dump(SprotoSchema sprotoSchema){
        byte[] bytes = SprotoEncoder.encodeStruct(group, sprotoSchema.toMap());
        return bytes;

    }

    public static byte[] dump(String sprotoSchemaStr){
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(sprotoSchemaStr);
        byte[] bytes = SprotoEncoder.encodeStruct(group, sprotoSchema.toMap());
        return bytes;

    }

}
