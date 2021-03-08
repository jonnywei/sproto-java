package org.sproto;

import org.junit.Test;

import static org.sproto.HexByteUtil.hexStringToByteArray;

public class SprotoPackTest {

    @Test
    public  void testPack(){
        String s = " 08 00 00 00 03 00 02 00   19 00 00 00 aa 01 00 00";
        s = s.replaceAll("\\s","");
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }



    @Test
    public  void testPackFF(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a ";
        s = s.replaceAll("\\s","");
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }

    @Test
    public  void testPackFFAddZero(){
        String s = " 08 8a  8a  8a  8a  8a  8a  8a    19 00 00 00 aa 01 00 00  08 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a "
                + " 8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a  8a   8a 8a  8a  8a  8a  8a  8a   ";
        s = s.replaceAll("\\s","");
        byte[] unpacked = hexStringToByteArray(s);
        byte[] packed =SprotoPack.pack(unpacked);
        HexByteUtil.printHex(packed);
    }
}
