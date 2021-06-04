package com.parvar.fullnode.utils.sendmsg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class EmailSender implements ISender {

    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    public String sendMsg(String mobileNumber, String msg, String userName) {
        return null;
    }
    @Async
    public void sendSimpleMail(String sTitle,String Body,String senTo,boolean isHtml) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(Sender);
            message.setTo(senTo); //收件地址
            message.setSubject(sTitle);
            message.setText(Body,isHtml);
            //mailSender.send(message);
            mailSender.send(mimeMessage);

    }
}