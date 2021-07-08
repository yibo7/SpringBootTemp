package com.ebsite.tempsite.service;

import com.ebsite.tempsite.dao.LogsDao;
import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.LogsPojo;
import com.ebsite.tempsite.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl extends BaseServiceImpl<LogsPojo, Integer> {

    //region 标准实现
    @Autowired
    private LogsDao dataDao;
    @Override
    public BaseDao<LogsPojo, Integer> getDAO() {
        return dataDao;
    }

    //endregion


}
