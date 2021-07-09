package com.ebsite.tempsite.ebsecurity.core.valcode.validatefilter;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.urlmanager.UrlMatcher;
import com.ebsite.tempsite.ebsecurity.core.valcode.EmValidateCodeType;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeException;
import com.ebsite.tempsite.ebsecurity.core.valcode.ValidateCodeProcessorHolder;
import com.ebsite.tempsite.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 校验验证码的过滤器
 *InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    /**
     * 验证模块的总配置信息
     */
    @Autowired
    private SecurityConfigs securityConfigs;
    /**
     * 系统中的校验码处理器,这个在发送控制器里也要调用，主要由它来处理相关的验证码生成与验证
     * 这里主要用来处理验证码的验证
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url,目前主要体现在四种验证：
     * 一是图片验证，二是手机短信验证，三是邮件验证，四是google身份验证
     */
    private Map<String, EmValidateCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    //private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //默认表单提交登录设置需要图片验证码(已经自定义拖拽验证)
        //urlMap.put(LoginConstants.Default_Login_PostUrl, EmValidateCodeType.IMAGE);
        //从配置里加载需要图片验证码的URL
        addUrlToMap(securityConfigs.getCode().getImage().getUrl(), EmValidateCodeType.IMAGE);
        //默认手机号登录的URL
        //urlMap.put(LoginConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, EmValidateCodeType.SMS);
        //从配置里加载需要手机短信验证码的URL
        addUrlToMap(securityConfigs.getCode().getSms().getUrl(), EmValidateCodeType.SMS);

        //从配置里加载需要验证码的URL
        addUrlToMap(securityConfigs.getCode().getEmail().getUrl(), EmValidateCodeType.EMAIL);

        addUrlToMap(securityConfigs.getCode().getJsrandom().getUrl(), EmValidateCodeType.JSRANDOM);

        addUrlToMap(securityConfigs.getCode().getGoogle().getUrl(), EmValidateCodeType.GOOGLE);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, EmValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                if(!urlMap.containsKey(url)){
                    urlMap.put(url, type);
                }
                else
                { //在某些情况下，需要对一个地址采用多种验证方法
                    urlMap.put(url.concat("_ebvalcodet").concat(KeyUtil.genUniqueKey()), type);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        List<EmValidateCodeType> types = getValidateCodeType(request);
        for (EmValidateCodeType type:types) {
            if (type != null) {
                logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
                try {
                    validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                    logger.info("验证码校验通过");
                }
                catch (ValidateCodeException exception)
                {
                    logger.info("验证码校验发生错误:"+exception.getMessage());

                    authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                    return;
                }
            }
        }
        chain.doFilter(request, response);

    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private List<EmValidateCodeType> getValidateCodeType(HttpServletRequest request) {
        List<EmValidateCodeType> lstRz = new ArrayList<>();

        //只接受 POST请求
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                String matchUrl = request.getRequestURI();
                //_ebvalcodet在某些情况下，需要对一个地址采用多种验证方法,见地址添加的规则
                if (UrlMatcher.getInstance().match(url, matchUrl)||UrlMatcher.getInstance().match(matchUrl+"_ebvalcodet*", url)) {
                    lstRz.add(urlMap.get(url));
                    //break;
                }

            }
        }
        return lstRz;
    }
//    private EmValidateCodeType getValidateCodeType(HttpServletRequest request) {
//        EmValidateCodeType result = null;
//        //只接受 POST请求
//        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
//            Set<String> urls = urlMap.keySet();
//            for (String url : urls) {
//                String matchUrl = request.getRequestURI();
//                if (UrlMatcher.getInstance().match(url, matchUrl)) {
//                    result = urlMap.get(url);
//                    break;
//                }
//
//            }
//        }
//        return result;
//    }

}