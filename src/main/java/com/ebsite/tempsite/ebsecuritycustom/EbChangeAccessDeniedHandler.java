package com.ebsite.tempsite.ebsecuritycustom;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import payvar.openapi.apihelper.ApiResult;
import payvar.openapi.apihelper.ApiResultUtils;
import payvar.openapi.ebsecurity.core.configs.SecurityConfigs;
import payvar.openapi.ebsecurity.core.urlmanager.UrlMatcher;
import payvar.openapi.enums.appapi.AppErrorEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义的权限访问控制输出结果
 *
 * @author 蔡齐盛
 * @create 2017-12-08 21:00
 **/
@Slf4j
@Component
public class EbChangeAccessDeniedHandler implements AccessDeniedHandler {

    private SecurityConfigs securityConfigs;
//    private RequestCache requestCache;
//    private RedirectStrategy redirectStrategy;
    public EbChangeAccessDeniedHandler(SecurityConfigs _securityConfigs){
        this.securityConfigs = _securityConfigs;//new SecurityConfigs();
//        this.requestCache = new HttpSessionRequestCache();
//        this.redirectStrategy= new DefaultRedirectStrategy();
    }

    /**
            * 替换默认的403页面， 没有权限的处理
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


//        if (SecurityContextHolder.getContext().getAuthentication()!=null&&!SecurityContextHolder.getContext().getAuthentication().getName().
//                equals("anonymousUser")) {
//
//            //api下的UserValidate 二次验证时添加了此角色
//            if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(EbUserDetailsUtils.secondRoleName)){ //还没有进行二次认证
//                log.info("还没有进行二次认证");
//                new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());
//                String s = securityConfigs.getBrowser().getLoginPage() + "?val=1";
//                response.sendRedirect(s);
//                return;
//            }
//
//        }

        String targetUrl = request.getRequestURI();
        // /api/下的所有请求都返回json
        if (UrlMatcher.getInstance().match("/api/**", targetUrl))//
        {
            ApiResult rz = ApiResultUtils.error(AppErrorEnum.E103);
            ObjectMapper objectMapper = new ObjectMapper();
            String sRZ = objectMapper.writeValueAsString(rz);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(sRZ);
            return;
        }
        log.warn("访问了没有受权地址:"+targetUrl);
        response.sendRedirect(securityConfigs.getBrowser().getLoginPage()+"?val=1");
    }
}
