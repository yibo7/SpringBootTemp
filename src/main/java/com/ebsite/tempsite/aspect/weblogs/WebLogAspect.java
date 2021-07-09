package com.ebsite.tempsite.aspect.weblogs;

import com.ebsite.tempsite.ebsecuritycustom.EbUserDetails;
import com.ebsite.tempsite.pojo.LogsPojo;
import com.ebsite.tempsite.queuehandler.QueueToDbEnum;
import com.ebsite.tempsite.queuehandler.QueueToDbModel;
import com.ebsite.tempsite.queuehandler.QueueToDbOpt;
import com.ebsite.tempsite.utils.DateUtils;
import com.ebsite.tempsite.utils.RequestPrams;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 这是日志处理的业务层，我目前这里是将日志直接写到了mySQL，你也可以在这里将日志写到其他存储介媒哦
     */
//    @Autowired
//    private LogsServiceImpl logsService;

    /**
      * 定义一个切入点.
      * ("execution(public * com.kfit.*.web..*.*(..))")
      * 解释下：
      * 第一个 * 代表任意修饰符及任意返回值.
      * 第二个 * 任意包名
      * 第三个 * 代表任意方法.
      * 第四个 * 定义在web包或者子包
      * 第五个 * 任意方法
      * .. 匹配任意数量的参数.
      */
    @Pointcut("@annotation(controllerLog)")
    public void controllerAspect(com.ebsite.tempsite.aspect.weblogs.ControllerLog controllerLog) {

    }
    @Autowired
    private QueueToDbOpt logOpt;
    @Before("controllerAspect(controllerLog)")
    public void doBefore(JoinPoint joinPoint, com.ebsite.tempsite.aspect.weblogs.ControllerLog controllerLog) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String description = controllerLog.description();
            String logType = LogsTypeEnum.getEnumString(controllerLog.logstype());
            String operateip = RequestPrams.getIpAddress(request);
            String operateurl = request.getRequestURI();
            String strPram = request.getQueryString();
            String operatelog = String.format("%s 来自 ip:%s URL:%s 参数:%s 日志类型:%s", description, operateip, operateurl,strPram,logType);

            LogsPojo logs = new LogsPojo();
            logs.setAddDate(DateUtils.getTimestamp());
            logs.setAddTimeint(DateUtils.getTodayStartTime());
            logs.setDescription(operatelog);
            logs.setIpAddr(operateip);
            logs.setLogType(QueueToDbEnum.MainLog.getCode());
            EbUserDetails user = null;
            if(SecurityContextHolder.getContext().getAuthentication()!=null) {
                if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                    Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    user = (EbUserDetails) obj;
                }
            }
            if(user!=null){
                logs.setUserId(user.getUserId());
                logs.setUserName(user.getUserName());
            }
            logOpt.addData(new QueueToDbModel(logs,QueueToDbEnum.MainLog));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("日志切面异常", throwable.getMessage());
        }
    }

}
