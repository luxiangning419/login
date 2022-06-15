package com.example.usermanage.serivce;/*
 *
 * @Author: luxiangning
 * @date:  2022/6/14 上午10:57
 * @Description TODO
 */

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.bean.Mail;
import com.example.usermanage.dao.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MailService {

    @Autowired
    private MailMapper mapper;

    @Autowired
    private JavaMailSender javaMailSender;

    public void save(Mail mail){
        mapper.insert(mail);
    }

    public void send(String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("manager");
        message.setTo(mail);
        message.setSubject("weclome");
        message.setText("welcome");
        javaMailSender.send(message);
    }

    public List<Mail> getMail(){

        return mapper.selectList(null);
    }

}
