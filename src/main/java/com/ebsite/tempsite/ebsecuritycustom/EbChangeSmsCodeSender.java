package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.valcode.sms.SmsCodeSender;
import com.ebsite.tempsite.settings.SiteSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这个短信发送器将覆盖ebsecurity的短信验证码的默认发送器
 *
 * @author 蔡齐盛
 * @create 2017-12-06 16:09
 **/
@Component
@Slf4j
public class EbChangeSmsCodeSender implements SmsCodeSender {
//    @Autowired
//    private IMobileSender mobileSender;
    @Autowired
    private SiteSetting siteSetting;

    @Override
    public void send(String mobile, String code)
    {
        //暂时关闭，上线后开启
//        if(siteSetting.isSendMobile()) {
//            String result = mobileSender.sendMsg(mobile, "您的验证码是:" + code, "uid");
//
//        }
        log.info("EbChangeSmsCodeSender发送验证码到" + mobile + "，信验证码：" + code);
    }
}
