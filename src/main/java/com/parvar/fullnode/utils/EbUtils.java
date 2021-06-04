package com.parvar.fullnode.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 当前项目工具类
 *
 * @author 毛峰
 * @create 2017-12-01 12:58
 **/
@Slf4j
public class EbUtils {
    /**
     * 1. 初始(NEW)：新创建了一个线程对象，但还没有调用start()方法。
     2. 运行(RUNNABLE)：Java线程中将就绪（ready）和运行中（running）两种状态笼统的成为“运行”。
     线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得cpu 时间片后变为运行中状态（running）。
     3.阻塞(BLOCKED)：表线程阻塞于锁。
     4.等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）。
     5.超时等待(TIME_WAITING)：该状态不同于WAITING，它可以在指定的时间内自行返回。
     6. 终止(TERMINATED)：表示该线程已经执行完毕。
     * @param state
     * @return
     */
    public static String getTreadStateCnName(Thread.State state){
       String sName = "未知";
        switch (state){
            case NEW:
                sName = "初始";
                break;
            case WAITING:
                sName = "等待";
                break;
            case BLOCKED:
                sName = "线程阻塞";
                break;
            case RUNNABLE:
                sName = "运行中";
                break;
            case TERMINATED:
                sName = "终止";
                break;
            case TIMED_WAITING:
                sName = "超时等待";
                break;

        }
        return sName;
    }
    /**
     * 利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     */
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String getJs() {
        return "fsdfsdf";
    }

    public static Date string2Data(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("时间格式转换失败:{}", e.getStackTrace());
        }

        return null;
    }

    //删除指定位数后面的数字,如2.35会被删除为2.3
    public static double getDouble(double value, int scale) {
        return new BigDecimal(String.valueOf(value)).setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    //删除指定位数后面的数字,如2.35会被删除为2.3
    public static BigDecimal getBigDecimal(BigDecimal value, int scale) {
        return value.setScale(scale, BigDecimal.ROUND_DOWN);
    }

    //获取时间戳
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    //判断该对象是否为空属性
    public static boolean isAllFieldNull(Object obj) throws IllegalAccessException {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);// 得到此属性的值
            if (val != null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取request中ip地址(反代理)
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        return RequestPrams.getIpAddress(request);
        /*try {
            String ip = request.getHeader("X-Forwarded-For");
            try {
                if (ip != null && ip.trim().length() > 0) {
                    return ip.split(",")[0];
                }
            } catch (Exception e) {
            }

            try {
                ip = request.getHeader("X-Real-IP");
                if ((ip != null && ip.trim().length() > 0) && (!"unKnown".equalsIgnoreCase(ip))) {
                    return ip;
                }
            } catch (Exception e) {
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("http_client_ip");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            // 如果是多级代理，那么取第一个ip为客户ip
            if (ip != null && ip.indexOf(",") != -1) {
                ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
            }
            return ip;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return request.getRemoteAddr();
        }*/
    }

    public static String getAfterDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -day);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 判断double是否是整数
     *
     * @param obj
     * @return
     */
    public static boolean isIntegerForDouble(double obj) {
        double eps = 1e-10;  // 精度范围
        return obj - Math.floor(obj) < eps;
    }

    /**
     * 判断double小数点后的位数
     *
     * @param d1
     * @return
     */
    public static int doubleDigit(double d1) {
        //整数返回0
        if (isIntegerForDouble(d1)) {
            return 0;
        }
        String s = doubleFormat(d1);
        return (s.length() - s.indexOf(".")) - 1;
    }

    /**
     * 格式化double,不以科学计数法显示
     *
     * @param d1
     * @return
     */
    public static String doubleFormat(double d1) {
        BigDecimal db = new BigDecimal(Double.toString(d1));
        return db.stripTrailingZeros().toPlainString();
    }

    /**
     * 格式化BigDecimal,不以科学计数法显示
     *
     * @param d1
     * @return
     */
    public static String bigDecimalFormat(BigDecimal d1) {
        return d1.stripTrailingZeros().toPlainString();
    }

    /**
     * 格式化double,不以科学计数法显示
     *
     * @param d1
     * @return
     */
    public static double doubleFormat(double d1, int scale) {
        BigDecimal db = new BigDecimal(Double.toString(d1));
        return db.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }


    /**
     * double根据指定位数UP(如:3.52 设置位数为1, 结果是3.6)
     *
     * @param d1
     * @param upCount 需要判断的位数
     * @return
     */
    public static double doubleUP(double d1, int upCount) {
        BigDecimal bigDecimal = BigDecimal.valueOf(d1);
        return bigDecimal.setScale(upCount, BigDecimal.ROUND_UP).doubleValue();
    }

    /**
     * double根据指定位数UP(如:3.52 设置位数为1, 结果是3.6)
     *
     * @param d1
     * @param upCount 需要判断的位数
     * @return
     */
    public static BigDecimal bigDecimalUP(BigDecimal d1, int upCount) {
        return d1.setScale(upCount, BigDecimal.ROUND_UP);
    }


    /**
     * 得到随机的32位的字符串
     *
     * @return
     */
    public static String getUUID() {
        //其中replace()方法表示将字符串中的"-"变为""
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 加密用户名称
     *
     * @param name
     * @return
     */
    public static String hideUserName(String name) {
        if (name == null || "".equals(name.trim())) {
            return "";
        }
        String pre = name.substring(0, 3);
        String hide = "****";
        if (name.length() > 7) {
            String end = name.substring(name.length() - 4, name.length());
            name = pre.concat(hide).concat(end);
        } else {
            name = pre.concat(hide);
        }
        return name;

    }


    /**
     * 对超过亿,万的数据进行模块化
     *
     * @param count
     * @return
     */
    public static String formattedTenThousand(double count) {
        String fcount = "";
        if (count >= 1000) {
            DecimalFormat df = new DecimalFormat(",###,###.####");
            fcount = df.format(BigDecimal.valueOf(count));
        } else {
            fcount = doubleFormat(count);
        }
        return fcount;
    }

    public static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim())) {
            return s.matches("^[0-9]*$");
        } else {

            return false;
        }
    }

    public static String dateFormat(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(timestamp);

    }


    /**
     * 根据value值找key
     *
     * @param map
     * @param value
     * @return
     */
    public static List<Object> getKey(Map map, Object value) {
        List<Object> keyList = new ArrayList<>();
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }


    /**
     * 获取Exception异常详细信息，以便保存
     *
     * @param ex
     * @return
     */
    public static String getExceptionAllInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        }catch(Exception e){
            return ex.getMessage();
        }finally{
            if(pout!=null){
                pout.close();
            }
        }
        return ret;
    }



    /**
     * 获取本机的mac地址
     *
     * @return
     */
    public static String getMACAddress() throws Exception {
        return ComputerInfo.getMacAddress().replaceAll("-","").replaceAll(":","");

    }

    /**
     * 获取0.1 - 5之间的一个小数
     *
     * @return
     */
    public static double getRandomforFives(int max){
        Random random = new Random();
        //小数位
        int decimal = random.nextInt(9)%(9-1+1) + 1;
        //整数位
        int whole = random.nextInt(max)%(max-0+1) + 0;

        double add = ArithUtil.add(ArithUtil.mul(decimal, 0.1), whole);
        return add;

    }

    /**
     * 获取一个随机数看是否小于指定的一个数值(可以用于概率随机,比如说: max传7, 那就会有7成的概率返回true)
     *
     * @return 是否小于指定的max值
     */
    public static boolean getProbabilityRandom(int max){
        Random random = new Random();
        //小数位
        int decimal = random.nextInt(10);

        return decimal < max;

    }


    /**
     * 判断一个字符串,是否在另外一个字符串分割数组中是否存在
     *
     * @param source        源字符串               例: 1,13,15
     * @param separator     分隔符                 例: ,
     * @param judgeString   要判断的字符串         例:1
     * @return  存在返回true                        例: true
     */
    public static boolean whetherExist(String source,String separator,String judgeString){

        return Arrays.asList(source.split(separator)).contains(judgeString);
    }


    public static <T> T htmlEscape(T entity){

        Class<T> entityClass = (Class<T>) entity.getClass();
        try {
            Field[] fields = entityClass.getDeclaredFields();
            for(Field field:fields){
                if(field.getType()==String.class){
                    field.setAccessible(true);
                    if(field.get(entity)!=null){
                        field.set(entity, HtmlUtils.htmlEscape(field.get(entity).toString()));
                    }
                }
            }
            return entity;

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     */
    public static boolean isContainChinese(String str) {
        Matcher m = Pattern.compile("[\u4e00-\u9fa5]").matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

}
