package com.ebsite.tempsite.utils.encrypt;

/**
 * ebchange使用的默认加密方法
 *
 * @author 蔡齐盛
 * @create 2017-12-13 11:48
 **/
public class EbEncode {
   private static final   String encryptKey = "f6ac9dc91e46463082757b5722648926";

    /**
     * AES加密
     * @param content 加密内容
     * @return
     */
    public static String encryptAes(String content) {

        return AESUtil.encrypt(content,encryptKey);
    }

    /**
     * AES解密
     * @param content 要解密的内容
     * @return
     */
    public static String decryptAes(String content) {
        return AESUtil.decrypt(content,encryptKey);
    }
    public static String encryptAesMD5(String content) {
        return encryptMD5(encryptAes(content));
    }

    /**
     * 将在md5的内容+网站的密钥后md5
     * @param content
     * @return
     */
    public static String encryptKeyMD5(String content) {
        return encryptMD5(content+encryptKey);
    }

    public static String encryptMD5(String content) {
        return MD5Encrypt.MD5Encode(content);
    }

}
