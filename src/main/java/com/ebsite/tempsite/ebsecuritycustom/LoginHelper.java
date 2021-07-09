package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.valcode.google.GoogleKeyGenerator;
import com.ebsite.tempsite.queuehandler.QueueToDbOpt;
import com.ebsite.tempsite.settings.SiteSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于security的手动登录
 *
 * @author 蔡齐盛
 * @create 2017-12-08 15:40
 **/
@Component
@Slf4j
public class LoginHelper {
    @Autowired
    protected GoogleKeyGenerator googleKeyGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired(required = false)
    private ApplicationEventPublisher eventPublisher;
//    @Autowired
//    private RollCallService rollCallService;
    @Autowired
    protected QueueToDbOpt logOpt;
    @Autowired
    private SiteSetting siteSetting;

    public  boolean autoLogin(String sUserName, String sPass, HttpServletRequest request, HttpServletResponse response){

        boolean isOk = false;
        // 创建一个用户名密码登陆信息
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(sUserName, sPass);


            token.setDetails(new WebAuthenticationDetails(request));
            // 用户名密码登陆效验
            Authentication authResult = authenticationManager.authenticate(token);
        EbUserDetails user = (EbUserDetails)authResult.getPrincipal();
//        String googleKey =user.getGoogleKey();
//
//        googleKeyGenerator.setGoogleKey(new ServletWebRequest(request, response),googleKey);
            // 在 session 中保存 authResult
            //sessionStrategy.onAuthentication(authResult, request, response);

            // 在当前访问线程中设置 authResult
            SecurityContextHolder.getContext().setAuthentication(authResult);

            // 如果记住我在配置文件中有配置
//            if (rememberMeServices != null) {
//                rememberMeServices.loginSuccess(request, response, authResult);
//            }
        if(siteSetting.isIsopenIpLimt()){
//            String ipAddress = RequestPrams.getIpAddress(request);
//            RollCall rollCall = rollCallService.findRollCallByRollcallip(ipAddress);
//            if (rollCall != null) {
//                if (rollCall.getRollcallstatus() == 1) {
//                    String sContent = "后台管理异常登录黑名单拦截：IP:" + ipAddress;
//                    sendMessage(sContent);
//                    isOk = false;
//                } else if (rollCall.getRollcallstatus() == 2) {
//                    isOk = true;
//                } else {
//                    isOk = false;
//                }
//            } else {
//                String sContent = "后台管理非黑名单登录：IP:" + ipAddress;
//                sendMessage(sContent);
//                isOk = true;
//            }

        }
        else{
            isOk = true;
        }


        // 发布登陆成功事件
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        return isOk;
    }

    public void sendMessage(String sContent) {
        String mobileNumber = siteSetting.getSendWarningPhone();
        //PmessageDto pmessageDto = new PmessageDto(2, mobileNumber, sContent);
        //LogOptModel logOptModel = new LogOptModel(pmessageDto, LogOptEnum.FVALIDATEMESSAGE);
        //logOpt.addLog(logOptModel);
        log.info(sContent);
    }
}
