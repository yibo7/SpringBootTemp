package com.ebsite.tempsite.ebsecurity.core.valcode.configs;

import lombok.Data;

/**
 * 验证码配置
 * @author zhailiang
 *
 */
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();
    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();
    /**
     * Email验证码的配置
     */
    private EmailCodeProperties email = new EmailCodeProperties();
    /**
     * 随机验证码
     */
    private JsRandomCodeProperties jsrandom = new JsRandomCodeProperties();
    /**
     * google 验证码
     */
    private GoogleCodeProperties google = new GoogleCodeProperties();

}