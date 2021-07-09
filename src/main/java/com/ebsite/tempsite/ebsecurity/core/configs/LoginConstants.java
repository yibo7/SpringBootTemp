package com.ebsite.tempsite.ebsecurity.core.configs;

public interface LoginConstants {

    String Default_Login_Url = "/ebseclogin.html";
     String Default_Login_PostUrl = "/eblogin/from";
    /**
     * 当请求需要身份认证时，默认跳转的url
     *
     * @see *.ebsecurity.browser.BrowserSecurityController
     */
     String Default_Login_Require = "/login/require";


    /**
     * 默认的处理验证码的url前缀
     */
    String Default_Code_Url_Prefix = "/code";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String Default_Parameter_Name_Code_Sms_Image = "imageCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String Default_Parameter_Name_Code_Sms= "smsCode";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    String Default_Parameter_Name_Code_Email= "emailCode";
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    String Default_Parameter_Name_Mobile = "mobile";
    /**
     * 发送邮件验证码 或 验证邮件验证码时，传递邮箱地址的参数的名称
     */
    String Default_Parameter_Name_Email = "em";
    /**
     * 发送随机验证码的表单名称，也就是参数名称
     */
    String Default_Parameter_Name_Code_JsRandom= "jsRandom";

    /**
     * 发送随机验证码的表单名称，也就是参数名称
     */
    String Default_Parameter_Name_Code_Google= "googlecode";
}
