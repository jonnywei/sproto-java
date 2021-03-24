package org.sproto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SprotoTestBase {


    public  SprotoStruct buildPersonSchema() {

        SprotoStruct person = new SprotoStruct("Person");
        person.addField(new SprotoField("name",0, SprotoType.STRING));
        person.addField( new SprotoField("age",1, SprotoType.INTEGER));
        person.addField(new SprotoField("marital",2, SprotoType.BOOLEAN));
        person.addField(new SprotoField("children",3,person,true));
        return person;

    }




    public static Map<String,Object> buildPerson(){
        Map<String,Object> person = new HashMap<>();
        person.put("name","Alice");
        person.put("age",13);
        person.put("marital",false );
        return person;
    }

    public byte[] personEncodeByte(){
        String expectStr = "03 00 (fn = 3)\n" + "00 00 (id = 0, value in data part)\n" + "1C 00 (id = 1, value = 13)\n"
                + "02 00 (id = 2, value = false)\n" + "05 00 00 00 (sizeof \"Alice\")\n" + "41 6C 69 63 65 (\"Alice\")";
       return   HexByteUtil.hexStringToByteArray(expectStr);

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



    public byte[] personHaveChild(){
        String s="04 00 (fn = 4)\n" + "00 00 (id = 0, value in data part)\n" + "52 00 (id = 1, value = 40)\n"
                + "01 00 (skip id = 2)\n" + "00 00 (id = 3, value in data part)\n" + "\n"
                + "03 00 00 00 (sizeof \"Bob\")\n" + "42 6F 62 (\"Bob\")\n" + "\n" + "26 00 00 00 (sizeof children)\n"
                + "\n" + "0F 00 00 00 (sizeof child 1)\n" + "02 00 (fn = 2)\n" + "00 00 (id = 0, value in data part)\n"
                + "1C 00 (id = 1, value = 13)\n" + "05 00 00 00 (sizeof \"Alice\")\n" + "41 6C 69 63 65 (\"Alice\")\n"
                + "\n" + "0F 00 00 00 (sizeof child 2)\n" + "02 00 (fn = 2)\n" + "00 00 (id = 0, value in data part)\n"
                + "0C 00 (id = 1, value = 5)\n" + "05 00 00 00 (sizeof \"Carol\")\n" + "43 61 72 6F 6C (\"Carol\")";

        return HexByteUtil.hexStringToByteArray(s);
    }



    public static SprotoStruct buildDataSchema() {

        SprotoStruct person = new SprotoStruct("Data");
        person.addField(new SprotoField("numbers",0,SprotoType.INTEGER,true));
        person.addField( new SprotoField("bools",1,SprotoType.BOOLEAN,true));
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




    public static Map<String,Object> buildDataBools(){
        Map<String,Object> data = new HashMap<>();

        List<Boolean> childs = new ArrayList<>();
        childs.add(false);
        childs.add(true);
        childs.add(false);

        data.put("bools",childs );
        return data;
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

    public static Map<String,Object> buildDataBigNumberList(){
        Map<String,Object> data = new HashMap<>();

        data.put("number",100000 );
        data.put("bignumber",-10000000000l );
        return data;
    }
}
