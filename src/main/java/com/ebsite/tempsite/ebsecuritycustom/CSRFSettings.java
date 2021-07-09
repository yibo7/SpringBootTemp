package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.httpconfig.IHttpSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * csrf跨域忽略地址配置
 * @author 蔡齐盛
 * @create 2017-12-08 21:32
 **/
@Component
@Order(Integer.MIN_VALUE)
public class CSRFSettings implements IHttpSecurityConfig {
    @Autowired
    private SecurityConfigs securityConfigs;
    @Override
    public void config(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/api/**","/api/publics/loginedvalcode","/main/msg/notice/imageUpload",
                "/main/admincode/**");
        //页面可以被iframe
        http.headers().frameOptions().disable();
        //http.csrf().disable();
    }
}
