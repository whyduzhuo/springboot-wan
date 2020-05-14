package com.duzhuo.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author: wanhy
 * @date: 2020/5/7 22:56
 */
@Service
public class EmailSending {

//    @Autowired
//    private JavaMailSender javaMailSender;

//    @Value("${spring.mail.username}")
//    private String from;
//
//    public void sendMail(String email,String title,String content){
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setText(content);
//        simpleMailMessage.setSubject(title);
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setFrom(from);
//        javaMailSender.send(simpleMailMessage);
//    }
}
