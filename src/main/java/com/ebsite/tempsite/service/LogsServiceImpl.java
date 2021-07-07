package com.ebsite.tempsite.service;

import com.ebsite.tempsite.dao.LogsDao;
import com.ebsite.tempsite.dao.base.BaseDao;
import com.ebsite.tempsite.pojo.Logs;
import com.ebsite.tempsite.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl extends BaseServiceImpl<Logs, Integer> {

    //region 标准实现
    @Autowired
    private LogsDao dataDao;
    @Override
    public BaseDao<Logs, Integer> getDAO() {
        return dataDao;
    }

    //endregion


}
