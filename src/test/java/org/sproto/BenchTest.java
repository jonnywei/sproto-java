package org.sproto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchTest {


    public  SprotoStruct buildAddressBookSchema() {

        SprotoStruct phoneNumber = new SprotoStruct("PhoneNumber");
        SprotoField number  = new SprotoField("number",0, SprotoType.STRING);
        SprotoField type   = new SprotoField("type",1, SprotoType.INTEGER);
        phoneNumber.addField(number);
        phoneNumber.addField(type);


        SprotoStruct person = new SprotoStruct("Person");
        SprotoField name = new SprotoField("name",0, SprotoType.STRING);
        SprotoField id  = new SprotoField("id",1, SprotoType.INTEGER);
        SprotoField email  = new SprotoField("email",2, SprotoType.STRING);
        person.addField(name);
        person.addField(id);
        person.addField(email);

        SprotoField phone = new SprotoField("phone",3, SprotoType.LIST);

        SprotoList phoneList = new SprotoList(phoneNumber);
        phone.setList(phoneList);

        person.addField(phone);
//
//        person.addField(new SprotoField("height",4,SprotoType.INTEGER));
//        person.addField(new SprotoField("data",5,SprotoType.BINARY));
//        person.addField(new SprotoField("weight ",6,SprotoType.DOUBLE));


        SprotoStruct addressBook  = new SprotoStruct("AddressBook");
        addressBook.addField(new SprotoField("person",0,new SprotoList(person)));

        return addressBook;
    }




    public static Map<String,Object> buildAddressBook(){
        Map<String,Object> Alice = new HashMap<>();
        Alice.put("name","Alice");
        Alice.put("id",10000);

        List<Map<String,Object>> phones  = new ArrayList<>();

        Map<String,Object> phone1  = new HashMap<>();
        phone1.put("number","123456789");
        phone1.put("type",1);

        Map<String,Object> phone2  = new HashMap<>();
        phone1.put("number","87654321");
        phone1.put("type",2);

        phones.add(phone1);
        phones.add(phone2);
        Alice.put("phone", phones);



        Map<String,Object> Bob = new HashMap<>();
        Bob.put("name","Bob");
        Bob.put("id",20000);

        List<Map<String,Object>> phones2  = new ArrayList<>();


        Map<String,Object> phone21  = new HashMap<>();
        phone1.put("number","01234567890");
        phone1.put("type",3);

        phones2.add(phone21);
        Bob.put("phone", phones2);


        Map<String,Object> AddressBook   = new HashMap<>();
        List<Map<String,Object>> p2  = new ArrayList<>();

        p2.add(Alice);
        p2.add(Bob);

        AddressBook.put("person", p2);

        return AddressBook;
    }




    @Test
    public   void testEncodeAddressBook() {

        SprotoStruct personSchema = buildAddressBookSchema();

        Map<String,Object> person = buildAddressBook();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 100_0000; i++){

            byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);
        }
        System.out.println(System.currentTimeMillis() - start);
//        PrintUtil.print(result);
//
//        System.out.println(result.length);
    }



    @Test
    public   void testEncodeAddressBookPacked() {

        SprotoStruct personSchema = buildAddressBookSchema();

        Map<String,Object> person = buildAddressBook();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 100_0000; i++){

            byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);
            result = SprotoPack.pack(result);
        }
        System.out.println(System.currentTimeMillis() - start);
        //        PrintUtil.print(result);
        //
        //        System.out.println(result.length);
    }


    @Test
    public   void testDecodeAddressBook() {

        SprotoStruct personSchema = buildAddressBookSchema();

        Map<String,Object> person = buildAddressBook();

        long start = System.currentTimeMillis();
        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);

        for(int i = 0; i < 100_0000; i++){
            Object v  =  SprotoDecoder.decodeStruct(personSchema, result);

        }
        System.out.println(System.currentTimeMillis() - start);
        //        PrintUtil.print(result);
        //
        //        System.out.println(result.length);
    }

    @Test
    public   void testDecodeAddressBookUnpacked() {

        SprotoStruct personSchema = buildAddressBookSchema();

        Map<String,Object> person = buildAddressBook();

        long start = System.currentTimeMillis();
        byte[] result =  SprotoEncoder.encodeStruct(personSchema, person);
        result = SprotoPack.pack(result);
        for(int i = 0; i < 100_0000; i++){

            Object v  =  SprotoDecoder.decodeStruct(personSchema, SprotoPack.unpack(result));

        }
        System.out.println(System.currentTimeMillis() - start);
        //        PrintUtil.print(result);
        //
        //        System.out.println(result.length);
    }
}
