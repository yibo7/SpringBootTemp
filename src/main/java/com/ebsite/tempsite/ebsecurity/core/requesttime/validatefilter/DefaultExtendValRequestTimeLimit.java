package com.ebsite.tempsite.ebsecurity.core.requesttime.validatefilter;

import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeConfig;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/11/12 13:29
 * @description：默认的ip限制过滤器
 * @modified By：
 */
//@Component  在外面实现ExtendValRequestTimeLimit后要将这个@Component
@Slf4j
public class DefaultExtendValRequestTimeLimit implements ExtendValRequestTimeLimit{
    @Override
    public boolean isHaveLimt(HttpServletRequest request, HttpServletResponse response, FilterChain chain, ValidateTimeConfig validateTimeConfig) {
        log.info("外部可以实现此方法来限制某个地址的请求IP");

        return true;
    }
}
