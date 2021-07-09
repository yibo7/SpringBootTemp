package com.ebsite.tempsite.ebsecuritycustom;

import com.ebsite.tempsite.pojo.UsersPojo;
import com.ebsite.tempsite.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
/**
 * 实现一个UserDetailsService，在用户提交登录的时候，可以通过用户名称，获取相关的用户数据
 */

@Component
public class EbUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersServiceImpl usersService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return  new EbUserDetails(1,"cqs","123456",AuthorityUtils.commaSeparatedStringToAuthorityList("allpermiss"));
        UsersPojo mdUser = usersService.findUsersByUserName(s);
        if(mdUser!=null) {
            EbUserDetails model = new EbUserDetails(
                    mdUser.getId(),
                    mdUser.getUserName(),
                    mdUser.getUserPass(),
                    mdUser.getUserStatus()>=1,//用户是否启用
                    true,//用户是否没过期
                    true,//用户密码是否没过期
                    true,//用户是否不冻结
                    AuthorityUtils.commaSeparatedStringToAuthorityList("allpermiss"));

            model.setAddTime(mdUser.getAddTime());
            model.setMobileNumber(mdUser.getMobileNumber());
            model.setUserEmail(mdUser.getUserEmail());
            model.setUserStatus(mdUser.getUserStatus());
            model.setMdwu(mdUser.getMdwu());
            model.setIcoUrl(null);
            if(model.getUserStatus()==0){
                throw new LoginException("用户被禁用!");
            }
            return model;
        }else{
            throw new LoginException("用户名或密码错误!");
        }
    }
}
