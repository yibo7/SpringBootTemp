package com.ebsite.tempsite.ebsecurity.core.httpconfig;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 管理接口
 *
 * @author 蔡齐盛
 * @create 2017-12-08 21:21
 **/
public interface IHttpSecurityConfigManager {
    void config(HttpSecurity http) throws Exception;
}
