package com.ebsite.tempsite.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Jay Yan
 * @version 1.0
 * @date 2020/8/14 10:38
 * @remark
 */
public class WebUtil {
        private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

        private WebUtil() {
            throw new IllegalStateException("Utility class");
        }

        public static String getDomainAndPort(HttpServletRequest request) {
            return request == null ? "" : request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }

        public static String getRequestURLAll(HttpServletRequest request) {
            return request == null ? "" : request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + getRequestURL(request);
        }

        public static String getRequestWebUrl(HttpServletRequest request) {
            return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }

        public static String getRequestURL(HttpServletRequest request) {
            if (request == null) {
                return "";
            } else {
                String url = "";
                url = request.getServletPath();
                if (!"".equals(request.getQueryString()) && request.getQueryString() != null) {
                    url = url + "?" + request.getQueryString();
                }

                return url;
            }
        }

    /**
     * 只获取一个ip
     * @param request
     * @return
     */
    public static String getNoneIpAddress(HttpServletRequest request) {

        String ipAddress = getIpAddress(request);
        if(ipAddress != null && ipAddress != ""){
            String[] split = ipAddress.split(",");
            if(split.length >= 2){
                ipAddress = split[0];
            }
        }

        return ipAddress;
    }

    /**
     * 获取ip,可能会有多外现象
     * @param request
     * @return
     */
        public static String getIpAddress(HttpServletRequest request) {
            try {
                String unknown = "unknown";
                String ip = request.getHeader("CF-Connecting-IP");
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("x-connecting-ip");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("x-forwarded-for");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }

                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }

                return ip;
            } catch (Exception var3) {
                return "";
            }
        }

        public static void setJsonResponse(HttpServletResponse response, String jsonStr) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;

            try {
                out = response.getWriter();
                out.append(jsonStr);
            } catch (Exception var7) {
                log.error("设置json返回数据失败:" + var7.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }

            }

        }
}
