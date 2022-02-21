package com.example.ibmmq.ibmmq_test.controller;


import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequestMapping("orders")
@RestController
public class MqController {
    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping
    public ResponseEntity<RequestObject> sendMessageToQueue(@RequestBody RequestObject requestObject) throws JMSException {
        MQQueue orderRequestQueue = new MQQueue(requestObject.getMqName());

        jmsTemplate.convertAndSend(orderRequestQueue, requestObject.getMessage(), textMessage -> {
            textMessage.setJMSCorrelationID(requestObject.getId());
            return textMessage;
        });
        return new ResponseEntity(requestObject, HttpStatus.ACCEPTED);
    }

    @Deprecated // this was just to show how to find a message by correlation Id
    @GetMapping
    public ResponseEntity<RequestObject> findOrderByCorrelationId(@RequestParam String correlationId) throws JMSException {
        log.info("Looking for message '{}'", correlationId);
        String convertedId = bytesToHex(correlationId.getBytes());
        final String selectorExpression = String.format("JMSCorrelationID='ID:%s'", convertedId);
        final TextMessage responseMessage = (TextMessage) jmsTemplate.receiveSelected("ORDER.REQUEST", selectorExpression);
        RequestObject response = RequestObject.builder()
                .message(responseMessage.getText())
                .id(correlationId)
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // You could use Apache Commons Codec library instead
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
    private static final String[] HEX_AR = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static String bytes2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0 ; i<bytes.length ; i++){
            int b = bytes[i] & 0xFF;
            String high = HEX_AR[b/16];
            String low = String.valueOf(b % 16);
            stringBuffer.append(high).append(low);
        }
        return stringBuffer.toString();
    }
}
