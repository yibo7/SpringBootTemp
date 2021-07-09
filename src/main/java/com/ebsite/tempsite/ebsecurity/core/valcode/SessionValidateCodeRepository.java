package com.ebsite.tempsite.ebsecurity.core.valcode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

/**
 * 基于session的验证码存取器
 *
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    private String keyCurrentEmailMobile = "CurrentEmailMobile";
    /**
     * 操作session的工具类
     */
    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Autowired
    HttpSession session;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, EmValidateCodeType validateCodeType,String mobileEmail) {
        //sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType,mobileEmail), code);
        session.setAttribute(getSessionKey(request, validateCodeType,mobileEmail), code);
        if(StringUtils.isNotEmpty(mobileEmail)){
            //sessionStrategy.setAttribute(request, keyCurrentEmailMobile, mobileEmail);
            session.setAttribute(keyCurrentEmailMobile, mobileEmail);
        }
    }

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, EmValidateCodeType validateCodeType,String mobileEmail) {
        return SESSION_KEY_PREFIX+mobileEmail + validateCodeType.toString().toUpperCase();
    }


    @Override
    public ValidateCode get(ServletWebRequest request, EmValidateCodeType validateCodeType,String mobileEmail) {
        return  (ValidateCode) session.getAttribute(getSessionKey(request, validateCodeType,mobileEmail));
        //return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType,mobileEmail));
    }


    @Override
    public void remove(ServletWebRequest request, EmValidateCodeType codeType,String mobileEmail) {

        session.removeAttribute(getSessionKey(request, codeType,mobileEmail));
        session.removeAttribute(keyCurrentEmailMobile);
//        sessionStrategy.removeAttribute(request, getSessionKey(request, codeType,mobileEmail));
//        sessionStrategy.removeAttribute(request, keyCurrentEmailMobile);
    }

//    @Override
//    public void setCurrentEmailMobile(ServletWebRequest request,String currentAc){
//        sessionStrategy.setAttribute(request, keyCurrentEmailMobile, currentAc);
//    }
    @Override
    public String getCurrentEmailMobile(ServletWebRequest request){
//         Object emailmobile = sessionStrategy.getAttribute(request, keyCurrentEmailMobile);
        Object emailmobile = session.getAttribute(keyCurrentEmailMobile);
         if(emailmobile!=null){
             return emailmobile.toString();
         }
         return "";
    }

}