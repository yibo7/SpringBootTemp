package com.ebsite.tempsite.ebsecurity.core.valcode.email;


import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信验证码发送器
 */
@Slf4j
public class EbEmailCodeSender implements EmailCodeSender {


    @Override
    public void send(String emailadrr, String code)
    {
        log.warn("请配置真实的邮箱验证码发送器(EmailCodeSender)");
        log.info("向邮箱"+emailadrr+"发送短信验证码"+code);
    }

}