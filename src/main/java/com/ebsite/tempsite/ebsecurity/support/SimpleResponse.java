package com.ebsite.tempsite.ebsecurity.support;

import lombok.Data;

@Data
public class SimpleResponse
{
    private Object content;
    private Integer code;

    public SimpleResponse(Object content,Integer code){
        this.content = content;
        this.code =code;
    }

}
