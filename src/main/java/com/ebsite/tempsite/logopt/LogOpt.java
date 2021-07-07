package com.ebsite.tempsite.logopt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 通用日志处理
 *
 * @author 蔡齐盛
 * @create 2018-02-13 11:03
 **/
@Component
public class LogOpt implements ILogOpt {

    @Resource //使用泛型RedisTemplate时不能使用 Autowired
    private RedisTemplate<String,LogOptModel> redisTemplate;
    /**
     * 将一个日志写入队列
     * @param model 队列日志实例
     */
    @Override
    public void addLog(LogOptModel model) {

        long len =  redisTemplate.opsForList().leftPush(logOptQueuesKey,model);
    }

    /**
     * 从队列获取一个日志
     * @return
     */
    @Override
    public LogOptModel popLog() {
        return redisTemplate.opsForList().leftPop(logOptQueuesKey);
    }

    /**
     * 将一个队列日志实例写入数据库
     * @param model 队列日志实例
     */
    @Override
    public void addLogToDb(LogOptModel model) {
        switch (model.getLogOptEnum()){
            case FLOG:
                // model.getLog() 转换成相应的对象并添加到数据库
                break;
            case FVALIDATEEMAIL:
                //邮件信息
//                Fvalidateemail fvalidateemail = fvalidateemailService.saveDto((EmessageDto)model.getLog());
//                if(fvalidateemail!=null&&fvalidateemail.getFid()>0){
//                  在这里发送邮件并保存记录到数据库
//                }
                break;
            case FVALIDATEMESSAGE:
                //手机信息
//                Fvalidatemessage fvalidatemessage = fvalidatemessageService.saveDto((PmessageDto)model.getLog());
//                if(fvalidatemessage!=null&&fvalidatemessage.getFid()>0){
//                      在这里发送手机短信并保存记录到数据库
//                }
                break;

            default:
        }
    }
}
