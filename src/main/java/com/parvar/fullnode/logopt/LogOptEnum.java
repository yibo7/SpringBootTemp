package com.parvar.fullnode.logopt;

import lombok.Getter;

/**
 * 书写规则：FLOG为日志表的名称
 */
public enum LogOptEnum {
    FLOG(0,"FLOG通用日志"),
    FVALIDATEEMAIL(1,"fvalidateemail邮件信息"),
    FVALIDATEMESSAGE(2,"fvalidatemessage手机信息"),
    ;
    @Getter
    private Integer code;
    @Getter
    private String msg;

    LogOptEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}