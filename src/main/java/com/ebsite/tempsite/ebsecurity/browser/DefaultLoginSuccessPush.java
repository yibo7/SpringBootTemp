package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 让外部监听登录成功后的事件接口
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:14
 **/
@Slf4j
//@Component  当外面重写了ILoginSuccessPush后，要将@Component注掉
public class DefaultLoginSuccessPush  implements  ILoginSuccessPush{

    @Override
    public void OnLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, SimpleResponse rz) {
        log.info("登录成功，来自默认登录成功监听器，可以在外部重写ILoginSuccessPush，覆盖此方法");
    }
}
