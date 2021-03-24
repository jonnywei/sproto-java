package org.sproto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoDecodeTest extends SprotoTestBase {



    @Test
    public   void testPerson() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPerson();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

        Object  a = SprotoDecoder.decodeStruct(personSchema,result);


    }

    @Test
    public   void testPersonHaveTwoChilds() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPersonHaveChild();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

        Object  a = SprotoDecoder.decodeStruct(personSchema,result);

    }


    @Test
    public   void testData() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataNumbers();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);


        Object  a = SprotoDecoder.decodeStruct(personSchema,result);

    }


    @Test
    public   void testDataBool() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBools();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);
        Object  a = SprotoDecoder.decodeStruct(personSchema,result);

    }





    @Test
    public   void testDataBigNumber() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumber();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);


        Object  a = SprotoDecoder.decodeStruct(personSchema,result);


    }



    @Test
    public   void testDataBigNumbers() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumberList();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);
        Object  a = SprotoDecoder.decodeStruct(personSchema,result);

    }





}
