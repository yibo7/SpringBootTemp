package com.ebsite.tempsite.ebsecurity.core.valcode.jsrandom;

import com.ebsite.tempsite.ebsecurity.core.valcode.AbstractValidateCodeProcessor;
import com.ebsite.tempsite.ebsecurity.core.valcode.EmValidateCodeType;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCode;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeException;
import com.ebsite.tempsite.utils.encrypt.MD5Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletResponse;

import static java.lang.Character.getNumericValue;

/**
 * 图片验证码处理器
 *
 *
 */
@Component("jsrandomValidateCodeProcessor") //jsrandom 为 EmValidateCodeType 中的小写，因为验证请求是通过EmValidateCodeType来查找ValidateCodeProcessor对象的
public class JsRandomCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Override
     protected String getMobileEmail(ServletWebRequest request){
        return "";
    }
    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode vCode) throws Exception {
        HttpServletResponse response = request.getResponse();
        //response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(vCode.getCode());
    }
//    @Override
//    public String allowMethod() {
//        return "get";
//    }
    @Override
    public void validate(ServletWebRequest request) {

        EmValidateCodeType codeType = getValidateCodeType(request);

        ValidateCode codeInSession =  validateCodeRepository.get(request, codeType,getMobileEmail(request));

        /**
         * 获取随机验证码
         */
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

        if (codeInSession == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, codeType,getMobileEmail(request));
            throw new ValidateCodeException(codeType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不匹配");
        }

        String sMdWuOut;
        try {
            sMdWuOut = ServletRequestUtils.getStringParameter(request.getRequest(),"md");
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("随机数验证出错！");
        }
       StringBuffer stringBuffer = new StringBuffer();
        for (int x = 0; x < codeInRequest.length(); x++) {
             char ch = codeInRequest.charAt(x);
             int num = getNumericValue((int)ch);
             if(num%2!=0){ //获取奇数
                 stringBuffer.append(ch);
             }
        }
        stringBuffer.append("@coinbig");
        String sMdWuIn = MD5Encrypt.MD5Encode(stringBuffer.toString());
        if(!StringUtils.equals(sMdWuOut,sMdWuIn)){
            throw new ValidateCodeException(codeType + "最终验证码不匹配");
        }
        validateCodeRepository.remove(request, codeType,getMobileEmail(request));

    }

}