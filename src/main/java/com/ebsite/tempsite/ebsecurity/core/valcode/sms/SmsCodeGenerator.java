package com.ebsite.tempsite.ebsecurity.core.valcode.sms;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCode;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器,这个比图片验证码生成器简单，所以直接用Compenent，不用在BeanConfig里配置
 *
 *
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityConfigs securityConfigs;

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.
     * springframework.web.context.request.ServletWebRequest)
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityConfigs.getCode().getSms().getLength());
        return new ValidateCode(code, securityConfigs.getCode().getSms().getExpireIn());
    }

    public SecurityConfigs getSecurityProperties() {
        return securityConfigs;
    }

    public void setSecurityProperties(SecurityConfigs securityProperties) {
        this.securityConfigs = securityProperties;
    }



}