package com.ebsite.tempsite.ebsecurity.core.valcode.google;

import com.ebsite.tempsite.ebsecurity.core.valcode.EmValidateCodeType;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeException;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * GOOGLE验证器的验证业务,google验证器实现验证码的生成，只要实现验证的验证业务
 *
 * @author 蔡齐盛
 * @create 2018-06-11 13:35
 **/
@Component("googleValidateCodeProcessor") //google 为 EmValidateCodeType 中的小写，因为验证请求是通过EmValidateCodeType来查找ValidateCodeProcessor对象的
public class GoogleValidateCodeProcessor  implements ValidateCodeProcessor {


    @Autowired
    private GoogleKeyGenerator googleKeyGenerator;

    @Override
    public void create(ServletWebRequest request) throws Exception {

    }
    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
//    protected EmValidateCodeType getValidateCodeType(ServletWebRequest request) {
//        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
//        return EmValidateCodeType.valueOf(type.toUpperCase());
//    }
    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request)  {
        EmValidateCodeType codeType = EmValidateCodeType.GOOGLE;//getValidateCodeType(request);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码的值不能为空");
        }
        if (!StringUtils.isNumeric(codeInRequest)) {
            throw new ValidateCodeException("请输入正确的数字");
        }
        boolean result = googleKeyGenerator.checkCode(codeInRequest,request);// UserGoogleAuthenticator.checkCode(Long.parseLong(codeInRequest),googleKeyGenerator.getGoogleKey(request));
        if(!result){
            throw new ValidateCodeException(codeType + "验证码不正确！");
        }

    }

    @Override
    public String allowMethod() {
        return "POST"; //没有调用到生成业务,可以不设置
    }
}
