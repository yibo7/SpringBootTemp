package com.ebsite.tempsite.ebsecuritycustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import payvar.openapi.ebsecurity.core.configs.SecurityConfigs;
import payvar.openapi.ebsecurity.core.urlmanager.AuthorizeConfigProvider;

/**
 *  自定义访问权限配置
 */
@Component
public class EbChangeAuthorizeProvider implements AuthorizeConfigProvider
{
    @Autowired
    private SecurityConfigs securityConfigs;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {


        //很奇怪，themes 为resources下static里的自定义目录，需要添加上免权限，默认的css,js,imgages这些不用手动添加免权限
//        config.antMatchers("/fonts/**","/themes/**","/ckeditor/**","/valuser","/secondval").permitAll();
        config.antMatchers(
                "/fonts/**",
                "/favicon.ico",
                "/test/**",
                "/images/**",
                "/themes/**",
                "/js/**",
                "/valuser**",
                "/secondval",
                "/viewjs/**",
                "/admin/admincode/val",
                "/admin/admincode/send",

                "/api/mrechangelist/**",
                "/api/cool/**",
                "/api/data/**",
                "/api/wallet/**",
                "/api/mcool/**",
                "/api/mhotwallet/**",
                "/api/mwithdrawlist/uploadwithdraw",
                "/api/mwithdrawlist/getwithdrawdata",
                "/api/mwithdrawlist/getwithdrawdataagain",
                "/api/mwithdrwalist/**",

                "/api/app/user/**",
                "/api/app/coinmanage/**",
                "/api/app/togeher/**",
                "/api/app/hotsave/**",
                "api/app/error/**"

                ).permitAll();
//        config.anyRequest().access("@adminsServiceImpl.hasPermission(request, authentication)");
        return true;
    }
}
