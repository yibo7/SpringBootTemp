package com.ebsite.tempsite.pojo;

import java.io.Serializable;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/11/12 14:40
 * @description：实体基类
 * @modified By：
 */
public interface BaseModel<ID extends Serializable> extends Serializable {
    ID getDataId();
}
