package org.sproto;

/**
 * In packed format, the message is padding to 8. Each 8 byte is reduced to a tag byte followed by zero to eight content bytes. The bits of the tag byte correspond to the bytes of the unpacked word, with the least-significant bit corresponding to the first byte. Each zero bit indicates that the corresponding byte is zero. The non-zero bytes are packed following the tag.
 *
 * For example:
 *
 * unpacked (hex):  08 00 00 00 03 00 02 00   19 00 00 00 aa 01 00 00
 * packed (hex):  51 08 03 02   31 19 aa 01
 *
 * Tag 0xff is treated specially. A number N is following the 0xff tag. N means (N+1)*8 bytes should be copied directly. The bytes may or may not contain zeros. Because of this rule, the worst-case space overhead of packing is 2 bytes per 2 KiB of input.
 *
 * For example:
 *
 * unpacked (hex):  8a (x 30 bytes)
 * packed (hex):  ff 03 8a (x 30 bytes) 00 00
 */
public class SprotoPack {


    public static byte[] pack(byte[] origin){
        int length = origin.length;
        if(length <= 0){
            return new byte[0];
        }
        byte[] packed = new byte[length +2];

        int i = 0;
        int firstFFHeaderIndex = -1;
        int ffCount  = 0;
        int packIndex = 0;
        while(i < length){
            int headerIndex = packIndex++;
            byte header = 0;
            int t =0;
            for( ;t < 8 && i < length; t++){
                byte v = origin[i++];
                if( v != 0){
                  header = (byte) (header |  ((1 << t) & 0XFF));
                  packed[packIndex++] = v;
                }
            }
            if((header & 0XFF) == 0XFF){
                if (firstFFHeaderIndex == -1){
                    firstFFHeaderIndex = headerIndex;
                    // right >> 1 position
                    int m = 0;
                    while (m < 8){
                        packed[packIndex - m] = packed[packIndex -m -1];
                        m++;
                    }
                    packed[headerIndex] = header;
                    packIndex++;
                }else {
                    // left << 1 position
                    int m = 8;
                    while (m > 0){
                        packed[packIndex - m -1] = packed[packIndex -m ];
                        m --;
                    }
                    packIndex--;
                }
                packed[firstFFHeaderIndex+1] = (byte) ffCount;
                ffCount++;
            }else {
                packed[headerIndex] = header;
                while (t < 8){ //补零
                    packed[packIndex++] = 0;
                    t++;
                }

                firstFFHeaderIndex = -1;
                ffCount  = 0;
            }
        }
        byte[] result = new byte[packIndex];
        for(int idx =0; idx < packIndex; idx ++){
             result[idx] = packed[idx];
        }
//        (packed,0,packIndex, result,0,packIndex);
        return result;
    }


    public static byte[] unpack(byte[] origin){

        return null;
    }
}
