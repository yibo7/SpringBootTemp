package com.ebsite.tempsite.ebsecurity.core.valcode.configs;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeGenerator;
import com.ebsite.tempsite.ebsecurity.core.valcode.email.EbEmailCodeSender;
import com.ebsite.tempsite.ebsecurity.core.valcode.email.EmailCodeSender;
import com.ebsite.tempsite.ebsecurity.core.valcode.image.ImageCodeGenerator;
import com.ebsite.tempsite.ebsecurity.core.valcode.sms.EbSmsCodeSender;
import com.ebsite.tempsite.ebsecurity.core.valcode.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityConfigs securityConfigs;

    /**
     * 图片验证码图片生成器,只要在项目中找得到ValidateCodeGenerator的实现，就使用此默认类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityConfigs);
        return codeGenerator;
    }

    /**
     * 短信验证码发送器,只要在项目中找到SmsCodeSender的实现，将不使用此默认配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new EbSmsCodeSender();
    }

    /**
     * 邮箱验证码发送器,只要在项目中找到EmailCodeSender的实现，将不使用此默认配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(EmailCodeSender.class)
    public EmailCodeSender emailCodeSender() {
        return new EbEmailCodeSender();
    }

}
