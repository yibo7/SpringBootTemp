package com.ebsite.tempsite.ebsecurity.core.valcode.configs;

import lombok.Data;

@Data
public class JsRandomCodeProperties {
    /**
     * 验证码长度
     */
//    private int length = 15;
    /**
     * 过期时间 10秒
     */
    private int expireIn = 60*10;
    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;
}
