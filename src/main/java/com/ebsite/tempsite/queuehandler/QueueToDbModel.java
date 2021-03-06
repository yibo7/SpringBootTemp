package com.ebsite.tempsite.queuehandler;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作日志的实例类
 *
 * @author 蔡齐盛
 * @create 2018-02-13 11:14
 **/
@Data
public class QueueToDbModel implements Serializable {
    private static final long serialVersionUID = -6378830550762748469L;
    private Object log;
    private QueueToDbEnum queueToDbEnum;

    /**
     * 创建一个日志队列对象
     * @param log  日志对象
     * @param queueToDbEnum  日志类型
     */
    public QueueToDbModel(Object log, QueueToDbEnum queueToDbEnum) {
        this.log = log;
        this.queueToDbEnum = queueToDbEnum;
    }

    public QueueToDbModel() {
    }
}
