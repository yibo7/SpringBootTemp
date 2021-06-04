package com.parvar.fullnode.dao;

import com.parvar.fullnode.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//@Repository
public interface UsersDao extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

//    @Query(value = "select * from  menus   where  ParentID=?1 order by orderid asc", nativeQuery = true)
//     List<Menus> findMenusByParentID(Integer pid);

//    @Query(value = " select * from  menus   where MenuName LIKE  CONCAT('%',?1,'%') ", nativeQuery = true)
//    List<Menus> searchMenus(String key);

    List<Users> findAdminsByRoleId(Integer rid);
    Users findAdminsByUserName(String username);
}
