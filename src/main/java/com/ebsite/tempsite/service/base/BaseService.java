package com.ebsite.tempsite.service.base;

import com.ebsite.tempsite.pojo.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 公用业务接口
 *
 * @author 毛峰
 * @create 2018-11-30 17:28
 **/
public interface BaseService<T extends BaseModel<ID>, ID extends Serializable> {

    /**
     * 新增或更新
     */
    T save(T t);

    /**
     * 新增或更新
     * 注意数量不要太大，特别是数据迁移时不要使用该方法
     */
    Iterable<T> save(Iterable<T> entities);

    /**
     * 根据ID删除
     */
    void delete(ID id);

    /**
     * 根据实体删除
     */
    void delete(T t);

    /**
     * 根据ID查找对象
     */
    T findOne(ID id);

    /**
     * 根据条件查询对象
     *
     * 注：！！！使用此接口只返回一条数据，如有多个结果集则会报错！！！
     *
     * @param params {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
     * @return 查到一个返回对象，多个结果集则会报错，否则为null
     */
    T findOne(Map<String, Object> params);

    List<T> findAll();

    /**
     * 分页排序获取数据
     * 禁止使用该接口进行count操作
     * Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"id"));
     *
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 多条件查询
     * 注：多个条件间是and关系 & 参数是属性对应的类型 使用时注意避免结果集过大
     *
     * @param params {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
     * @return
     */
    List<T> list(Map<String, Object> params);

    /**
     * 多条件查询
     * 注：多个条件间是and关系 & 参数是属性对应的类型 使用时注意避免结果集过大
     *
     * @param params {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
     * @param sort 排序 例如: Sort sort = new Sort(Sort.Direction.DESC, "fid");
     * @return
     */
    List<T> list(Map<String, Object> params, Sort sort);

    /**
     * 分页多条件查询
     * 注：多个条件间是and关系 & 参数是属性对应的类型
     *
     * @param params   {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
     * @param pageable 分页信息 new PageRequest(page, size,new Sort(Direction.DESC, "updateTime"))
     * @return
     */
    Page<T> list(Map<String, Object> params, Pageable pageable);

    /**
     * 是否存在某个Id的数据
     * @param id
     * @return
     */
    boolean existsById(ID id);

    /**
     * 删除所有数据
     */
    void deleteAll();

    /**
     * 统计所有数据
     * @return
     */
    long count();
    /**
     * 统计某条件的数据
     * @return
     */
    long count(final Map<String, Object> params);

}
