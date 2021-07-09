package com.ebsite.tempsite.queuehandler;

import lombok.Getter;

/**
 * 书写规则：FLOG为日志表的名称
 */
public enum QueueToDbEnum {
    MainLog(0,"LOG通用日志"),
    SendMsg(1,"SengMsg包括邮件与短信类型的数据"),
    UserNotice(2,"公告消息"),
    ;
    @Getter
    private Integer code;
    @Getter
    private String msg;

    QueueToDbEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
