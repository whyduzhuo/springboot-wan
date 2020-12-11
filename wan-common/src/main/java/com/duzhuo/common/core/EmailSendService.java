package com.duzhuo.common.core;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/11 11:20
 */
@Component
public class EmailSendService {
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;


    /**
     * 无附件 简单文本内容发送
     * @param email 接收方email
     * @param title 邮件内容主题
     * @param text 邮件内容
     */
    public void simpleMailSend(String email,String title,String text) {
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        //发送邮件
        mailSender.send(message);
        System.out.println("\033[32;1m"+"发送给 "+email+" 的邮件发送成功"+"\033[0m");
    }

    /**
     * 发送带附件的邮件
     *
     * @param to      接受人
     * @param title 主题
     * @param html    发送内容
     * @param file  附件路径
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public  void sendAttachmentMail(String to, String title, String html, File file) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(username,"江西外语外贸");
        messageHelper.setTo(to);
        messageHelper.setSubject(title);
        messageHelper.setText(html, true);
        FileSystemResource fileSystemResource=new FileSystemResource(file);
        String fileName= FilenameUtils.getName(file.getName());
        messageHelper.addAttachment(fileName,fileSystemResource);
        mailSender.send(mimeMessage);
    }

//    /**
//     * 发送html内容的 邮件
//     * @param email
//     * @param title
//     * @param text
//     */
//    public void sendSimpleMailHtml(String email,String title,String text) throws MessagingException {
//
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom(username);
//        helper.setTo("demogogo@yeah.net");
//        helper.setSubject("主题：嵌入静态资源");
//        // 注意<img/>标签，src='cid:jpg'，'cid'是contentId的缩写，'jpg'是一个标记
//        helper.setText("<html><body><img src=\"cid:jpg\"></body></html>", true);
//        // 加载文件资源，作为附件
//        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\吴超\\Pictures\\Camera Roll\\微信截图_20191016142536.png"));
//        // 调用MimeMessageHelper的addInline方法替代成文件('jpg[标记]', file[文件])
//        helper.addInline("jpg", file);
//        // 发送邮件
//        mailSender.send(mimeMessage);
//    }
}
