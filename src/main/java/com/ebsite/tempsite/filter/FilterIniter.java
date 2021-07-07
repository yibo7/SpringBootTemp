package com.ebsite.tempsite.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器入口
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class FilterIniter implements Filter {
    FilterConfig filterConfig = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//        long stat = System.currentTimeMillis();
        //清洗,防范XSS，SQL注入等攻击
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
//        chain.doFilter(request, response);
//        log.info("耗时{}",System.currentTimeMillis() - stat);
    }
}