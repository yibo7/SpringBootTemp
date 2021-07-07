package com.ebsite.tempsite.aspect.weblogs;

import java.lang.annotation.*;

/**
 * @author lsm
 * @create 2019-06-14 11:52
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ControllerLog {

    /**
     * 描述
     *
     * return
     */
    String description() default "";

    /**
     * 操作日志类型
     *
     * @return
     */
    int logstype() default 0;

}
