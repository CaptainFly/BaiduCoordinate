package com.jxtii.oa.modules.scheduler.service;

import com.jxtii.oa.modules.scheduler.dao.ScheduleJobLogDao;
import com.jxtii.oa.modules.scheduler.entity.ScheduleJobLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Created by guolf on 17/4/28.
 */
@Service("scheduleJobLogService")
@Transactional(readOnly = true)
public class ScheduleJobLogService {

    @PostConstruct
    public void init() {
        System.out.println(" 项目初始化 ScheduleJobLogService  ");
    }


    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    @Transactional(readOnly = false)
    public void save(ScheduleJobLogEntity log) {
        scheduleJobLogDao.insert(log);
    }


}
