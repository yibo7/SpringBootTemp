package com.ebsite.tempsite.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/*-------------------------------------------------------------------------
* 作者：IBM_LELE
* 创建时间： 2019/11/6
* 版本号：v1.0
* 本类主要用途描述：
*  -------------------------------------------------------------------------*/
@Slf4j
public class ApiUitl {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    /**
     * 生成32位大写MD5值
     */
    public static String getMD5String(String str) {
        try {
            if (str == null || str.trim().length() == 0) {
                return "";
            }
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
                        + HEX_DIGITS[bytes[i] & 0xf]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("出现异常，方法名{}，异常信息：{}","getMD5String",e.getMessage());
        }
        return "";
    }

//    /**
//     * 私钥的生成规则:获取当前用户的密码+用户Id+用户账号+当前时间+ip 然后 md5利用32位MD5算法
//     * @param uid 用户Id
//     * @param password
//     * @param ip
//     * @return
//     */
//    public static String buildPrivateKey(String uname, int uid, String password, Timestamp createTime, String ip){
//        String result=getMD5String(uname+uid+password+ip+createTime);
//        //log.info("加密之前的私钥:{}",result);
//        return EbChangeEncode.encryptAes(result);
//    }
    /**
     * 公钥的生成规则:随机生成一个GUID 然后 md5利用32位MD5算法
     * @return 一个字符串
     */
    public static String buildPubliKey() {
        String sourceStr= UUID.randomUUID().toString().replace("-","");

        return  getMD5String(sourceStr);
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 构造签名
     * @param params 需要参与签名的参数
     * @param secret_key 私钥
     * @return 签名结果,所有字母全部大写
     */
    public static String buildMysignV1(Map<String,String> params,String secret_key){
        //2.将待签名字符串要求按照参数名进行排序(首先比较所有参数名的第一个字母，按abcd顺序排列，若遇到相同首字母，则看第二个字母，以此类推)
        String result= ApiUitl.createLinkString(params);

        //3.将排序后的结果链接&secret_key=secretKey
        result=new StringBuffer(result).append("&secret_key=").append(secret_key).toString();
        //4.利用32位MD5算法，对最终待签名字符串进行签名运算，从而得到签名结果字符串（MD5计算结果中字母全部大写）
        //log.info("追加私钥后的结果：{}",result);

        result= ApiUitl.getMD5String(result);
        //log.info("签名：{}",result);
        return result;
    }


    public static boolean paramIsEmpty(Map<String,String> param){
        if (param == null || param.size()==0) {
            return true;
        }
        for (String key : param.keySet()) {
            if(StringUtils.isEmpty(param.get(key) + "")){

                return true;
            }
        }
        return false;
    }
    public static Integer matchePasswodrRules(String password) {

        if (password.matches("^([0-9]+){6,}$") || password.matches("^([a-zA-Z]+){6,}$")) {
            return 1;
        }

        if (password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$")) {

            return 2;
        }
        if (password.matches("^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,}$")) {

            return 3;
        }

        return 0;
    }
}
