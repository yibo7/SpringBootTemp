package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.httpconfig.IHttpSecurityConfig;
import com.ebsite.tempsite.settings.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

/**
 * 扩展 security 的 HttpSecurity
 * @author 蔡齐盛
 * @create 2017-12-08 21:32
 **/
@Component
@Order(Integer.MIN_VALUE)
public class EbChangeHttpSecurityConfig implements IHttpSecurityConfig {
    @Autowired
    private SecurityConfigs securityConfigs;
    @Override
    public void config(HttpSecurity http) throws Exception {
        //无权限监听器
        http.exceptionHandling().accessDeniedHandler(new EbChangeAccessDeniedHandler(securityConfigs));

        http.formLogin().defaultSuccessUrl(AppConstants.adminUrlPath.concat("parent"));
    }
}
