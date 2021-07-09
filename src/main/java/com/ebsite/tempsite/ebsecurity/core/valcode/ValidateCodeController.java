package com.ebsite.tempsite.ebsecurity.core.valcode;

import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这是一个所有验证码处理的Url，如显示验证码图片，发送手机短信验证码，发送Email验证码
 */
@RestController
public class ValidateCodeController {

    /**
     * 系统中的校验码处理器,这个在验证过滤器里也要用到，主要由它来处理相关的验证码生成与验证
     * 这里主要用来处理验证码的生成或发送
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    //@GetMapping(LoginConstants.Default_Code_Url_Prefix + "/{type}")
    @RequestMapping(LoginConstants.Default_Code_Url_Prefix + "/{type}") // 如code/sms?mobile=15910983264
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception
    {
        /**
         * create 方法里执行了两个操作，一个是发送验证码，一个是将验证码保存到session
         */
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorHolder.findValidateCodeProcessor(type);
        if (StringUtils.equalsIgnoreCase(request.getMethod(), validateCodeProcessor.allowMethod())) {
            validateCodeProcessor.create(new ServletWebRequest(request, response));
        }
        else {
            //抛出异常会跳转到登录页面
            //throw new ValidateCodeException("请使用"+validateCodeProcessor.allowMethod());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            response.getWriter().write("{code:0,msg:'请使用当前验证所允许的方式调用'}");
        }
    }

}
