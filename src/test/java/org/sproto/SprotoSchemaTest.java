package org.sproto;

public class SprotoSchemaTest {


    public static void mwwwain(String[] args) {

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

        person.addField(new SprotoField("height",4,SprotoType.INTEGER));
        person.addField(new SprotoField("data",5,SprotoType.BINARY));
        person.addField(new SprotoField("weight ",6,SprotoType.DOUBLE));



        SprotoStruct addressBook  = new SprotoStruct("AddressBook");
        addressBook.addField(new SprotoField("person",0,new SprotoList(person)));


    }
}
