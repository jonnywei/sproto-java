package org.sproto;

import java.nio.ByteBuffer;

public class SprotoByteBuffer {

    private ByteBuffer buffer;

    private int initCapacity;

    private int minExpandSize  =1;


    public static SprotoByteBuffer allocate(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        return new SprotoByteBuffer( capacity);
    }

    public static SprotoByteBuffer wrap(byte[] array){
        return new SprotoByteBuffer( array);
    }

    public static SprotoByteBuffer wrap(SprotoByteBuffer origin){
        return new SprotoByteBuffer(origin.buffer.array());
    }

    public static SprotoByteBuffer wrap(SprotoByteBuffer origin, int offset){
        return new SprotoByteBuffer( origin, offset);
    }


    private  SprotoByteBuffer(SprotoByteBuffer origin, int offSet){
        this.initCapacity = origin.buffer.array().length- offSet;
        this.buffer =ByteBuffer.wrap(origin.buffer.array(),offSet, this.initCapacity);

    }


    private  SprotoByteBuffer(byte[] array){
        this.initCapacity = array.length;
        this.buffer =ByteBuffer.wrap(array);

    }

    private SprotoByteBuffer(int initCapacity) {
        this.initCapacity = initCapacity;
        this.buffer = ByteBuffer.allocate(initCapacity);

    }

    private void ensureSize(int need){
        if(buffer.remaining() < need){
            int actualSize = need - buffer.remaining();
            int newSize = actualSize > minExpandSize ? actualSize: minExpandSize;
            ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.limit() + newSize);
            buffer.flip();
            byteBuffer.put(buffer);
            this.buffer = byteBuffer;
        }

    }


    public  void putShort(int index, int value){
        ensureSize(2);
        buffer.put(index++,(byte) value);
        buffer.put(index,  (byte) (value>>8));
    }

    public  void putShort(int value){
        ensureSize(2);
        buffer.put((byte) value);
        buffer.put((byte) (value>>8));
    }


    public  int getShort(){
        int re = buffer.get() & 0XFF;
        int hi = (buffer.get() & 0XFF) << 8;
        return hi + re;
    }

    public  void putInt( int value){
        ensureSize(4);
        buffer.put((byte) value);
        buffer.put( (byte) (value>>8));
        buffer.put( (byte) (value>>16));
        buffer.put( (byte) (value>>24));
    }


    public  int getInt(){
        int value = buffer.get() & 0XFF;
        value = (buffer.get() & 0XFF) << 8 | value;
        value = (buffer.get() & 0XFF) << 16 | value;
        value = (buffer.get() & 0XFF) << 24 | value;
        return value;
    }

    public  long getLong(){
        long value = buffer.get() & 0XFFL;
        value = (buffer.get() & 0XFFL) << 8  | value;
        value = (buffer.get() & 0XFFL) << 16 | value;
        value = (buffer.get() & 0XFFL) << 24 | value;
        value = (buffer.get() & 0XFFL) << 32 | value;
        value = (buffer.get() & 0XFFL) << 40 | value;
        value = (buffer.get() & 0XFFL) << 48 | value;
        value = (buffer.get() & 0XFFL) << 56 | value;
        return value;
    }
    public  void putLong( long value){
        ensureSize(8);
        buffer.put((byte) value);
        buffer.put( (byte) (value>>8));
        buffer.put( (byte) (value>>16));
        buffer.put( (byte) (value>>24));
        buffer.put( (byte) (value>>32));
        buffer.put( (byte) (value>>40));
        buffer.put( (byte) (value>>48));
        buffer.put( (byte) (value>>56));
    }

    public  void putInt(int index, int value){
//        ensureSize(4);
        buffer.put(index++, (byte) value);
        buffer.put(index++, (byte) (value>>8));
        buffer.put(index++, (byte) (value>>16));
        buffer.put(index,   (byte) (value>>24));
    }


    public  void putBytes( byte[] value){
        ensureSize(value.length);
        buffer.put(value);
    }


    public  byte[] getBytes( int length){
        byte [] dst = new byte[length];
        buffer.get(dst);
        return dst;
    }

    public  void putByte( byte value){
        ensureSize(1);
        buffer.put(value);
    }


    public  byte getByte( ){
        return  buffer.get();
    }

    public SprotoByteBuffer flip(){
        this.buffer.flip();
        return this;
    }


    public byte[] array(){
        return this.buffer.array();
    }



    public  SprotoByteBuffer putDouble(double d){
         this.buffer.putDouble(d);
         return this;
    }


    public  Double getDouble(){
        return this.buffer.getDouble();
    }

    public final int limit() {
        return buffer.limit();
    }

    public final int capacity() {
        return buffer.capacity();
    }

    public final int position() {
        return buffer.position();
    }

    public SprotoByteBuffer  position(int newPosition) {
        this.buffer.position(newPosition);
        return this;
    }

    public  SprotoByteBuffer putSprotoByteBuffer(SprotoByteBuffer sprotoByteBuffer){
        sprotoByteBuffer.flip();
        ensureSize(sprotoByteBuffer.limit());
        this.buffer.put(sprotoByteBuffer.buffer);
        return this;
    }

    public byte[] get(){
        byte[] bytes = new byte[this.buffer.position()];
        this.buffer.flip();
        this.buffer.get(bytes);
       return  bytes;
    }

}
