package com.ebsite.tempsite.ebsecurity.core.httpconfig;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 让外部可扩展Security http
 *
 * @author 蔡齐盛
 * @create 2017-12-08 21:15
 **/
public interface IHttpSecurityConfig {
    void config(HttpSecurity http) throws Exception;
}
