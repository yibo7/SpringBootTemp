package com.ebsite.tempsite.controller;

import com.ebsite.tempsite.configs.LocaleMessageService;
import com.ebsite.tempsite.logopt.LogOpt;
import com.ebsite.tempsite.settings.SiteSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
public class ControllerBase {
    @Autowired
    protected LocaleMessageService langBll;
    @Autowired
    protected LogOpt logOpt;
    @Autowired
    protected SiteSetting siteSetting;

}
