package com.ebsite.tempsite.ebsecuritycustom;

import org.springframework.security.core.AuthenticationException;

/**
 * 登录发生的异常
 *
 * @author 蔡齐盛
 * @create 2018-06-07 15:30
 **/
public class LoginException extends AuthenticationException {
    private static final long serialVersionUID = -2688493763705031588L;

    public LoginException(String msg) {
        super(msg);
    }
}
