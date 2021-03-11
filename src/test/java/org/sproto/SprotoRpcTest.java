package org.sproto;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoRpcTest {

    @Test
    public void testToken(){

        String s = "# This is a comment.\n" + "\n" + ".Person {\t# . means a user defined type \n"
                + "    name 0 : string\t# string is a build-in type.\n" + "    id 1 : integer\n"
                + "    email 2 : string\n" + "\n" + "    .PhoneNumber {\t# user defined type can be nest.\n"
                + "        number 0 : string\n" + "        type 1 : integer\n" + "    }\n" + "\n"
                + "    phone 3 : *PhoneNumber\t# *PhoneNumber means an array of PhoneNumber.\n"
                + "    height 4 : integer(2)\t# (2) means a 1/100 fixed-point number.\n"
                + "    data 5 : binary\t\t# Some binary data\n" + "    weight 6 : double   # floating number\n" + "}\n"
                + "\n" + ".AddressBook {\n"
                + "    person 0 : *Person(id)\t# (id) is optional, means Person.id is main index.\n" + "}\n";


        List<String> t = SprotoSchemaParser.generateToken(s);
        for(String st : t){
            System.out.println(st);
        }
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(t);

    }




    @Test
    public void testSimpleToken(){


        String s = ".Person {\n" + "    name 0 : string\n" + "    age 1 : integer #ddddd\n" + "    marital 2 : boolean\n"
                + "    children 3 : *Person\n" + "}";

        List<String> t = SprotoSchemaParser.generateToken(s);
        for(String st : t){
            System.out.println(st);
        }

        SprotoSchema sprotoSchema =    SprotoSchemaParser.parse(t);
    }



    public static Map<String,Object> buildPerson(){
        Map<String,Object> person = new HashMap<>();
        person.put("name","Alice");
        person.put("age",13);
        person.put("marital",false );
        return person;
    }

    @Test
    public void testProtocol(){

        String s = "# This is a comment.\n" + "\n" + ".Person {\t# . means a user defined type \n"
                + "    name 0 : string\t# string is a build-in type.\n" + "    id 1 : integer\n"
                + "    age 1 : integer #ddddd\n" + "    marital 2 : boolean\n"
                + "    email 2 : string\n" + "\n" + "    .PhoneNumber {\t# user defined type can be nest.\n"
                + "        number 0 : string\n" + "        type 1 : integer\n" + "    }\n" + "\n"
                + "    phone 3 : *PhoneNumber\t# *PhoneNumber means an array of PhoneNumber.\n"
                + "    height 4 : integer(2)\t# (2) means a 1/100 fixed-point number.\n"
                + "    data 5 : binary\t\t# Some binary data\n" + "    weight 6 : double   # floating number\n" + "}\n"
                + "\n" + ".AddressBook {\n"
                + "    person 0 : *Person(id)\t# (id) is optional, means Person.id is main index.\n" + "}\n" + "\n"
                + "foobar 1 {\t# define a new protocol (for RPC used) with tag 1\n"
                + "    request Person\t# Associate the type Person with foobar.request\n"
                + "    response {\t# define the foobar.response type\n" + "        ok 0 : boolean\n" + "    }\n" + "}";


        List<String> t = SprotoSchemaParser.generateToken(s);
        for(String st : t){
            System.out.println(st);
        }
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(t);


        SprotoRpc sprotoRpc = new SprotoRpc(sprotoSchema);

        byte[] request = sprotoRpc.request("foobar",buildPerson(),1000L);

        HexByteUtil.printHex(request);

        SprotoRpc.RpcMessage message = sprotoRpc.dispatch(request);

        System.out.println(message);
    }


    @Test
    public void testProtocolAll(){
        String protocolStr = ".package {\n" + "\ttype 0 : integer\n" + "\tsession 1 : integer\n" + "}\n"
                + "foobar 1 {\n" + "\trequest {\n" + "\t\twhat 0 : string\n" + "\t}\n" + "\tresponse {\n"
                + "\t\tok 0 : boolean\n" + "\t}\n" + "}\n" + "foo 2 {\n" + "\tresponse {\n" + "\t\tok 0 : boolean\n"
                + "\t}\n" + "}\n" + "bar 3 {\n" + "\tresponse nil\n" + "}\n" + "blackhole 4 {\n" + "}";

        List<String> t = SprotoSchemaParser.generateToken(protocolStr);
        for(String st : t){
            System.out.println(st);
        }
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(t);

        for(SprotoProtocol protocol : sprotoSchema.getProtocols()){
            System.out.println(protocol);
        }
    }
}
