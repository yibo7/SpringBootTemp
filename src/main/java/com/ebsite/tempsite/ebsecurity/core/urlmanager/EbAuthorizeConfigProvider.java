/**
 * 
 */
package com.ebsite.tempsite.ebsecurity.core.urlmanager;

import com.ebsite.tempsite.ebsecurity.core.configs.LoginConstants;
import com.ebsite.tempsite.ebsecurity.core.configs.SecurityConfigs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 *这里是配置了一些默认的免验证URL
 * 更多的免验证规则可以重写更多的这样的类
 */
@Component
@Order(Integer.MIN_VALUE)
public class EbAuthorizeConfigProvider implements AuthorizeConfigProvider {

	@Autowired
	private SecurityConfigs securityConfigs;

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(LoginConstants.Default_Login_Require,
				LoginConstants.Default_Login_PostUrl,
				LoginConstants.Default_Login_Url,
				LoginConstants.Default_Code_Url_Prefix + "/*",
				securityConfigs.getBrowser().getLoginPage(),
				securityConfigs.getBrowser().getSignOutUrl(),
				securityConfigs.getBrowser().getSignOutSecucessUrl()
				).permitAll();

		if (StringUtils.isNotBlank(securityConfigs.getBrowser().getSignOutUrl())) {
			config.antMatchers(securityConfigs.getBrowser().getSignOutUrl()).permitAll();
		}
		return false;
	}

}
