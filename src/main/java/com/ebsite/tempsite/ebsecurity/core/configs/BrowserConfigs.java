package com.ebsite.tempsite.ebsecurity.core.configs;

import lombok.Data;

@Data
public class BrowserConfigs {
    /**
     * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl = "/signOut";
    private String signOutSecucessUrl = LoginConstants.Default_Login_Url;
    private String loginPage = LoginConstants.Default_Login_Url;


}
