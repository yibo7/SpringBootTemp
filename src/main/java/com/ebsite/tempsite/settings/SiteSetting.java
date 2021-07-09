package com.ebsite.tempsite.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component //需要加入这个，否则在引用的地方会有找不到的报错提醒
@ConfigurationProperties(prefix = "siteconfigs")
public class SiteSetting {
    private String urlUserInex;
    private String siteName;
    private String publicKey;
    private String privateKey;
    private String allowIps;
}
