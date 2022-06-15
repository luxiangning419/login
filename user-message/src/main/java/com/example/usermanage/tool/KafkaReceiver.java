package com.example.usermanage.tool;/*
 *
 * @Author: luxiangning
 * @date:  2022/6/14 上午10:49
 * @Description TODO
 */

import com.alibaba.fastjson.JSONObject;
import com.example.common.bean.Mail;
import com.example.common.bean.User;
import com.example.usermanage.serivce.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KafkaReceiver {

    @Autowired
    private MailService service;

    @KafkaListener(topics = {"${kafka.topic.regist_topic}"})
    public void listen1(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            JSONObject jsonObject = JSONObject.parseObject(message.toString());
            if (null != jsonObject) {
                User user = JSONObject.toJavaObject(jsonObject, User.class);
                try {
                    Mail email = new Mail();
                    email.setEmail(user.getEmail());
                    service.save(email);
                    service.send(user.getEmail());
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
            log.info("kafka车辆备案消息：{}", message);
            log.info("----------------- record =" + record);
        }

    }
}
