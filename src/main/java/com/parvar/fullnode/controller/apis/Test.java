package com.parvar.fullnode.controller.apis;

import com.parvar.fullnode.apihelper.ApiResult;
import com.parvar.fullnode.apihelper.ApiResultUtils;
import com.parvar.fullnode.controller.ControllerBase;
import com.parvar.fullnode.from.SiteFormDemo;
import com.parvar.fullnode.vo.SiteInfoVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/test/")
public class Test extends ControllerBase {
    @GetMapping("site")
    public ApiResult<SiteInfoVO> SiteInfo(){
        SiteInfoVO siteInfo = new SiteInfoVO();
        siteInfo.setSiteName(siteSetting.getSiteName());
        siteInfo.setSiteId(1);
        siteInfo.setSiteType(100);
        return ApiResultUtils.success(siteInfo);
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
