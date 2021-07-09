package com.ebsite.tempsite.ebsecurity.core.valcode.sms;


import lombok.extern.slf4j.Slf4j;

/**
 * 默认的短信验证码发送器
 */
@Slf4j
public class EbSmsCodeSender implements SmsCodeSender {


    @Override
    public void send(String mobile, String code)
    {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机"+mobile+"发送短信验证码"+code);
    }

}