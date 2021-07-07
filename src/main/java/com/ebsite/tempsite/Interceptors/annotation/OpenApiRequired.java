package com.ebsite.tempsite.Interceptors.annotation;

import java.lang.annotation.*;

/**
 * @author cqs
 * @version 1.0
 * @date 2021/6/8
 * @remark
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApiRequired {
//    boolean value() default true;
    boolean isCheckIp() default true;
}
