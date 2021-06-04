package com.parvar.fullnode.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * 一个基于RestTemplate的实例
 *
 * @author 蔡齐盛
 * @create 2017-12-06 15:09
 **/
@Slf4j
public class RestClient {

    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        log.info("RestClient初始化完成");
    }

    private RestClient() {

    }

    /**
     *     被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。
     *     PostConstruct在构造函数之后执行,init()方法之前执行。
     *     PreDestroy（）方法在destroy()方法执行执行之后执行
     * @return
     */
    @PostConstruct
    public static RestTemplate getClient() {
        return restTemplate;
    }
}
