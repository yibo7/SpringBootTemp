package com.ebsite.tempsite.ebsecurity.core.requesttime.validatefilter;

import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeConfig;
import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeException;
import com.ebsite.tempsite.ebsecurity.core.urlmanager.UrlMatcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 校验验证码的过滤器
 *InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
 */
@Component("validateTimeFilter")
public class ValidateTimeFilter extends OncePerRequestFilter implements InitializingBean {

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
     * 存放所有需要校验验证码的url,目前主要体现在四种验证：
     * 一是图片验证，二是手机短信验证，三是邮件验证，四是google身份验证
     */
    private Map<String, ValidateTimeConfig> urlMap = new HashMap<>();

    /**
     * 初始化要拦截的url配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
//        ValidateTimeConfig validateTimeConfig = new ValidateTimeConfig();
//        validateTimeConfig.setTimespan(5);
//        validateTimeConfig.setId("indexlogin");
//        validateTimeConfig.setUrls("/index.html,/login.html");
        //System.out.println("配置项:"+securityConfigs.getTimes().size());
        if(securityConfigs.getTimes().size()>0){

            for (ValidateTimeConfig model:securityConfigs.getTimes()) {
                addUrlToMap(model.getUrls(), model);
            }
        }
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     */
    protected void addUrlToMap(String urlString,ValidateTimeConfig validateTimeConfig) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, validateTimeConfig);
            }
        }
    }

//    @Autowired
//    private CacheRedis cacheRedis;
    /**
     * 默认限制ip,但ip可以被代理，如果想从用户Id,或其他，可以在您的应用里实现此接口来定制自己的限制条件
     * 但始终要经过ip限制
     */
    @Autowired
    private ExtendValRequestTimeLimit extendLimit;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        ValidateTimeConfig validateTimeConfig = getValidateCodeType(request);
        if (validateTimeConfig != null) {
            //logger.info("时间校验请求" + request.getRequestURI() );
            try {
                //有定制限制实现
                if(extendLimit!=null){
                    if(!extendLimit.isHaveLimt(request,response,chain,validateTimeConfig)){
                        throw new ValidateTimeException("您的访问频率太快！");
                    }
                }
                else{
                    //默认ip限制实现
                    //String ip = WebUtils.getIpAddress(request);
//                    if(StringUtils.isEmpty(ip)){
//                        throw new ValidateTimeException("无法获取到您的IP");
//                    }
//                    String configId = validateTimeConfig.getId();
//                    String key = "valtime".concat(ip).concat(configId);
//                    String cacheClass = "validatetime";
//                    String rz = cacheRedis.getStr(key,cacheClass);
//                    if(StringUtils.isEmpty(rz)){
//                        cacheRedis.addCacheItem(key,cacheClass,"1",validateTimeConfig.getTimespan(), TimeUnit.MILLISECONDS);
//                    }
//                    else{
//                        throw new ValidateTimeException("您的访问频率太快！");
//                    }
                }


            }
            catch (ValidateTimeException exception)
            {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }

        chain.doFilter(request, response);

    }

    /**
     * 获取当前请求是否是时间限制访问的url,如果不是返回null
     * @param request
     * @return
     */
    private ValidateTimeConfig getValidateCodeType(HttpServletRequest request) {
        ValidateTimeConfig result = null;

        //只接受 POST请求
//        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
//            Set<String> urls = urlMap.keySet();
//            for (String url : urls) {
//                String matchUrl = request.getRequestURI();
//                if (UrlMatcher.getInstance().match(url, matchUrl)) {
//                    result = urlMap.get(url);
//                    break;
//                }
//            }
//        }
//        RunTimeEb runTimeEb = new RunTimeEb();
//        runTimeEb.start("限时匹配");
        Set<String> urls = urlMap.keySet();
        for (String url : urls) {
            String matchUrl = request.getRequestURI();
            if (UrlMatcher.getInstance().match(url, matchUrl)) {
                result = urlMap.get(url);
                break;
            }
        }
//        runTimeEb.end();
        return result;
    }

}