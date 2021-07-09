package com.ebsite.tempsite.ebsecurity.core.valcode.google;

import com.ebsite.tempsite.ebsecurity.core.valcode.google.googleauth.UserGoogleAuthenticator;
import com.ebsite.tempsite.ebsecurity.support.AESUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

/**
 * 获取当前用户google key 的接口，由外部实现
 *
 * @author 蔡齐盛
 * @create 2018-06-11 14:50
 **/
@Component
public class GoogleKeyGenerator {
    /**
     * 验证码放入session时的前缀
     */
    private String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_GOOGLEKEY";

    /**
     * 操作session的工具类
     */
    //private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @Autowired
    private HttpSession httpSession;
    public void  setGoogleKey(ServletWebRequest request,String googleKey){
        String encodeKey  = AESUtil.encryptByDefaultKey(googleKey);
        httpSession.setAttribute(SESSION_KEY_PREFIX, encodeKey);
        //sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX, encodeKey);
    }
    public String getGoogleKey(ServletWebRequest request){
//         String googleKey =   (String) sessionStrategy.getAttribute(request, SESSION_KEY_PREFIX);
        String googleKey =   (String) httpSession.getAttribute(SESSION_KEY_PREFIX);
         if(googleKey!=null){

             return AESUtil.decryptByDefaultKey(googleKey);
         }
         return null;
    }
    public void clearGoogleKey(ServletWebRequest request) {
//        sessionStrategy.removeAttribute(request, SESSION_KEY_PREFIX);
        httpSession.removeAttribute(SESSION_KEY_PREFIX);
    }
    public boolean checkCode(String codeInRequest,ServletWebRequest request){
        if(StringUtils.isNumeric(codeInRequest)){
            Long code =  Long.parseLong(codeInRequest);
            return UserGoogleAuthenticator.checkCode(code,getGoogleKey(request));
        }
        return false;
    }

    /**
     * 在不支持session的应用下使用，如app
     * @param codeInput 用户输入的google验证码
     * @param googleKey 用户绑定的googke key
     * @return
     */
    public boolean checkCode(String codeInput,String googleKey){
        if(StringUtils.isNumeric(codeInput)){
            Long code =  Long.parseLong(codeInput);
            return UserGoogleAuthenticator.checkCode(code,googleKey);
        }
        return false;
    }
}
