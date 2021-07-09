package com.ebsite.tempsite.ebsecurity.browser;

import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;
import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import com.ebsite.tempsite.ebsecurity.core.urlmanager.UrlMatcher;
import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import com.ebsite.tempsite.utils.RequestPrams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController
{

    @Autowired
    private SecurityConfigs securityConfigs;

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping(LoginConstants.Default_Login_Require) // 登录验证重定向的地址/login/require
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED) //返回401
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            // request.getRequestURI() 值为/login/require，所以要用 savedRequest.getRedirectUrl();
            String targetUrl =  savedRequest.getRedirectUrl();
            String mathUrl = RequestPrams.getDomainAndPort(request).concat("/api/*");
            //System.out.printf("引发登录路转的地址是:"+targetUrl);
            if (UrlMatcher.getInstance().match(mathUrl, targetUrl))
            {
                return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页",401);
            }
//            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
//                redirectStrategy.sendRedirect(request, response, securityConfigs.getBrowser().getLoginPage());
//            }
        }
        redirectStrategy.sendRedirect(request, response, securityConfigs.getBrowser().getLoginPage());
        return null;
    }

}
