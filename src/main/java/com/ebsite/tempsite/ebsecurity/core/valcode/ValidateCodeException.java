package com.ebsite.tempsite.ebsecurity.core.valcode;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义的验证码处理异常
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}