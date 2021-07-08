package com.ebsite.tempsite.controller.apis;

import com.ebsite.tempsite.aspect.weblogs.ControllerLog;
import com.ebsite.tempsite.aspect.weblogs.LogsTypeEnum;
import com.ebsite.tempsite.form.SiteFormDemo;
import com.ebsite.tempsite.vo.SiteInfoVO;
import com.ebsite.tempsite.apihelper.ApiResult;
import com.ebsite.tempsite.apihelper.ApiResultUtils;
import com.ebsite.tempsite.controller.ControllerBase;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/test/")
public class Test extends ControllerBase {
    @GetMapping("site")
    public ApiResult<SiteInfoVO> SiteInfo(){
        SiteInfoVO siteInfo = new SiteInfoVO();
        siteInfo.setSiteName(siteSetting.getSiteName());
        siteInfo.setSiteId(1);
        siteInfo.setSiteType(100);
        return ApiResultUtils.success(siteInfo);
    }
    @GetMapping("log")
    @ControllerLog(description = "调用测试日志的方法", logstype = LogsTypeEnum.LOGS_SETTING)
    public ApiResult<String> addLog(){
        return  ApiResultUtils.success("请求成功");
    }
    @PostMapping("site")
    public ApiResult<String> SiteInfo(@Valid SiteFormDemo site) {
        return ApiResultUtils.success("添加成功");
    }
    @GetMapping("str")
    @Cacheable(cacheNames = "testcache")
    public String test(){
        return "请求成功！";
    }
}
