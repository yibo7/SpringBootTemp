package com.ebsite.tempsite.service.base;

import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
@SuppressWarnings("unchecked")
public  abstract class BaseServiceImpl<T extends BaseModel<ID>, ID extends Serializable> implements BaseService<T, ID> {

    public void logInfo(String info){
        log.info(info);
    }
    public void logErr(String err){
        log.error(err);
    }
    public void logWarn(String warn){
        log.warn(warn);
    }
    public abstract BaseDao<T, ID> getDAO();

    @Override
    public T save(T t) {
        return getDAO().save(t);
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        return getDAO().saveAll(entities);
    }

    @Override
    public void delete(ID id) {
        T t = findOne(id);
        if (t == null) {
            return;
        }
        getDAO().delete(t);
    }

    @Override
    public void delete(T t) {
        getDAO().delete(t);
    }

    @Override
    public T findOne(ID id) {
        Optional<T> model =  getDAO().findById(id);

        return model.isPresent()?model.get():null;
    }

    @Override
    public T findOne(Map<String, Object> params) {
           Optional<T> model = getDAO().findOne(createListSpec(params));
        return model.isPresent()?model.get():null;
    }

    @Override
    public List<T> findAll() {
        return getDAO().findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getDAO().findAll(pageable);
    }

    @Override
    public List<T> list(final Map<String, Object> params) {
        List<T> list = getDAO().findAll(createListSpec(params));
        return list;
    }

    @Override
    public List<T> list(final Map<String, Object> params, Sort sort) {
        List<T> list = getDAO().findAll(createListSpec(params),sort);
        return list;
    }

    @Override
    public Page<T> list(final Map<String, Object> params, Pageable pageable) {
        Page<T> page = getDAO().findAll(createListSpec(params), pageable);
        return page;
    }

    @Override
    public boolean existsById(ID id) {
        return getDAO().existsById(id);
    }

    @Override
    public void deleteAll() {
        getDAO().deleteAll();
    }

    @Override
    public long count() {
        return getDAO().count();
    }

    @Override
    public long count(Map<String, Object> params) {
        return getDAO().count(createListSpec(params));
    }



    /**
     * 创建List查询条件
     */
    private Specification<T> createListSpec(final Map<String, Object> params){
        Specification<T> spec = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                for (Entry<String, Object> entry : params.entrySet()) {
                    Object value = entry.getValue();
                    if (value == null || StringUtils.isBlank(value.toString())) {
                        continue;
                    }
                    String key = entry.getKey();
                    String[] arr = key.split(":");
                    Predicate predicate = getPredicate(arr, value, root, cb);
                    list.add(predicate);
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return spec;
    }

    private Predicate getPredicate(String[] arr, Object value,
                                   Root<T> root, CriteriaBuilder cb) {
        if (arr.length == 1) {
            return cb.equal(root.get(arr[0]).as(value.getClass()), value);
        }
        if (QueryTypeEnum.like.name().equals(arr[1])) {
            return cb.like(root.get(arr[0]).as(String.class), String.format("%%%s%%", value));
        }
        if (QueryTypeEnum.ne.name().equals(arr[1])) {
            return cb.notEqual(root.get(arr[0]).as(value.getClass()), value);
        }
        if (QueryTypeEnum.lt.name().equals(arr[1])) {
            return getLessThanPredicate(arr, value, root, cb);
        }
        if (QueryTypeEnum.lte.name().equals(arr[1])) {
            return getLessThanOrEqualToPredicate(arr, value, root, cb);
        }
        if (QueryTypeEnum.gt.name().equals(arr[1])) {
            return getGreaterThanPredicate(arr, value, root, cb);
        }
        if (QueryTypeEnum.gte.name().equals(arr[1])) {
            return getGreaterThanOrEqualToPredicate(arr, value, root, cb);
        }
        if (QueryTypeEnum.equal.name().equals(arr[1])) {
            return cb.equal(root.get(arr[0]).as(value.getClass()), value);
        }
        if (QueryTypeEnum.in.name().equals(arr[1])) {
            CriteriaBuilder.In<Object> in = cb.in(root.get(arr[0]).as(value.getClass()));
            String[] split = value.toString().split(",");
            for (String s : split) {
                in.value(s);
            }
            return in;
        }
        throw new UnsupportedOperationException(String.format("不支持的查询类型[%s]", arr[1]));
    }

    private Predicate getLessThanPredicate(String[] arr, Object value,
                                           Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Integer.class), (int) value);
        }
        if (Long.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Long.class), (long) value);
        }
        if (Double.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Double.class), (double) value);
        }
        if (Float.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Float.class), (float) value);
        }
        if (Timestamp.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
        }
        if (Date.class == value.getClass()) {
            return cb.lessThan(root.get(arr[0]).as(Date.class), (Date) value);
        }
        return cb.lessThan(root.get(arr[0]), String.valueOf(value));
    }

    private Predicate getLessThanOrEqualToPredicate(String[] arr,
                                                    Object value, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int) value);
        }
        if (Long.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Long.class), (long) value);
        }
        if (Double.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Double.class), (double) value);
        }
        if (Float.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Float.class), (float) value);
        }
        if (Timestamp.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
        }
        if (Date.class == value.getClass()) {
            return cb.lessThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date) value);
        }
        return cb.lessThanOrEqualTo(root.get(arr[0]), String.valueOf(value));
    }

    private Predicate getGreaterThanPredicate(String[] arr,
                                              Object value, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Integer.class), (int) value);
        }
        if (Long.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Long.class), (long) value);
        }
        if (Double.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Double.class), (double) value);
        }
        if (Float.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Float.class), (float) value);
        }
        if (Timestamp.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
        }
        if (Date.class == value.getClass()) {
            return cb.greaterThan(root.get(arr[0]).as(Date.class), (Date) value);
        }
        return cb.greaterThan(root.get(arr[0]), String.valueOf(value));
    }

    private Predicate getGreaterThanOrEqualToPredicate(String[] arr, Object value,
                                                       Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Integer.class), (int) value);
        }
        if (Long.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Long.class), (long) value);
        }
        if (Double.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Double.class), (double) value);
        }
        if (Float.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Float.class), (float) value);
        }
        if (Timestamp.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Timestamp.class), (Timestamp) value);
        }
        if (Date.class == value.getClass()) {
            return cb.greaterThanOrEqualTo(root.get(arr[0]).as(Date.class), (Date) value);
        }
        return cb.greaterThanOrEqualTo(root.get(arr[0]), String.valueOf(value));
    }

    protected void copyModel(Object source, Object target){
        BeanUtils.copyProperties(source, target);
    }
}