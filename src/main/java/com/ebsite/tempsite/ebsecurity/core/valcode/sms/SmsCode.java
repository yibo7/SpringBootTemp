package com.ebsite.tempsite.ebsecurity.core.valcode.sms;

import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 图片验证码
 */
@Data
public class SmsCode extends ValidateCode {


    private static final long serialVersionUID = 4707063321631128497L;
    private String mobileNumber;

    public SmsCode(String mbNumber, String code, int expireIn){
        super(code, expireIn);
        this.mobileNumber = mbNumber;
    }

    public SmsCode(String mbNumber, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.mobileNumber = mbNumber;
    }


}