package com.ebsite.tempsite.ebsecurity.browser;//package com.parvar.fullnode.ebsecurity.browser;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 验证码相关的扩展点配置。配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
// * 模块默认的配置。
// *
// */
//@Configuration
//public class CustomBeanConfig {
//
//
//    /**
//     * 邮箱验证码发送器,只要在项目中找到ILoginSuccessPush的实现，将不使用此默认配置
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(ILoginSuccessPush.class)
//    public ILoginSuccessPush CustomLoginSuccessPush() {
//        return new DefaultLoginSuccessPush();
//    }
//
//}
