package com.ebsite.tempsite.Interceptors.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

/**
 * @author Jay Yan
 * @version 1.0
 * @date 2020/8/27 20:03
 * @remark
 */
@SpringBootConfiguration
public class APPWebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public OpenApiCheckInterceptor getOpenApiCheckInterceptor() {
        return new OpenApiCheckInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getOpenApiCheckInterceptor()).excludePathPatterns(new ArrayList<String>() {{
//            add("/**/**");
        }}).addPathPatterns("/api/**/**");
//        registry.addInterceptor(getOpenApiCheckInterceptor()).addPathPatterns("/api/open/**");
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/company/home");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.addViewControllers(registry);
//    }
}
