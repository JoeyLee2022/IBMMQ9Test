package com.example.ibmmq.ibmmq_test;

import com.example.ibmmq.ibmmq_test.controller.MqController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.codec.binary.Hex;
@SpringBootTest
public class MqControllerTest {


    @Test
    public void byte2Hex(){
        String str = "12345";
        byte[] str2Bytes = str.getBytes();
        System.out.println(str2Bytes + "--- Bytes ---");
        String str2Hex1 = MqController.bytesToHex(str2Bytes);
        System.out.println(str2Hex1 + "--- Hex ---");

        String str2Hex2 = Hex.encodeHexString(str2Bytes);
        assert str2Hex2.equals(str2Hex1);

        String str2Hex3 = MqController.bytes2Hex(str2Bytes);
        assert str2Hex2.equals(str2Hex3);
    }

    @Test
    public void shiftOperatorTest(){
        int a=49;
        System.out.println("Integer_MAX:" + Integer.MAX_VALUE);
        System.out.println("Integer_MIN:" + Integer.MIN_VALUE);
        System.out.println("Byte_MAX:" + Byte.MAX_VALUE);
        System.out.println("a<<? :"+ (a>>>4));
        System.out.println("(49 to Hex) = (" + (49 >>> 4) + "" + (49 % 16) + ")");
    }

    @Test
    public void swapTwoIntWithoutTempVaraiabel(){
        int a = 3;
        int b = 9;
        a = b - a ;
      //6   9 - 3
        b = b - a;
      //3   9   6
        a = a + b;
      //9   6   3
    }
}
