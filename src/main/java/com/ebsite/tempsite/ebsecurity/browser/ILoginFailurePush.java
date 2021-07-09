package com.ebsite.tempsite.ebsecurity.browser;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 让外部监听登录失败后的事件接口
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:14
 **/
public interface ILoginFailurePush {
    void OnLoginFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception);
}
