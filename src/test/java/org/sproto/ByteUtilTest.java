package org.sproto;

import org.junit.Test;

public class ByteUtilTest {


    @Test
    public  void test(){
        String s= "03 00 (fn = 3)\n" + "00 00 (id = 0, value in data part)\n" + "1C 00 (id = 1, value = 13)\n"
                + "02 00 (id = 2, value = false)\n" + "05 00 00 00 (sizeof \"Alice\")\n" + "41 6C 69 63 65 (\"Alice\")";


        byte[] b = HexByteUtil.hexStringToByteArray(s);
    }

}
