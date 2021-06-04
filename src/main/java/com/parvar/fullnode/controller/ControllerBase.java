package com.parvar.fullnode.controller;

import com.parvar.fullnode.configs.LocaleMessageService;
import com.parvar.fullnode.logopt.LogOpt;
import com.parvar.fullnode.settings.SiteSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ControllerBase {
    @Autowired
    protected LocaleMessageService langBll;
    @Autowired
    protected LogOpt logOpt;
    @Autowired
    protected SiteSetting siteSetting;

}
