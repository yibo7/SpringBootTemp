package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import com.ebsite.tempsite.settings.SiteSetting;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("ebAuthenticationSuccessHandler")
public class EbAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SiteSetting siteSetting;
    @Autowired
    private ObjectMapper objectMapper;
    public EbAuthenticationSuccessHandler(){
        this.requestCache = new HttpSessionRequestCache();
    }

    @Autowired
    private ILoginSuccessPush loginSuccessPush;

    /*
     * 在这里处理登录成功后的业务，目前主要在这里区分登录成功后的请求来源是不是要求返回json
     * 否则就直接交给原处理程序定向处理
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    private RequestCache requestCache;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //logger.info("登录成功");
        String sJson = request.getQueryString();
        if (!StringUtils.isEmpty(sJson)&&sJson.indexOf("isjson")>-1) {
            response.setContentType("application/json;charset=UTF-8");
            SavedRequest savedRequest = this.requestCache.getRequest(request, response);

            String targetUrl = "";
            if(savedRequest!=null){
                targetUrl = savedRequest.getRedirectUrl();
            }
            if(StringUtils.isEmpty(targetUrl))
                targetUrl = siteSetting.getUrlUserInex();
            SimpleResponse rz = new SimpleResponse(targetUrl,200);

            loginSuccessPush.OnLoginSuccess(request,response,authentication,rz);
            response.getWriter().write(objectMapper.writeValueAsString(rz));
        }
        else
        {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
