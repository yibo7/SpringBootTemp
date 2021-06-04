package com.parvar.fullnode.logopt;

import com.parvar.fullnode.utils.EbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 操作日志管理实例创建类
 *
 * @author 蔡齐盛
 * @create 2018-02-13 11:25
 **/
@Component
@Slf4j
public class LogOptToDb {
    @Autowired
    private LogOpt logOpt;
    /**
     * 从队列中获取opt日志，并写入数据库
     */
    public  void startToDb(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true ){
                    //获取队列中的一个订单
                    LogOptModel model = logOpt.popLog();
                    try {
                        if(model!=null)
                        {
                            //将订单添加到数据库
                            logOpt.addLogToDb(model);

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
