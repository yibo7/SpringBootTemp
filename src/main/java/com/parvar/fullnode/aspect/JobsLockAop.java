package com.parvar.fullnode.aspect;

import com.parvar.fullnode.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class JobsLockAop {
    private static final int TIMEOUT = 30 * 1000; //超时时间 10s
    @Autowired
    private RedisLock redisLock;

    @Around("execution(* com.parvar.fullnode.jobs.*Job.*(..))")
    public void handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        String sKeyName = pjp.getSignature().getName();
        //暂时关闭任务
        String lockKey = "EbJobsLockKey".concat(sKeyName);
        long lockTime = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(lockKey, String.valueOf(lockTime))) {
//            log.info("取不到锁不能执行!" + lockKey);
            return;
        }
        pjp.proceed();
        redisLock.unlock(lockKey, String.valueOf(lockTime));
    }
}
