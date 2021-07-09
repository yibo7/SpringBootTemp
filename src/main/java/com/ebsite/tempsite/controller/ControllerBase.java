package com.ebsite.tempsite.controller;

import com.ebsite.tempsite.configs.LocaleMessageService;
import com.ebsite.tempsite.ebsecuritycustom.EbUserDetails;
import com.ebsite.tempsite.pojo.LogsPojo;
import com.ebsite.tempsite.queuehandler.QueueToDbEnum;
import com.ebsite.tempsite.queuehandler.QueueToDbModel;
import com.ebsite.tempsite.queuehandler.QueueToDbOpt;
import com.ebsite.tempsite.settings.SiteSetting;
import com.ebsite.tempsite.utils.DateUtils;
import com.ebsite.tempsite.utils.RequestPrams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
public class ControllerBase {
    @Autowired
    protected LocaleMessageService langBll;
    @Autowired
    private QueueToDbOpt logOpt;
    @Autowired
    protected SiteSetting siteSetting;

    /**
     * 通用的日志添加方法
     * @param logContent 日志内容
     * @param request
     */
    protected void  addLogs(String logContent,HttpServletRequest request){
        QueueToDbModel model = new QueueToDbModel();
        LogsPojo logs = new LogsPojo();
        logs.setAddDate(DateUtils.getTimestamp());
        logs.setAddTimeint(DateUtils.getTodayStartTime());
        logs.setDescription(logContent);
        String operateip = RequestPrams.getIpAddress(request);
        logs.setIpAddr(operateip);
        logs.setLogType(QueueToDbEnum.MainLog.getCode());
        EbUserDetails user = getCurrentUser();
        logs.setUserId(user.getUserId());
        logs.setUserName(user.getUserName());
        model.setLog(logs);
        model.setQueueToDbEnum(QueueToDbEnum.MainLog);
        logOpt.addData(model);
    }

    /**
     * 获取当前登录的用户
     * @return
     */
    protected EbUserDetails getCurrentUser(){
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        //SecurityContextHolder.getContext().getAuthentication().getDetails()
        //SecurityContextHolder.getContext().getAuthentication().getCredentials()
        //SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (!SecurityContextHolder.getContext().getAuthentication().getName().
                equals("anonymousUser")) {
            Object obj =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            EbUserDetails md = (EbUserDetails)obj;
            return md;

        }
        //SecurityContextHolder.getContext().setAuthentication();
        return null;
    }
}
