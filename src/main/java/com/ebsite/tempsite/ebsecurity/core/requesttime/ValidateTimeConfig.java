package com.ebsite.tempsite.ebsecurity.core.requesttime;

import lombok.Data;

@Data
public class ValidateTimeConfig  {
    /**
     * Id是唯一键值
     */
    private String id;
    /**
     * 要拦截的URL,多个用逗号分开
     */
    private String urls;
    /**
     *  时间间隔（毫秒）
     */
    private Integer timespan;

}