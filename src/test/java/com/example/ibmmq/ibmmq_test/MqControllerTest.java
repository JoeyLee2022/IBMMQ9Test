package com.example.ibmmq.ibmmq_test;

import com.example.ibmmq.ibmmq_test.controller.MqController;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.Assert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MqControllerTest {
//    @Spy
//    List list = new ArrayList();
//
//    @Before
//    public void init(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void byte2Hex() {
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
    public void shiftOperatorTest() {
        int a = 49;
        System.out.println("Integer_MAX:" + Integer.MAX_VALUE);
        System.out.println("Integer_MIN:" + Integer.MIN_VALUE);
        System.out.println("Byte_MAX:" + Byte.MAX_VALUE);
        System.out.println("a<<? :" + (a >>> 4));
        System.out.println("(49 to Hex) = (" + (49 >>> 4) + "" + (49 % 16) + ")");
    }

    @Test
    public void messageListenerMockTest() throws JMSException {
        TextMessage message = Mockito.mock(TextMessage.class);
        Mockito.when(message.getText()).thenReturn("THIS IS A TEST MESSAGE");
        String result = new MqController().receive(message);
        Assert.isTrue(result.equals("THIS IS A TEST MESSAGE"));
    }

    @Test
    public void messageListernerSpyTest() throws JMSException {
        // Spy : create a real instance of class, so really call the method of instance
        List listSpy = Mockito.spy(ArrayList.class);
        listSpy.add("1");
        assertEquals(true, listSpy.size() == 1);
        // Mock : create a bare boned instance of class, it's just a shell, not really call the method of instance
        List listMock = Mockito.mock(ArrayList.class);
        listMock.add("1");
        assertEquals(false, listMock.size() == 1);
    }

    @Test
    public void swapTwoIntWithoutTempVaraiabel() {
        int a = 3;
        int b = 9;
        a = b - a;
        //6   9 - 3
        b = b - a;
        //3   9   6
        a = a + b;
        //9   6   3
    }
}
