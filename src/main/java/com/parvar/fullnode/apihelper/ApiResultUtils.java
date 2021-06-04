package com.parvar.fullnode.apihelper;

import com.parvar.fullnode.StartRun;
import com.parvar.fullnode.exception.EResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResultUtils {

    public static ApiResult success(Object data){
        ApiResult rz = new ApiResult();
        rz.setCode(EResultEnum.SUCCESS.getCode());
        rz.setMsg(EResultEnum.SUCCESS.getMsg());
        rz.setData(data);
        return rz;
    }
    public static ApiResult success(){
        return success(null);
    }

    public static ApiResult error(EResultEnum er){

        return error(er.getCode(),er.getMsg());
    }
    public static ApiResult error(Integer code,String msg){
        ApiResult rz = new ApiResult();
        rz.setCode(code);
        rz.setMsg(msg);
        return rz;
    }
     
}
