package com.ebsite.tempsite.ebsecurity.core.valcode.jsrandom;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCode;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 名称规则必须是 访问目录+ValidateCodeGenerator
 */
@Component("jsrandomValidateCodeGenerator")
public class JsRandomCodeGenerator implements ValidateCodeGenerator {

    /**
     * 系统配置
     */
    @Autowired
    private SecurityConfigs securityConfigs;


    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(15);
        return new ValidateCode(code, securityConfigs.getCode().getJsrandom().getExpireIn()); //10分钟
    }




}