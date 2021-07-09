package com.ebsite.tempsite.Interceptors.interceptor;

import com.alibaba.fastjson.JSON;
import com.ebsite.tempsite.Interceptors.annotation.OpenApiRequired;
import com.ebsite.tempsite.apihelper.ApiResultUtils;
import com.ebsite.tempsite.exception.EResultEnum;
import com.ebsite.tempsite.settings.SiteSetting;
import com.ebsite.tempsite.utils.ApiUitl;
import com.ebsite.tempsite.utils.EbUtils;
import com.ebsite.tempsite.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cqs
 * @version 1.0
 * @date 2021/6/8 19:20
 * @remark
 */
@Component
public class OpenApiCheckInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(OpenApiCheckInterceptor.class);

    @Autowired
    protected SiteSetting siteSetting;
    /**
     * 验证请求端的IP合法性，和验证参数签名合法性
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("openapi拦截器开启==>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
//        String loginToken = request.getHeader(AppConstants.tokenName);

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        OpenApiRequired openApiRequired = handlerMethod.getMethodAnnotation(OpenApiRequired.class);
        if (openApiRequired != null) {

                String publicKey = request.getParameter("pk");
                //如果是多用户的情况，可以通过publicKey从数据库获取对应的用户配置
                if (publicKey == siteSetting.getPublicKey()){
                    //1.检查ip的合法性
                    if(openApiRequired.isCheckIp()){
                        String iP =  WebUtil.getNoneIpAddress(request);
                        String userIps = siteSetting.getAllowIps();
                        //验证ip合法性
                        if (!EbUtils.whetherExist(userIps, ",", iP)) { //ip不通过
                            print(response, EResultEnum.NOACCESS.getCode(), "Bad IP",503);
                            return false;
                        }
                    }
                    //2.验证参数合法性
                    String errMsg = checkPrams(request,siteSetting.getPrivateKey());
                    if(!StringUtils.isEmpty(errMsg)){
                        print(response, EResultEnum.NOACCESS.getCode(), errMsg,503);
                        return false;
                    }

                }
                else{
                    print(response, EResultEnum.NOACCESS.getCode(), "not user",503);
                    return false;
                }

        }
        return true;
    }
    String checkPrams(HttpServletRequest request,String secret_key){
        //region 遍历参数获取所有的参数key和value
        Map<String, String> params = new HashMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String parameter = request.getParameter(key);
            //xss过滤器拦截到的+号,切换为正常数据
            parameter = parameter.replace("[+]","+");

            params.put(key, parameter);
        }
        //endregion 遍历参数获取所有的参数key和value

        //region 参数校验
        //没有提交这两个参数
        if (!params.containsKey("sign") || !params.containsKey("time")) {
            return "not sign or time";
        }
        if (("".equals(params.containsKey("sign"))) || ("".equals(params.containsKey("time")))) {
            return "sign or time is null";
        }
        if (!EbUtils.isNumeric(params.get("time"))) {
            return "time not a number";
        }
        Long sysTime = System.currentTimeMillis();
        if ((sysTime - Long.parseLong(params.get("time"))) > (30 * 1000)) { //超过30秒就过时了，主要是为了防止地址重放攻击
            return "sign time out";
        }

        //endregion 参数校验


        // 获取签名,因为后面要根据参数重新计算签名,所以将用户传送过来的签名从参数列表删除
        String sign = params.remove("sign");

        String mysignV1 = ApiUitl.buildMysignV1(params, secret_key);
        if (!mysignV1.equals(sign)) {
            return "sign bad";
        }
        return "";
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
//        计算接口访问时间
    }



    private void print(HttpServletResponse response, Integer code,String msg,Integer states)  throws IOException {
        response.setStatus(states);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(ApiResultUtils.error(code, msg)));

    }
}
