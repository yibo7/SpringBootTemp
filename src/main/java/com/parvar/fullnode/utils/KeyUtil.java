package com.parvar.fullnode.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 生成唯一键值
 *
 * @author 蔡齐盛
 * @create 2017-12-15 18:53
 **/
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式: 时间+4随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
//        Random random = new Random();
//        Integer number = random.nextInt(9000) + 1000;
//        return System.currentTimeMillis() + String.valueOf(number);

        return System.currentTimeMillis()+"";
    }

    /**
     * 将当前时间转化为毫秒
     * @return
     */
    public static  long getCurrentTimeInt() {
        return System.currentTimeMillis();
    }
}
