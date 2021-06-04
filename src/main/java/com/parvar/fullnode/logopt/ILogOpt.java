package com.parvar.fullnode.logopt;

/**
 * 操作类日志处理
 *
 * @author 蔡齐盛
 * @create 2018-02-13 11:01
 **/
public interface ILogOpt {
    /**
     * 日志队列的键
     */
    String  logOptQueuesKey = "logOptQueuesKey";
    void addLog(LogOptModel model);
    LogOptModel popLog();
    void addLogToDb(LogOptModel model);
}
