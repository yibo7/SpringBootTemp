package com.ebsite.tempsite.ebsecurity.browser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 让外部监听登录成功后的事件接口
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:14
 **/
@Slf4j
//@Component 在外面重写后将这个@Component注掉
public class DefaultLoginFailurePush implements  ILoginFailurePush{


    @Override
    public void OnLoginFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.info("登录失败，来自默认登录成功监听器，可以在外部重写ILoginFailurePush，覆盖此方法");
    }
}
