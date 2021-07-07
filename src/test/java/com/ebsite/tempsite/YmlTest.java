package com.ebsite.tempsite;

import com.ebsite.tempsite.utils.encrypt.EbEncode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class YmlTest {
    @Value("${spring.mail.password}")
    private String emPassword; // 数据库密码
    @Test
    public void TestLog(){
            log.info("获取到的配置："+emPassword);
    }

    @Test
    public void TestEnCode(){
        String message = "ebsite";
        String value = EbEncode.encryptAes(message);
        log.info("加密值："+value);
    }
}
