package com.ebsite.tempsite.dao;

import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.Logs;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsDao extends BaseDao<Logs, Integer> {

//    @Query(value = "select * from  menus   where  ParentID=?1 order by orderid asc", nativeQuery = true)
//     List<Menus> findMenusByParentID(Integer pid);

//    @Query(value = " select * from  menus   where MenuName LIKE  CONCAT('%',?1,'%') ", nativeQuery = true)
//    List<Menus> searchMenus(String key);

}
