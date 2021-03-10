package org.sproto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// decoder sproto
public class SprotoDecoder {

    public static Object decodeStruct(SprotoStruct schema,  byte[] data) {
        return decodeStruct(schema,SprotoByteBuffer.wrap(data));
    }

        public static Object decodeStruct(SprotoStruct schema,  SprotoByteBuffer fieldBuffer){

        Map<String ,Object> structObj = new HashMap<>();

        int fieldCount = fieldBuffer.getShort(); //  header field

        int dataOffset = (fieldCount) *2;

        SprotoByteBuffer dataBuffer =  SprotoByteBuffer.wrap(fieldBuffer);
        dataBuffer.position(fieldBuffer.position()+ dataOffset);

        int tag  =0;

        for(int i = 0; i <fieldCount; i++){

            int tagValue = fieldBuffer.getShort(); //  tag field

            if (tagValue %2 == 1){
                int skip = (tagValue +1 ) /2;
                tag = tag + skip;
                continue;
            }
            SprotoField field = findFieldByFieldNumber(schema,tag);
            tag++;
            if (field == null){ //not in schema
                continue;
            }

            String fieldName = field.getName();
            if(tagValue != 0){ // value in field
                int v = tagValue /2 -1;
                if (field.getType() == SprotoType.BOOLEAN){
                    if(v ==1){
                        structObj.put(fieldName,Boolean.TRUE);
                    }else {
                        structObj.put(fieldName,Boolean.FALSE);
                    }
                }else {
                    structObj.put(fieldName, v);
                }
                continue;
            }
            Object v = null;
            //value in dataBuffer
            if(field.isArray()) {
                v =  decodeList(field, dataBuffer );
                structObj.put(fieldName,v );
                continue;
            }
            if((field.getType() == SprotoType.INTEGER )){
                int length = dataBuffer.getInt();
                if(length == 4){
                     v = dataBuffer.getInt();
                }else {
                     v = dataBuffer.getLong();
                }
            }
            if(field.getType() == SprotoType.STRING) {
                v = decodeStringData(dataBuffer);
            }
            if(field.getType() == SprotoType.BINARY) {
                v = decodeBinaryData(dataBuffer);
            }

            if(field.getType() == SprotoType.DOUBLE){
                v = decodeDoubleData(dataBuffer);
            }

            if(field.getType() == SprotoType.STRUCT){
                SprotoStruct sprotoStruct = field.getSprotoStruct();
                v = decodeStruct(sprotoStruct,dataBuffer);
            }

            structObj.put(fieldName,v );

        }
        fieldBuffer.position(dataBuffer.position());
        return structObj;
    }

    public static Object decodeList(SprotoField field, SprotoByteBuffer  byteBuffer){

        List<Object> list = new ArrayList<>();
        SprotoType elemType = field.getType();

        int totalLen = byteBuffer.getInt();  // total   length

        if (elemType == SprotoType.BOOLEAN){
            for(int i = 0; i <totalLen; i++ ){
                byte v = byteBuffer.getByte();
                if (v  == 0  ){
                    list.add(Boolean.FALSE);
                }else {
                    list.add(Boolean.TRUE);
                }
            }
        }
        if (elemType == SprotoType.STRING){
            for(Object sobj : list){
                String str = decodeStringData(byteBuffer);
            }
        }
        if (elemType == SprotoType.INTEGER){
            int type = byteBuffer.getByte();
            for(int index = totalLen -1; index - type >= 0; index = index - type){
                if (type == 4){
                    Integer v =  decodeIntegerData(byteBuffer);
                    list.add(v);
                }else {
                    Long l = decodeLongData(byteBuffer);
                    list.add(l);
                }
            }
        }

        if (elemType == SprotoType.BINARY){
            for(Object byteO : list){
                byte[] byteArray = decodeBinaryData(byteBuffer) ;
                byteBuffer.putBytes((byteArray));
            }
        }
        if (elemType == SprotoType.DOUBLE){
            for(Object o : list){
                 Double aDouble = decodeDoubleData(byteBuffer) ;
            }
        }

        if (elemType == SprotoType.STRUCT){
            int restLength = totalLen;
            do {
                int structLength = byteBuffer.getInt();
                restLength = restLength - structLength -4;
                SprotoStruct elemStructSchema = field.getSprotoStruct();
                Object struct =  decodeStruct(elemStructSchema,byteBuffer);
                list.add(struct);
            }while (restLength > 0);
        }

//        if (elemType == SprotoType.LIST){
//            for(Object listObject : list){
//                SprotoList elemListSchema = listSchema.getListElemSchema();
//                decodeList(elemListSchema,byteBuffer);
////                byteBuffer.putSprotoByteBuffer();
//            }
//        }
//        int size = byteBuffer.position();
//        byteBuffer.putInt(0, size-4);
        return list;
    }

    public static SprotoField findFieldByFieldNumber(SprotoStruct schema, int number){
        for(SprotoField field : schema.getFields()){
            if (field.getNumber() == number){
                return field;
            }
        }
        return null;
    }

    public static  String decodeStringData( SprotoByteBuffer dataBuffer ){
        int length = dataBuffer.getInt();
        return  new String(dataBuffer.getBytes(length));
    }




    public static  byte[] decodeBinaryData(SprotoByteBuffer byteBuffer){
        int length = byteBuffer.getInt();
        return  byteBuffer.getBytes(length);
    }

    public static int decodeIntegerData(SprotoByteBuffer byteBuffer){
        return  byteBuffer.getInt();
    }


    public static long decodeLongData(SprotoByteBuffer byteBuffer){
        return  byteBuffer.getLong();
    }
    public static Double decodeDoubleData(SprotoByteBuffer byteBuffer){
        return   byteBuffer.getDouble();
    }


}
