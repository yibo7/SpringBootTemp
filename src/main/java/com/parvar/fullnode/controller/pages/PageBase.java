package com.parvar.fullnode.controller.pages;

import com.parvar.fullnode.controller.ControllerBase;

/**
 * @author ：蔡齐盛
 * @date ：Created in 2019/10/11 15:36
 * @description：界面控制器基类
 * @modified By：
 */
public class PageBase extends ControllerBase {

    protected int getPageSize(){
        return 25;
    }
    private String temRoot() {
        return "/";
    }
    protected String getUrl(String sUrlName){
        return  "/".concat(sUrlName);
    }
    protected String redirectUrl(String sUrlName){
        return  "redirect:".concat(getUrl(sUrlName));
    }

    protected String getTemPath(String temName){
        return temRoot().concat(temName);
    }
}
