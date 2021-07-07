package com.ebsite.tempsite.service.base;


/**
 * 查询条件支持的过滤方式
 *
 * @author 毛峰
 * @create 2018-11-30 17:28
 **/
public enum QueryTypeEnum {
    /**
     * 包含
     */
    in,
    /**
     * 模糊
     */
    like,
    /**
     * 等于
     */
    equal,
    /**
     * 不等
     */
    ne,
    /**
     * 小于
     */
    lt,
    /**
     * 小于或等于
     */
    lte,
    /**
     * 大于
     */
    gt,
    /**
     * 大于或等于
     */
    gte
}
