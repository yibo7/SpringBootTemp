package com.ebsite.tempsite.aspect.weblogs;
public class LogsTypeEnum {

    public static final int LOGS_APPERR = 1;//系统出错

    public static final int LOGS_FAILED = 2;//操作执行失败
    public static final int LOGS_SETTING = 3; //系统配置类日志

    public static String getEnumString(int value) {
        String name = "";
        if (value == LOGS_APPERR) {
            name = "系统出错";
        } else if (value == LOGS_FAILED) {
            name = "操作执行失败";
        } else if (value == LOGS_SETTING) {
            name = "系统配置类日志";
        }
        return name;
    }
}
