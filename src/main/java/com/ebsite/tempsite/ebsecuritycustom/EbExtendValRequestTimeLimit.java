package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.ebsecurity.core.requesttime.ValidateTimeConfig;
import com.ebsite.tempsite.ebsecurity.core.requesttime.validatefilter.ExtendValRequestTimeLimit;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定制自己的访问频率限制实现
 *
 * @author 蔡齐盛
 * @create 2018-07-06 15:06
 **/
@Component
public class EbExtendValRequestTimeLimit implements ExtendValRequestTimeLimit {
//    @Autowired
//    private CacheRedis cacheRedis;

    @Override
    public boolean isHaveLimt(HttpServletRequest request, HttpServletResponse response, FilterChain chain, ValidateTimeConfig validateTimeConfig) {
//        String limitKey =   request.getParameter("apikey"); //如果有公钥，限制公钥
//        if(StringUtils.isEmpty(limitKey)){  //如果没有公钥看看有没有登录，如果有登录就限制用户Id
////            String sUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//            if(SecurityContextHolder.getContext().getAuthentication()!=null){
//                String sUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//                if (!sUserName.equals("anonymousUser")) {
//                    limitKey = sUserName;
//                    //Object obj =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                    //EbUserDetails md = (EbUserDetails)obj;
//                }
//            }
//
//        }
//
//        if(StringUtils.isEmpty(limitKey)){  //最后都没有，就使用IP
//            limitKey = WebUtils.getIpAddress(request);
//        }
//
//        String configId =request.getRequestURI();// validateTimeConfig.getId();
//        String key = MD5Encrypt.MD5Encode("valtime".concat(limitKey).concat(configId.toLowerCase()));
//        String cacheClass = "validatetime";
//        String rz = cacheRedis.getStr(key,cacheClass);
//        if(StringUtils.isEmpty(rz)){
//            cacheRedis.addCacheItem(key,cacheClass,"1",validateTimeConfig.getTimespan(), TimeUnit.MILLISECONDS);
//        }
//        else{
//            return false;
//        }
        return true;
    }
}
