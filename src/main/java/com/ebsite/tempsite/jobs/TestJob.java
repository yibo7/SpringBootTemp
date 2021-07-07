package com.ebsite.tempsite.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试演示任务,上线后可以去掉
 */
@Slf4j
@Component
public class TestJob {
    @Scheduled(cron = "*/10 * * * * ?")
    public void addAddress() {
        try {
            log.info("测试任务已执行...");
        } catch (Exception e) {
            log.error("测试任务已执行出现异常");
        }
    }
}
