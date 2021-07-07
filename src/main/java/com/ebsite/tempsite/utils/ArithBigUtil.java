package com.ebsite.tempsite.utils;

import java.math.BigDecimal;

/**
 * 准确的算数工具类(加减乘除) BigDecimal版本
 *
 * @author 毛峰
 * @create 2018-02-10 14:13
 **/
public class ArithBigUtil {
    private static final int DEF_DIV_SCALE = 10;

    private ArithBigUtil() {
    }

    /**
     * 加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.ZERO;
        }
        if (d2 == null) {
            d2 = BigDecimal.ZERO;
        }
        return d1.add(d2);
    }

    public static void main(String arg[]) {
        BigDecimal a = new BigDecimal(0);
        BigDecimal b = new BigDecimal(0);
        BigDecimal c = a;
        System.out.println(add(a, c));
    }

    /**
     * 减法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.ZERO;
        }
        if (d2 == null) {
            d2 = BigDecimal.ZERO;
        }
        return d1.subtract(d2);

    }

    /**
     * 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        return d1.multiply(d2);

    }

    /**
     * 除法
     *
     * @param d1 除数
     * @param d2 被除数
     * @return
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {

        return div(d1, d2, DEF_DIV_SCALE);

    }

    /**
     * 除法
     *
     * @param d1    除数
     * @param d2    被除数
     * @param scale 小数点后要保留的位数
     * @return
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度不能小于零");
        }
        return d1.divide(d2, scale, BigDecimal.ROUND_DOWN);

    }

    /**
     * 返回两个数中大的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static BigDecimal returnMax(BigDecimal v1, BigDecimal v2) {
        return v1.max(v2);
    }

    /**
     * 返回两个数中小的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static BigDecimal returnMin(BigDecimal v1, BigDecimal v2) {
        return v1.min(v2);
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2);
    }

}
