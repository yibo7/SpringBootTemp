package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 让外部监听登录成功后的事件接口
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:14
 **/
public interface ILoginSuccessPush {
    void OnLoginSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication, SimpleResponse rz);
}
