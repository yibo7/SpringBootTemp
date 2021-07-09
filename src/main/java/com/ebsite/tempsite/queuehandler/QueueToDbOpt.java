package com.ebsite.tempsite.queuehandler;

import com.ebsite.tempsite.pojo.LogsPojo;
import com.ebsite.tempsite.service.LogsServiceImpl;
import com.ebsite.tempsite.utils.EbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 通用队列处理，一些不用实时的，并且频繁的数据库操作可以先写到队列来处理
 *
 * @author 蔡齐盛
 * @create 2018-02-13 11:03
 **/
@Component
@Slf4j
public class QueueToDbOpt {
    String  logOptQueuesKey = "logOptQueuesKey";
    @Resource //使用泛型RedisTemplate时不能使用 Autowired
    private RedisTemplate<String, QueueToDbModel> redisTemplate;
    /**
     * 是否开启了队列,比如没有安装redis可以将isOpenQueue设置为false,否则将isOpenQueue设置为true
     */
    private boolean isOpenQueue = false;
    public void addData(QueueToDbModel model) { //写入一个队列消息
        if(isOpenQueue){
            long len =  redisTemplate.opsForList().leftPush(logOptQueuesKey,model);
        }else{
            addLogToDb(model);
        }

    }

    private QueueToDbModel popLog() { //获取一个队列消息
        return redisTemplate.opsForList().leftPop(logOptQueuesKey);
    }

    @Autowired
    private LogsServiceImpl logsService;

    private void addLogToDb(QueueToDbModel model) {
        switch (model.getQueueToDbEnum()){
            case MainLog:
                LogsPojo md =(LogsPojo) model.getLog(); //转换成相应的对象并添加到数据库
                if(md!=null)
                    logsService.save(md);
                break;
            case SendMsg:
                //邮件信息手机信息
//                SendMsg   log = (SendMsg) model.getLog();
//                mSendMsgService.InsertToDb(log);
                break;
            case UserNotice:
                //公告推送之类
//                Notice   notice = (Notice) model.getLog();
//                noticeService.InsertToDb(notice);
                break;
            default:
        }
    }

    public  void startToDb(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true ){
                    //获取队列中的一个订单
                    QueueToDbModel model = popLog();
                    try {
                        if(model!=null){
                            //将订单添加到数据库
                            addLogToDb(model);
                        }
                    } catch (Exception e) {
                        log.error("LOGOPT日志入库失败 {}   -- {}", model.toString(), EbUtils.getExceptionAllInfo(e));
                    }
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        log.error("出现异常，方法名：startToDb，异常信息："+e.getMessage());
                    }
                }

            }
        }) ;
        thread.setPriority(Thread.MAX_PRIORITY) ;
        thread.start() ;
    }
}
