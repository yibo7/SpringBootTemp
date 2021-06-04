package com.parvar.fullnode.exception;

import lombok.Getter;

public enum EResultEnum {
    UNKNOWN_ERROR(-1,"unknown error"), //未知错误
    SUCCESS(0,"success"),  //操作成功
    MINVALUE100(100,"please enter a value less than 100"), //输入的值不能小100
    NULL(101,"no result"),  //无结果
    ERRPRAM(102,"parameter is error"),
    NOUSER(1,"without this user"),
    USEREXPRIRED(2,"this user has expired"),
    USERLOCK(3,"this user has been locked up"),
    NOACCESS(4,"no access"),
    ERRUSERORPASS(5,"password or account error"),
    ERRVALCODE(6,"verification code is wrong"),
    ;
    @Getter
    private Integer code;
    @Getter
    private String msg;
    EResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
