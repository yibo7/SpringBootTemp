package com.ebsite.tempsite.ebsecurity.core.httpconfig;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * @author 蔡齐盛，此类没有实质作用
 * @create 2017-12-08 21:32
 **/
@Component
@Order(Integer.MIN_VALUE)
public class DefaultHttpSecurityConfig  implements  IHttpSecurityConfig{
    @Override
    public void config(HttpSecurity http) {

    }
}
