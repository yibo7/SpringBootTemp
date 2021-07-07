package com.ebsite.tempsite.exception;

import lombok.Data;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/10/10 15:11
 * @description：异常基类
 * @modified By：
 */
@Data
public abstract class EbExBase extends RuntimeException{
    /*错误码*/
    private Integer code;
    public EbExBase(){
    }

    public EbExBase(EResultEnum eResultEnum) {
        super(eResultEnum.getMsg());
        this.code = eResultEnum.getCode();
    }
    public EbExBase(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
