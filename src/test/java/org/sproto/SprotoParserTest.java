package org.sproto;

import org.junit.Test;

import java.util.List;

public class SprotoParserTest {

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



    @Test
    public void testProtocol(){

        String s = "# This is a comment.\n" + "\n" + ".Person {\t# . means a user defined type \n"
                + "    name 0 : string\t# string is a build-in type.\n" + "    id 1 : integer\n"
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

        for(SprotoStruct struct : sprotoSchema.getTypes()){
            System.out.println(struct);
        }
    }
}
