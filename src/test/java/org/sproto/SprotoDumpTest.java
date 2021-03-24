package org.sproto;

import org.junit.Test;

import java.util.List;

public class SprotoDumpTest {
    @Test
    public void testSchemaAll(){
        String protocolStr = ".type {\n" + "    .field {\n" + "        name 0 : string\n"
                + "        buildin\t1 : integer\n"
                + "        type 2 : integer\t# type is fixed-point number precision when buildin is SPROTO_TINTEGER; When buildin is SPROTO_TSTRING, it means binary string when type is 1.\n"
                + "        tag 3 : integer\n" + "        array 4\t: boolean\n"
                + "        key 5 : integer # If key exists, array must be true, and it's a map.\n" + "    }\n"
                + "    name 0 : string\n" + "    fields 1 : *field\n" + "}\n" + "\n" + ".protocol {\n"
                + "    name 0 : string\n" + "    tag 1 : integer\n" + "    request 2 : integer # index\n"
                + "    response 3 : integer # index\n"
                + "    confirm 4 : boolean # response nil where confirm == true\n" + "}\n" + "\n" + ".group {\n"
                + "    type 0 : *type\n" + "    protocol 1 : *protocol\n" + "}";

        List<String> t = SprotoSchemaParser.generateToken(protocolStr);
        for(String st : t){
            System.out.println(st);
        }
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(t);

        SprotoStruct group = null;
        for(SprotoStruct struct : sprotoSchema.getTypes()){
            if(struct.getName().equals("group")){
                group = struct;
            }
            System.out.println(struct);
        }

        byte[] bytes = SprotoEncoder.encodeStruct(group, sprotoSchema.toMap());

        HexByteUtil.printHex(bytes);

    }


    @Test
    public void testSchemaPersonAll(){


        String person = ".Person {\n" + "    name 0 : string\n" + "    id 1 : integer\n" + "    email 2 : string\n"
                + "\n" + "    .PhoneNumber {\n" + "        number 0 : string\n" + "        type 1 : integer\n"
                + "    }\n" + "\n" + "    phone 3 : *PhoneNumber\n" + "}\n" + "\n" + ".AddressBook {\n"
                + "    person 0 : *Person\n" + "}";
        byte[] bytes = SprotoDump.dump(person);
        HexByteUtil.printHex(bytes);

    }


    @Test
    public void testSchemaPhoneNumber(){


        String person = ".PhoneNumber {\n" + "        number 0 : string\n" + "        type 1 : integer\n" + "    }";
        byte[] bytes = SprotoDump.dump(person);
        HexByteUtil.printHex(bytes);

    }


    @Test
    public void testSchemaPerson(){


        String person = ".Person {\n" + "    name 0 : string\n" + "    id 1 : integer\n" + "    email 2 : string\n"
                + "\n" + "    .PhoneNumber {\n" + "        number 0 : string\n" + "        type 1 : integer\n"
                + "    }\n" + "\n" + "    phone 3 : *PhoneNumber\n" + "}";
        byte[] bytes = SprotoDump.dump(person);
        HexByteUtil.printHex(bytes);

    }


}
