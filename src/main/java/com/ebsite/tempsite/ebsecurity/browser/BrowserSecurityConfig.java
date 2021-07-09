package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.core.EbPasswordEncoder;
import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;
import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.httpconfig.HttpSecurityConfigManager;
import com.ebsite.tempsite.ebsecurity.core.requesttime.validatefilter.ValidateTimeSecurityConfig;
import com.ebsite.tempsite.ebsecurity.core.urlmanager.AuthorizeConfigManager;
import com.ebsite.tempsite.ebsecurity.core.valcode.validatefilter.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private SecurityConfigs securityConfigs;

    @Autowired
    private EbAuthenticationSuccessHandler ebAuthenticationSuccessHandler;
    @Autowired
    private EbAuthenctiationFailureHandler ebAuthenctiationFailureHandler;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;
    /**
     * 让外部可以实现IHttpSecurityConfig来扩展HttpSecurity http
     */
    @Autowired
    private HttpSecurityConfigManager httpSecurityConfigManager;
    /**
     * 验证码过滤器
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    /**
     * 时间限制过滤器
     */
    @Autowired
    private ValidateTimeSecurityConfig validateTimeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String sLogOutUrl = securityConfigs.getBrowser().getSignOutUrl();
        //sLogOutUrl = "/fffddd.html";
        http.formLogin() //启用表单登录
                    .loginPage(LoginConstants.Default_Login_Require)
                    .loginProcessingUrl(LoginConstants.Default_Login_PostUrl)
                    .successHandler(ebAuthenticationSuccessHandler)
                    .failureHandler(ebAuthenctiationFailureHandler)
                .and()
                .apply(validateTimeSecurityConfig)
                .and()
                .apply(validateCodeSecurityConfig) //验证码过滤器
                .and()
                .logout()
                    .logoutUrl(sLogOutUrl)
                    .logoutSuccessUrl(securityConfigs.getBrowser().getLoginPage())
                .deleteCookies("JSESSIONID");
                //.and().csrf().disable();
        //免权限访问配置器
        authorizeConfigManager.config(http.authorizeRequests());
        //让外部可以实现IHttpSecurityConfig来扩展HttpSecurity http
        httpSecurityConfigManager.config(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new EbPasswordEncoder();
    }

    /**
     * 解决authenticationManager注入为空的问题
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
