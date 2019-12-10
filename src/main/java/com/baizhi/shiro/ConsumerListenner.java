package com.baizhi.shiro;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class ConsumerListenner {
    @JmsListener(destination = "hzx")
    public void consum(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("springboot被监听" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}