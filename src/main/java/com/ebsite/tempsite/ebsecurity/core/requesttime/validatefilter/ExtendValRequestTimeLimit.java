package com.ebsite.tempsite.ebsecurity.core.requesttime.validatefilter;


import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeConfig;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 扩展限制权限
 *
 * @author 蔡齐盛
 * @create 2018-07-06 14:55
 **/
public interface ExtendValRequestTimeLimit {
     boolean isHaveLimt(HttpServletRequest request, HttpServletResponse response, FilterChain chain, ValidateTimeConfig validateTimeConfig);
}
