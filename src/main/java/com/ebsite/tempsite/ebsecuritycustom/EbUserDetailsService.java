package com.ebsite.tempsite.ebsecuritycustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import payvar.openapi.pojo.tables.BusinessUser;
import payvar.openapi.service.BusinessUserService;

/**
 * 实现一个UserDetailsService，在用户提交登录的时候，可以通过用户名称，获取相关的用户数据
 */

@Component
public class EbUserDetailsService implements UserDetailsService {

    @Autowired
    private BusinessUserService businessUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return  new EbUserDetails(1,"cqs","123456",AuthorityUtils.commaSeparatedStringToAuthorityList("allpermiss"));
        BusinessUser mdUser = businessUserService.getByUseraccount(s);
        if(mdUser!=null) {
            EbUserDetails model = new EbUserDetails(
                    mdUser.getId(),
                    mdUser.getUseraccount(),
                    mdUser.getUserpassword(),
                    mdUser.getStatus()==1,//用户是否启用
                    true,//用户是否没过期
                    true,//用户密码是否没过期
                    true,//用户是否不冻结
                    AuthorityUtils.commaSeparatedStringToAuthorityList("allpermiss"));

            model.setUserId(mdUser.getId());
            model.setAddTime(mdUser.getAddtime());
            model.setMobileNumber(mdUser.getTelphone());
            model.setUserEmail(mdUser.getEmail());
            model.setUserStatus(mdUser.getStatus());
            model.setMdwu(mdUser.getMdwu());
            model.setUserGid(mdUser.getUserid());
            model.setCompanyName(mdUser.getCompanyname());
            model.setIcoUrl(null);
            model.setTradePwd(mdUser.getTradePwd());
            model.setLoginPwd(mdUser.getUserpassword());
            model.setUserAccount(mdUser.getUseraccount());
            if(model.getUserStatus()==0){
                throw new LoginException("用户被禁用!");
            }

            return model;
        }else{
            throw new LoginException("用户名或密码错误!");
        }
    }
}
