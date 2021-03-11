package org.sproto;

import java.util.List;
import java.util.Map;

public class SprotoEncoder {

    public static byte[] encodeStruct(SprotoStruct schema, Object structObj){
       return encode (schema, (Map<String,Object>)structObj);
    }

    private static byte[] encode(SprotoStruct schema, Map<String,Object>  o){
        SprotoByteBuffer fieldBuffer =  SprotoByteBuffer.allocate(256);
        SprotoByteBuffer dataBuffer =  SprotoByteBuffer.allocate(256);
        fieldBuffer.putShort(0); // default set zero, header field

        int fieldCount = 0;
        int tag  =0;
        int lastTagNumber  = -1;
        for(SprotoField field : schema.getFields()){

            String fieldName =  field.getName();

            if (! o.containsKey(fieldName)){
                tag ++;
                continue;
            }
            //
            if(field.getNumber() > lastTagNumber + 1 ){
                int skipCount = 2 * (field.getNumber() - lastTagNumber-1) -1 ;
                fieldBuffer.putShort(skipCount);
                fieldCount++;
            }

            fieldCount++;
            lastTagNumber = field.getNumber();
            Object v = o.get(fieldName);
            tag ++;
            if(field.isArray()){
                fieldBuffer.putShort( 0);
                dataBuffer.putSprotoByteBuffer( encodeList(field, (List<Object> )v));
                continue;
            }
            if(field.getType() == SprotoType.BOOLEAN){
                int ev = (0 +1) *2;
                if (Boolean.TRUE.equals(v )){
                    ev  = (1+2) *2;
                }
                fieldBuffer.putShort(ev);
            }

            if((field.getType() == SprotoType.INTEGER )){
                if (v instanceof Long){
                    long iv = (Long) v;
                    fieldBuffer.putShort(0);
                    dataBuffer.putInt(8); // size of number
                    dataBuffer.putBytes(encodeLongData(iv));
                }else {
                    int iv = (Integer) v;
                    if(iv >= 0 && iv  <= 32767){
                        int ev = (iv  +1) *2;
                        fieldBuffer.putShort(ev);
                    }else {
                        fieldBuffer.putShort(0);
                        dataBuffer.putInt(4); // size of number
                        dataBuffer.putBytes(encodeIntegerData((int)iv));
                    }
                }
            }
            if(field.getType() == SprotoType.STRING) {
                fieldBuffer.putShort( 0);
                dataBuffer.putBytes( encodeStringData(v.toString()));
            }

            if(field.getType() == SprotoType.BINARY) {
                fieldBuffer.putShort( 0);
                dataBuffer.putBytes( encodeBinaryData((byte[])v));
            }

            if(field.getType() == SprotoType.DOUBLE){
                dataBuffer.putBytes( encodeDoubleData((double)v));
            }

            if(field.getType() == SprotoType.STRUCT){
                SprotoStruct sprotoStruct = field.getSprotoStruct();
                fieldBuffer.putShort( 0);
                dataBuffer.putBytes( encodeStruct(sprotoStruct ,v));
            }


        }
        fieldBuffer.putShort(0, fieldCount);
                    // redefine filedCount


        fieldBuffer.putSprotoByteBuffer(dataBuffer);
        return fieldBuffer.get();
    }

    public static SprotoByteBuffer encodeList(SprotoField field , List<Object> list){
        SprotoType elemType =field.getType();
        SprotoByteBuffer byteBuffer =   SprotoByteBuffer.allocate(2);
        byteBuffer.putInt(0);  // total   length

        if (elemType == SprotoType.BOOLEAN){
            for(Object b : list){
                if (Boolean.TRUE.equals(b)){
                    byteBuffer.putByte((byte)1);
                }else {
                    byteBuffer.putByte( (byte)0);
                }
            }
        }
        if (elemType == SprotoType.STRING){
            for(Object sobj : list){
                String str = (String) sobj;
                byteBuffer.putBytes(encodeStringData(str));
            }
        }
        if (elemType == SprotoType.INTEGER){
            boolean int32 =true;
            boolean sizeSet = false;
            for(Object sobj : list){
                if(!sizeSet){
                    if(sobj instanceof Long){
                        byteBuffer.putByte((byte)8);
                        int32 = false;
                    }else {
                        byteBuffer.putByte((byte)4); // put size of int32
                    }
                    sizeSet =true;
                }
                if(int32){
                    Integer str = (Integer) sobj;
                    byteBuffer.putBytes(encodeIntegerData(str));
                }else {
                    Long str = (Long) sobj;
                    byteBuffer.putBytes(encodeLongData(str));
                }

            }
        }

        if (elemType == SprotoType.BINARY){
            for(Object byteO : list){
                byte[] byteArray = (byte[]) byteO ;
                byteBuffer.putBytes(encodeBinaryData(byteArray));
            }
        }
        if (elemType == SprotoType.DOUBLE){
            for(Object o : list){
                 Double aDouble = (Double) o ;
                byteBuffer.putBytes(encodeDoubleData(aDouble));
            }
        }

        if (elemType == SprotoType.STRUCT){
            for(Object structObj : list){
                SprotoStruct elemStructSchema = field.getSprotoStruct();
                byte[] struct =  encodeStruct(elemStructSchema,structObj);
                byteBuffer.putInt(struct.length);
                byteBuffer.putBytes(struct);
            }
        }

//        if (elemType == SprotoType.LIST){
//            for(Object listObject : list){
//                SprotoList elemListSchema = listSchema.getListElemSchema();
//                byteBuffer.putSprotoByteBuffer(encodeList(elemListSchema,(List<Object>)listObject));
//            }
//        }
        int size = byteBuffer.position();
        byteBuffer.putInt(0, size-4);
        return byteBuffer;
    }


    public static  byte[] encodeStringData(String s){
        SprotoByteBuffer byteBuffer = SprotoByteBuffer.allocate(s.length() +4);
        byteBuffer.putInt( s.getBytes().length);
        byteBuffer.putBytes(s.getBytes());
        return   byteBuffer.array();
    }




    public static  byte[] encodeBinaryData(byte[] bytes){
        SprotoByteBuffer byteBuffer = SprotoByteBuffer.allocate(bytes.length +4 );
        byteBuffer.putInt( bytes.length );
        byteBuffer.putBytes(bytes);
        return   byteBuffer.array();
    }

    public static byte[] encodeIntegerData(int integerValue){
        SprotoByteBuffer byteBuffer = SprotoByteBuffer.allocate(4);
        byteBuffer.putInt(integerValue);
        return  byteBuffer.array();
    }


    public static byte[] encodeLongData(long longValue){
        SprotoByteBuffer byteBuffer = SprotoByteBuffer.allocate(8);
        byteBuffer.putLong(longValue);
        return  byteBuffer.array();
    }
    public static  byte[] encodeDoubleData(Double integerValue){
        SprotoByteBuffer byteBuffer = SprotoByteBuffer.allocate(12);
        byteBuffer.putDouble(8);
        byteBuffer.putDouble(integerValue);
        return   byteBuffer.array();
    }


}
