package org.sproto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SprotoEncodeTest  extends  SprotoTestBase{


    @Test
    public   void testPerson() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPerson();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

        Assert.assertArrayEquals(personEncodeByte(),result);
    }

    @Test
    public   void testPersonHaveTwoChilds() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPersonHaveChild();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

        Assert.assertArrayEquals(personHaveChild(),result);

    }



    @Test
    public   void testData() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataNumbers();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

    }

    @Test
    public   void testDataBool() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBools();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

    }



    @Test
    public   void testDataBigNumber() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumber();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

    }


    @Test
    public   void testDataBigNumbers() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumberList();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        HexByteUtil.printHex(result);

    }



}
