package com.ebsite.tempsite.ebsecurity.core.configs;

import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeConfig;
import com.ebsite.tempsite.ebsecurity.core.valcode.configs.ValidateCodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component //需要加入这个，否则在引用的地方会有找不到的报错提醒
@ConfigurationProperties(prefix = "ebsecurity")
public class SecurityConfigs
{
    private BrowserConfigs browser = new BrowserConfigs();
    private ValidateCodeProperties code = new ValidateCodeProperties();

    private List<ValidateTimeConfig> times = new ArrayList<ValidateTimeConfig>();

}
