package com.ebsite.tempsite.ebsecurity.core.valcode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 *
 *
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, EmValidateCodeType validateCodeType, String mobileEmail);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, EmValidateCodeType validateCodeType, String mobileEmail);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, EmValidateCodeType codeType, String mobileEmail);
//    void setCurrentEmailMobile(ServletWebRequest request,String currentAc);
    String getCurrentEmailMobile(ServletWebRequest request);
}