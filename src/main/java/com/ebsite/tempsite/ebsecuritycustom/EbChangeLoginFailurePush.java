package com.ebsite.tempsite.ebsecuritycustom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import payvar.openapi.ebsecurity.browser.ILoginFailurePush;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功后触发此类的方法
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:27
 **/
@Component
@Slf4j
public class EbChangeLoginFailurePush implements ILoginFailurePush {
    /**
     * 登录失败后会经过这里，可以在这里记录异常行为
     * @param request
     * @param response
     * @param exception
     */
    @Override
    public void OnLoginFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.info("登录发生了异常:"+exception.getMessage());
    }
}
