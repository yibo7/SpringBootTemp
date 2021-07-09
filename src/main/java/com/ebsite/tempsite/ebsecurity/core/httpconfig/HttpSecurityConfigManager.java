package com.ebsite.tempsite.ebsecurity.core.httpconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 管理所有实现IHttpSecurityConfig的对象
 *
 * @author 蔡齐盛
 * @create 2017-12-08 21:18
 **/
@Component
public class HttpSecurityConfigManager implements IHttpSecurityConfigManager {
    @Autowired
    private List<IHttpSecurityConfig> httpSecurityConfigs;

    @Override
    public void config(HttpSecurity http) throws Exception {
        for (IHttpSecurityConfig httpSecurityConfig : httpSecurityConfigs) {
            httpSecurityConfig.config(http);
        }
    }
}
