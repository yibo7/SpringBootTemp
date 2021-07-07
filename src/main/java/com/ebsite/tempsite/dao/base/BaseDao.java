package com.ebsite.tempsite.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * dao的基类
 * @param <T> 与表对应的pojo类
 * @param <ID> 表的主键类型
 */
@NoRepositoryBean
public interface BaseDao<T, ID extends Serializable> extends JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

}
