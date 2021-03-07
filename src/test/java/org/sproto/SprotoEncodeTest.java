package org.sproto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoEncodeTest {



    public static SprotoStruct buildPersonSchema() {

        SprotoStruct person = new SprotoStruct("Person");
        person.addField(new SprotoField("name",0, SprotoType.STRING));
        person.addField( new SprotoField("age",1, SprotoType.INTEGER));
        person.addField(new SprotoField("marital",2, SprotoType.BOOLEAN));
        person.addField(new SprotoField("children",3,new SprotoList(person)));
        return person;

    }





    public static Map<String,Object> buildPerson(){
        Map<String,Object> person = new HashMap<>();
        person.put("name","Alice");
        person.put("age",13);
        person.put("marital",false );
        return person;
    }



    public static Map<String,Object> buildPersonHaveChild(){
        Map<String,Object> person = new HashMap<>();
        person.put("name","Bob");
        person.put("age",40);

        Map<String,Object> Alice = new HashMap<>();
        Alice.put("name","Alice");
        Alice.put("age",13);

        Map<String,Object> Carol = new HashMap<>();
        Carol.put("name","Carol");
        Carol.put("age",5);

        List<Map<String,Object>> childs = new ArrayList<>();
        childs.add(Alice);
        childs.add(Carol);

        person.put("children",childs );
        return person;
    }

    @Test
    public   void testPerson() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPerson();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);

    }

    @Test
    public   void testPersonHaveTwoChilds() {

        SprotoStruct personSchema = buildPersonSchema();

        Map<String,Object> person = buildPersonHaveChild();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);



    }





    public static SprotoStruct buildDataSchema() {

        SprotoStruct person = new SprotoStruct("Data");
        person.addField(new SprotoField("numbers",0, new SprotoList(SprotoType.INTEGER)));
        person.addField( new SprotoField("bools",1, new SprotoList(SprotoType.BOOLEAN)));
        person.addField(new SprotoField("number",2, SprotoType.INTEGER));
        person.addField(new SprotoField("bignumber",3, SprotoType.INTEGER));
        return person;

    }


    public static Map<String,Object> buildDataNumbers(){
        Map<String,Object> data = new HashMap<>();

        List<Integer> childs = new ArrayList<>();
        childs.add(1);
        childs.add(2);
        childs.add(3);
        childs.add(4);
        childs.add(5);

        data.put("numbers",childs );
        return data;
    }


    @Test
    public   void testData() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataNumbers();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);

    }



    public static Map<String,Object> buildDataBools(){
        Map<String,Object> data = new HashMap<>();

        List<Boolean> childs = new ArrayList<>();
        childs.add(false);
        childs.add(true);
        childs.add(false);

        data.put("bools",childs );
        return data;
    }


    @Test
    public   void testDataBool() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBools();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);

    }





    public static Map<String,Object> buildDataBigNumber(){
        Map<String,Object> data = new HashMap<>();

        List<Long> childs = new ArrayList<>();
        childs.add((1L<<32)+1);
        childs.add((1L<<32)+2);
        childs.add((1L <<32)+ 3);

        data.put("numbers",childs );
        return data;
    }



    @Test
    public   void testDataBigNumber() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumber();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);

    }



    @Test
    public   void testDataBigNumbers() {

        SprotoStruct personSchema = buildDataSchema();

        Map<String,Object> person = buildDataBigNumberList();

        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        PrintUtil.print(result);

    }



    public static Map<String,Object> buildDataBigNumberList(){
        Map<String,Object> data = new HashMap<>();

        data.put("number",100000 );
        data.put("bignumber",-10000000000l );
        return data;
    }


}
