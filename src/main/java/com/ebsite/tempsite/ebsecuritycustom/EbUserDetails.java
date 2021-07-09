package com.ebsite.tempsite.ebsecuritycustom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

/**
 * 实现一个自己的EbUserDetails
 */
//@Data
public class EbUserDetails extends User {

    private static final long serialVersionUID = -1178189560698580643L;
    /**
     * 用户id
     */
    @Getter
    @Setter
    private Long userId;
    /**
     * 注册时间
     */
    @Getter
    @Setter
    private Date addTime;
    /**
     * mdwu
     */
    @Getter
    @Setter
    private String mdwu;
    /**
     * 用户邮箱
     */
    @Getter
    @Setter
    private String userEmail;
    /**
     * 用户手机号
     */
    @Getter
    @Setter
    private String mobileNumber;
    /**
     * 用户状态
     */
    @Getter
    @Setter
    private Integer userStatus;



    /**
     * 头像地址
     */
    @Getter
    @Setter
    private String icoUrl;
    /**
     * 交易密码
     */
    @Getter
    @Setter
    private String tradePwd;

    /**
     * 登录密码
     */
    @Getter
    @Setter
    private String loginPwd;

    /**
     * 用户账号
     */
    @Getter
    @Setter
    private String userName;




    public EbUserDetails(Long UserId, String username, String password, Collection<? extends GrantedAuthority> authorities)
    {
        super(username,password,authorities);
        userId = UserId;
    }
    public EbUserDetails(Long UserId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
         super( username,  password,  enabled,  accountNonExpired,  credentialsNonExpired,  accountNonLocked,  authorities);
        userId = UserId;
    }

//    private List<Integer> securityIds;
//
//    public boolean isHaveLimit(Integer limitid){
//        if(securityIds!=null){
//           return securityIds.contains(limitid);
//        }
//        return false;
//    }
}
