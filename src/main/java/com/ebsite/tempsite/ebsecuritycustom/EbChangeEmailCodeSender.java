package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.valcode.email.EmailCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 默认的短信验证码发送器,实现了SmsCodeSender后将覆盖系统的默认的EbSmsCodeSender
 *
 */
@Slf4j
@Component
public class EbChangeEmailCodeSender implements EmailCodeSender {

//    @Autowired
//    private EmailSender emailSender;
    @Override
    public void send(String emailaddr, String code)  {
        try {
            //emailSender.sendSimpleMail("来自COINBIG的验证码", "你本次的验证码为:"+code, emailaddr,false);
            log.info("成功向邮箱"+emailaddr+"发送邮件验证码"+code);
        } catch (Exception e) {
            log.info("发送验证码邮箱失败，地址:"+emailaddr+",原因:"+e.getMessage());
        }

    }

}