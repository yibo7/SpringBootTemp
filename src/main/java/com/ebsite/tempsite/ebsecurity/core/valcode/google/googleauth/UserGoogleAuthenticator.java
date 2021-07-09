package com.ebsite.tempsite.ebsecurity.core.valcode.google.googleauth;

import lombok.Getter;

/**
 * 一个用户的GOOGLE身份认证器
 *
 * @author 蔡齐盛
 * @create 2017-12-09 18:49
 **/
public class UserGoogleAuthenticator {
    private String userName;
    @Getter private String secretKey;

    /**
     * 对新用户初始化
     * @param userName
     */
    public UserGoogleAuthenticator(String userName) {
        this.userName = userName;
        //生成绑定密钥
        this.secretKey = GoogleAuthenticator.generateSecretKey();
    }

    /**
     * 对已经绑定的用户初始化
     * @param userName  用户名称
     * @param secretKey  绑定过的secretKey
     */
    public UserGoogleAuthenticator(String userName,String secretKey) {
        this.userName = userName;
        //生成绑定密钥
        this.secretKey = secretKey;
    }
    /**
     * 获取二维码内容，qrcode:otpauth://totp/2816661736@qq.com?secret=OMEDHYHXFNFPADKI
     * @return
     */
    public String getQRBarcode(){
        String qrcode = GoogleAuthenticator.getQRBarcode(userName, secretKey,"COINBIG");
        return qrcode;
    }

    /**
     * 验证google验证码是否正确
     * @param code 用户输入的验证码
     * @param key  用户绑定的google key
     * @return
     */
    static public boolean checkCode(long code,String key){
         long t = System.currentTimeMillis();
         GoogleAuthenticator ga = new GoogleAuthenticator();
         ga.setWindowSize(1);
         return ga.check_code(key, code, t);
    }


}
