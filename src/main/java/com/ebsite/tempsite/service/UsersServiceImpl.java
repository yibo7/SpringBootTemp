package com.ebsite.tempsite.service;

import com.ebsite.tempsite.dao.UsersDao;
import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.UsersPojo;
import com.ebsite.tempsite.service.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersServiceImpl extends BaseServiceImpl<UsersPojo,Long> {

    //region 标准实现
    @Autowired
    private UsersDao dataDao;
    @Override
    public BaseDao<UsersPojo, Long> getDAO() {
        return dataDao;
    }
    //endregion

    public List<UsersPojo> findAllByRid(Integer rid){
        if(rid==null)
        return dataDao.findAll();

        return dataDao.findUsersByRoleId(rid);
    }

    public UsersPojo findUsersByUserName(String username){
        return dataDao.findUsersByUserName(username);
    }



}
