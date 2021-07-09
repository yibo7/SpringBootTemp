package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.browser.ILoginSuccessPush;
import com.ebsite.tempsite.ebsecurity.support.SimpleResponse;
import com.ebsite.tempsite.queuehandler.QueueToDbEnum;
import com.ebsite.tempsite.queuehandler.QueueToDbModel;
import com.ebsite.tempsite.queuehandler.QueueToDbOpt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功后触发此类的方法
 *
 * @author 蔡齐盛
 * @create 2017-12-08 17:27
 **/
@Component
@Slf4j
public class EbChangeLoginSuccessPush implements ILoginSuccessPush {

    @Autowired
    private QueueToDbOpt logOpt;
    @Override
    public void OnLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, SimpleResponse rz) {

        EbUserDetails md = (EbUserDetails) authentication.getPrincipal();

//        LoginLog loginLog = new LoginLog();
//        loginLog.setUserName(md.getUsername());
//        loginLog.setUserId(md.getUserId());
//        String loginIp = RequestPrams.getIpAddress(request);
//        loginLog.setIp(loginIp);
//        loginLog.setLoginTime(new Date());
//        loginLog.setLoginTimel(System.currentTimeMillis());

        log.info("管理员登录成功，需要添加登录日志处理");
    }
}
