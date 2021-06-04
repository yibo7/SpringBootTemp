package com.parvar.fullnode.apihelper;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用的API请求返回VO对象
 *
 * @param <T>
 */
@Data
public class ApiResult<T> implements Serializable {
    /**
     * 错误码，-1发生未知道错误(可查看错误日志找原因),0代表成功,大于于0代表有错误
     */
    private Integer code;
    /**
     * 返回的以信息
     */
    private String msg;
    /**
     * 返回的请求的具体对象
     */
    private T data;
}
