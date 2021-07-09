package com.ebsite.tempsite.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间处理根据类
 *
 * @author 严建杰
 * @create 2017-12-26 12:58
 **/
@Slf4j
public class DateUtils {
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(Date ts) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            tsStr = sdf.format(ts);
        } catch (Exception e) {
            log.error("出现异常，方法名：stampToDate，异常信息：" + e.getMessage());
        }
        return tsStr;

    }

    /**
     * 获得上周一
     *
     * @param date
     * @return
     */
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 获得本周一
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获得下周一
     *
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static String longToDateString(long systime, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        //前面的SysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        Date dt = new Date(systime * 1000);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示
        return sdf.format(dt);
    }

    /**
     * 将毫秒值，转换成时间 HH：mm：ss
     *
     * @param systime
     * @param formatString
     * @return
     */
    public static String longToDateTime(long systime, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date dt = new Date(systime);
        return sdf.format(dt);
    }


    public static String formatDateTime(Date date) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = sdf.format(date);
        } catch (Exception e) {
            log.error("出现异常，方法名：formatDateTime，异常信息：" + e.getMessage());
        }
        return tsStr;
    }


    /*
     * 将时间戳转换为时间,自定义时间格式
     */
    public static String stampToDateSdf(Date ts, String sdf) {
        String tsStr = "";
        DateFormat sdf1 = new SimpleDateFormat(sdf);
        try {
            tsStr = sdf1.format(ts);
        } catch (Exception e) {
            log.error("出现异常，方法名：stampToDateSdf，异常信息：" + e.getMessage());
        }
        return tsStr;

    }

    /**
     * 根据时间返回刻度时间
     *
     * @param datetime
     * @param scale
     * @return
     */
    public static long GetDateByScale(long datetime, String scale) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long newTime = 0;
        try {
            switch (scale) {
                case "1":
                    //获得当前时间至天
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    newTime = format.parse(format.format(datetime)).getTime();
                    break;
                case "5":
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date5 = format.parse(format.format(datetime));
                    int munt5 = date5.getMinutes();
                    date5.setMinutes((int) Math.floor(munt5 / 5) * 5);
                    newTime = date5.getTime();
                    break;
                case "15":
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date15 = format.parse(format.format(datetime));
                    int munt15 = date15.getMinutes();
                    date15.setMinutes((int) Math.floor(munt15 / 15) * 15);
                    newTime = date15.getTime();
                    break;
                case "30":
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date30 = format.parse(format.format(datetime));
                    int munt30 = date30.getMinutes();
                    date30.setMinutes((int) Math.floor(munt30 / 30) * 30);
                    newTime = date30.getTime();
                    break;
                case "60":
                    format = new SimpleDateFormat("yyyy-MM-dd HH");
                    newTime = format.parse(format.format(datetime)).getTime();
                    break;
                case "180":
                    //00  03  06  09 12 15 18 21
                    format = new SimpleDateFormat("yyyy-MM-dd HH");
                    Date date180 = format.parse(format.format(datetime));
                    int hour180 = date180.getHours();
                    date180.setHours((int) Math.floor(hour180 / 3) * 3);
                    newTime = date180.getTime();
                    break;
                case "240":
                    //00  03  06  09 12 15 18 21
                    format = new SimpleDateFormat("yyyy-MM-dd HH");
                    Date date240 = format.parse(format.format(datetime));
                    int hour240 = date240.getHours();
                    date240.setHours((int) Math.floor(hour240 / 4) * 4);
                    newTime = date240.getTime();
                    break;
                case "360":
                    //00 06 12 18
                    format = new SimpleDateFormat("yyyy-MM-dd HH");
                    Date date360 = format.parse(format.format(datetime));
                    int hour360 = date360.getHours();
                    date360.setHours((int) Math.floor(hour360 / 6) * 6);
                    newTime = date360.getTime();
                    break;
                case "D":
                    //获得当前时间至天
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    newTime = format.parse(format.format(datetime)).getTime();
                    break;
                case "W":
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    newTime = getThisWeekMonday(format.parse(format.format(datetime))).getTime();
                    break;
                case "M":
                    //获得当前时间至月份
                    format = new SimpleDateFormat("yyyy-MM");
                    newTime = format.parse(format.format(datetime)).getTime();
                    break;
                default:
            }
        } catch (Exception ex) {

        }
        return newTime;
    }


    /**
     * 获取针对当前时间,任何一个小时的整点
     *
     * @param hour 0为当前时间的整点 1为下个小时的整点 -1为上个小时的整点
     * @return
     */
    public static String anyHourOfTheHour(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        return df.format(calendar.getTime());
    }


    /**
     * 获取针对当前时间,一天的晚上0点
     *
     * @param hour 0为今天整点 1为明天的整点 -1为昨天的整点
     * @return
     */
    public static String anyHourOfTheDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + hour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return df.format(calendar.getTime());
    }

    /**
     * 当前时间  距离当天凌晨  秒数
     *
     * @return
     */
    public static long secondMorningOfTheDay() {
        long overTime = 0;
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
            overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime())) / 1000;
        } catch (ParseException e) {
            log.error("出现异常，方法名：get，异常信息：" + e.getMessage());
        }
        return overTime;
    }

    /**
     * 获得当月1号零时零分零秒
     *
     * @return
     */
    public static Timestamp initDateByMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return timestamp;
    }


    /**
     * 判断是否是今天
     *
     * @param date
     * @return
     */
    public static Boolean isToday(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        Integer y2 = cal2.get(Calendar.YEAR);
        Integer m2 = cal2.get(Calendar.MONTH);
        Integer d2 = cal2.get(Calendar.DAY_OF_MONTH);
        Integer y1 = cal1.get(Calendar.YEAR);
        Integer m1 = cal1.get(Calendar.MONTH);
        Integer d1 = cal1.get(Calendar.DAY_OF_MONTH);
        if (y1.intValue() == y2.intValue()
                && m1.intValue() == m2.intValue()
                && d1.intValue() == d2.intValue()) {
            return true;
        }
        return false;
    }

    /**
     * @param date1  字符串日期1
     * @param date2  字符串日期2
     * @param format 日期格式化方式  format="yyyy-MM-dd"
     * @return
     * @descript:计算两个字符串日期相差的天数
     */
    public static long dayDiff(String date1, String date2, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        long diff = 0L;
        try {
            long d1 = formater.parse(date1).getTime();
            long d2 = formater.parse(date2).getTime();
            //diff=(Math.abs(d1-d2) / (1000 * 60 * 60 * 24));
            diff = (d1 - d2) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            log.error("出现异常，方法名：dayDiff，异常信息：" + e.getMessage());
        }
        return diff;
    }

    /**
     * 获取指定区段内容的开始和结束时间
     */
    public static Map<String, String> getDateSection(int section) {
        //计算24小时划分的时 段数量
        long countSection = 24 / section;

        Calendar calendar =new GregorianCalendar() ;
        //当前时间 秒
        int current = calendar.get(Calendar.HOUR_OF_DAY) ;


        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < countSection; i++) {
            if (current >= i * section && current < (i + 1) * section){
                //计算开始时间
                Calendar start = Calendar.getInstance();
                start.set(Calendar.HOUR_OF_DAY, (i * section));
                //计算结束时间
                Calendar end = Calendar.getInstance();
                end.set(Calendar.HOUR_OF_DAY, ((i + 1) * section));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
//                System.out.println("当前的小时："+current+"，开始："+ sdf.format(new Date(start.getTimeInMillis()))+"，结束："+sdf.format(new Date(end.getTimeInMillis())));
                map.put("start", sdf.format(new Date(start.getTimeInMillis())));
                map.put("end", sdf.format(new Date(end.getTimeInMillis())));
                break;
            }

        }

        return map;
    }


    /**
     * 获取指定区段内容的开始和结束时间(自定义时间版)
     */
    public static Map<String, String> getDateSectionCustomize(int section,String sdfs) {
        //计算24小时划分的时 段数量
        long countSection = 24 / section;

        Calendar calendar =new GregorianCalendar() ;
        //当前时间 秒
        int current = calendar.get(Calendar.HOUR_OF_DAY) ;


        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < countSection; i++) {
            if (current >= i * section && current < (i + 1) * section){
                //计算开始时间
                Calendar start = Calendar.getInstance();
                start.set(Calendar.HOUR_OF_DAY, (i * section));
                //计算结束时间
                Calendar end = Calendar.getInstance();
                end.set(Calendar.HOUR_OF_DAY, ((i + 1) * section));
                SimpleDateFormat sdf = new SimpleDateFormat(sdfs);
                map.put("start", sdf.format(new Date(start.getTimeInMillis())));
                map.put("end", sdf.format(new Date(end.getTimeInMillis())));
                break;
            }

        }

        return map;
    }

    /**
     * YHL
     * 时间 相加减
     * @param date
     * @param iCount
     * @param type  1:day 2.hour
     * @return
     */
    public static Date getAddDate(Date date, int iCount,int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(type==1){
            cal.add(Calendar.DAY_OF_MONTH, iCount);
        }else  if(type==2){
            cal.add(Calendar.HOUR_OF_DAY, iCount);
        }
        return cal.getTime();
    }


    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 获取指定时间的 周一 日历
     *
     * @param time
     */
    public static Calendar getMondayCalendar(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);

        //时分秒,毫秒 清零
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        return cal;
    }

    /**
     * 获取指定时间的 周一 0点
     *
     * @param time
     */
    public static Date getMonday(Date time) {
        return getMondayCalendar(time).getTime();
    }

    /**
     * 获取指定时间的 下周一 0点
     *
     * @param time
     */
    public static Date getNextMonday(Date time) {
        Calendar cal = getMondayCalendar(time);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();

    }

    /**
     * 获取今天凌晨0点整的时间戳
     *
     * @param
     */
    public static long getTodayStartTime() {
        long nowTime =System.currentTimeMillis();
        long todayStartTime = nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
        return todayStartTime;
    }
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }
    /**
     * 获取当前时间整点毫秒值
     * @return
     */
    public static long getNowDateToHourTime() {
        long overTime = 0;
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
            overTime = sdfOne.parse(sdfOne.format(now)).getTime();
        } catch (ParseException e) {
            log.error("出现异常，方法名：get，异常信息：" + e.getMessage());
        }
        return overTime;
    }
}
