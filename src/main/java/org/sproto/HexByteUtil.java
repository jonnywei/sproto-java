package org.sproto;

public class HexByteUtil {

    public static void printHex(byte[] bytes){
        int n =1;
        System.out.println("-----------");
        for(byte b: bytes){
            if  (0<= b && b <=16){
                System.out.printf("0");
            }
            System.out.printf("%X ",b);
            if (n %4 ==0){
                System.out.println();
            }
            n++;
        }
        System.out.println("-----------");
        System.out.println("");
    }


    public static byte[] hexStringToByteArray(String s) {
        s = s.replaceAll("\\s","");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
