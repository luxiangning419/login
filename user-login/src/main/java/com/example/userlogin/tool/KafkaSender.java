package com.example.userlogin.tool;/*
 *
 * @Author: luxiangning
 * @date:  2022/6/14 上午10:45
 * @Description TODO
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //异步发送消息方法
    public void sendAsynchronize(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    //同步发送消息方法
    public void sendSynchronize(String topic, String message) throws Exception {
        kafkaTemplate.send(topic, message).get();

    }
}
