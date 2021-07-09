package com.ebsite.tempsite.ebsecurity.core.requesttime;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义的验证码处理异常
 */
public class ValidateTimeException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateTimeException(String msg) {
        super(msg);
    }

}