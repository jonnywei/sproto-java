package org.sproto;

public class PrintUtil {

    public static void print(byte[] bytes){
        int n =1;
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
        System.out.println();
    }
}
