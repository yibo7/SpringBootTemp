package com.ebsite.tempsite.ebsecurity.core.valcode.email;

import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;
import com.ebsite.tempsite.ebsecurity.core.valcode.AbstractValidateCodeProcessor;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 *
 *
 */
@Component("emailValidateCodeProcessor") //email 为 EmValidateCodeType 中的小写，因为验证请求是通过EmValidateCodeType来查找ValidateCodeProcessor对象的
public class EmailCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private EmailCodeSender emailCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
//        String paramName = LoginConstants.Default_Parameter_Name_Email;
//        String emailaddr = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        String emailaddr = getMobileEmail(request);
        emailCodeSender.send(emailaddr, validateCode.getCode());
    }
    @Override
    protected String getMobileEmail(ServletWebRequest request){

        String emailaddr = "";
        try {
            String paramName = LoginConstants.Default_Parameter_Name_Email;
              emailaddr = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        return emailaddr;
    }
}
