package com.ebsite.tempsite.ebsecurity.core.valcode;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 生成校验码器接口
 */
public interface ValidateCodeGenerator {

    /**
     * 生成校验码
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);

}