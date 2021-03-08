package org.sproto;

import org.junit.Test;

import static org.sproto.HexByteUtil.hexStringToByteArray;

public class SprotoPackTest {

    @Test
    public  void testPack(){
        String s = " 08 00 00 00 03 00 02 00   19 00 00 00 aa 01 00 00";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }



    @Test
    public  void testPackFF(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a ";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }

    @Test
    public  void testPackFFAddZero(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a   ";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }

    @Test
    public  void testPackNotAlign(){
        String s = " 08 00 00 00 03 00 02 00   19 00 00 00 aa 01 00 00 22";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }


    @Test
    public  void testUnPackNotAlign(){
        String s = " 08 00 00 00 03 00 02 00   19 00 00 00 aa 01 00 00 22";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        unpacked = SprotoPack.unpack(packed);
        HexByteUtil.printHex(packed);
        HexByteUtil.printHex(unpacked);
    }



    @Test
    public  void testUnPackFF(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a ";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
        unpacked =SprotoPack.unpack(packed);
        HexByteUtil.printHex(unpacked);
    }



    @Test
    public  void testUnPackFFAddZero(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a   ";
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
        unpacked =SprotoPack.unpack(packed);
        HexByteUtil.printHex(unpacked);
    }
}
