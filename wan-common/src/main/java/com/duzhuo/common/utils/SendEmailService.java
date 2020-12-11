//package com.duzhuo.common.utils;
//
//import com.duzhuo.common.thread.ThreadPoolService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Properties;
//
///**
// * 邮件发送
// */
//@Service
//@Slf4j
//public class SendEmailService {
//
//    @Value("${wan.email.from}")
//    private String from;
//    @Value("${wan.email.username}")
//    private String username;
//    @Value("${wan.email.password}")
//    private String password ;
//    @Value("${wan.email.host}")
//    private String host;
//    @Resource
//    private ThreadPoolService threadPoolService;
//
//
//    public void sendEmail(String infomation, String email, String title){
//        threadPoolService.execute(() -> {
//            try{
//                Properties prop = new Properties();
//                prop.setProperty("mail.smtp.host", host);
//                prop.setProperty("mail.transport.protocol", "smtp");
//                prop.setProperty("mail.smtp.auth", "true");
//                Session session = Session.getInstance(prop);
//                session.setDebug(true);
//                Transport ts = session.getTransport();
//                ts.connect(host, username, password);
//                Message message = createEmail(session,infomation,email,title);
//                ts.sendMessage(message, message.getAllRecipients());
//                ts.close();
//            }catch (Exception e) {
//                log.error("邮件发送失败",e);
//            }
//        });
//
//    }
//
//    /**
//     * @Method: createEmail
//     * @Description: 创建要发送的邮件
//     * @Anthor:孤傲苍狼
//     *
//     * @param session
//     * @return
//     * @throws Exception
//     */
//    public Message createEmail(Session session,String infomation,String email,String title) throws Exception{
//        MimeMessage message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(from));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email)});
//        message.setSubject(title);
//        message.setContent(infomation, "text/html;charset=UTF-8");
//        message.saveChanges();
//        return message;
//    }
//}