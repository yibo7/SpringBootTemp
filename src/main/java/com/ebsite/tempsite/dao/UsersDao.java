package com.ebsite.tempsite.dao;

import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.UsersPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDao extends BaseDao<UsersPojo,Long> {

    List<UsersPojo> findUsersByRoleId(Integer rid);
    UsersPojo findUsersByUserName(String username);
}
