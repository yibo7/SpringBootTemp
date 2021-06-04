package com.parvar.fullnode.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/*
获取网站被请求的相关参数
 */
@Slf4j
public class RequestPrams {

    private RequestPrams() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取网站根地址
     * @param request
     * @return
     */
    public static String getDomainAndPort(HttpServletRequest request){
        if (request == null) {
            return "";
        }
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    }
    /**
     * 获取全部url
     * @param request
     * @return
     */
    public static String getRequestURLAll(HttpServletRequest request){
        if (request == null) {
            return "";
        }
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath() + getRequestURL(request);
    }

    /**
     * 获取应用访问url
     * @param request
     * @return
     */
    public static String getRequestWebUrl(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
    }

    /**
     * 获取url
     * @param request
     * @return
     */
    public static String getRequestURL(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String url = "";
        url = request.getServletPath();

        if (!"".equals(request.getQueryString()) && request.getQueryString() != null) {
            url = url + "?" + request.getQueryString();
        }
        return url;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        try {
            String unknown = "unknown";
            //x-connecting-ip
            //String ip = request.getHeader("x-forwarded-for");
            String ip = request.getHeader("CF-Connecting-IP");//这是创宇clondfire转发头
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) { //这是创宇cdn转发头
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
        }catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置json返回数据
     * @param response
     * @return
     */
    public static void setJsonResponse(HttpServletResponse response, String jsonStr){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonStr);
        } catch (Exception e) {
            log.error("设置json返回数据失败:"+e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getBrowser(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent").toLowerCase();
        if(agent == null || agent.equals("")){
            return "other";
        }
        if(agent.indexOf("trident")>0){
            return "IE";
        }else if(agent.indexOf("msie")>0){
            return "IE";
        }else if(agent.indexOf("opera")>0){
            return "Opera";
        }else if(agent.indexOf("firefox")>0){
            return "Firefox";
        }else if(agent.indexOf("chrome")>0){
            return "Chrome";
        }else if(agent.indexOf("safari")>0){
            return "Safari";
        }else if(agent.indexOf("camino")>0){
            return "Camino";
        }else if(agent.indexOf("konqueror")>0){
            return "Konqueror";
        }

        return "other";
    }


    /**
     * 获取主站域名，用于发送邮件，短信
     *  根据访问主站域名自动选择，如没有request 则选择最新配置的地址
     * @param request
     * @param domainName
     * @return
     */
    public static String getDomainName(HttpServletRequest request,String domainName){
        String[] domainNames = domainName.split(",");
        //访问来源
        String origin = request==null?".":request.getHeader("Origin")!=null?request.getHeader("Origin"):".";
        //来源域名
        String extensionname = origin.substring(origin.lastIndexOf("."));
        if(extensionname.length()<3){
            domainName= domainNames[domainNames.length-1];
        }else {
            for (String url:domainNames){
                if(url.contains(extensionname)){
                    domainName= url;
                    break;
                }else {
                    domainName=domainNames[domainNames.length-1];

                }
            }
        }
        return domainName;
    }

}
