package com.jxtii.oa.modules.scheduler.service;

import com.jxtii.oa.modules.scheduler.dao.ScheduleJobDao;
import com.jxtii.oa.modules.scheduler.entity.ScheduleJobEntity;
import com.jxtii.oa.modules.scheduler.utils.ScheduleStatus;
import com.jxtii.oa.modules.scheduler.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guolf on 17/4/28.
 */
@Service("scheduleJobService")
@Transactional(readOnly = true)
public class ScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobDao schedulerJobDao;

    @PostConstruct
    public void init() {
//        List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new HashMap<String, Object>());
//        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
//            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
//            //如果不存在，则创建
//            if (cronTrigger == null) {
//                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
//            } else {
//                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
//            }
//        }
    }

    public ScheduleJobEntity queryObject(Long jobId) {
        return schedulerJobDao.queryObject(jobId);
    }


    public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
        return schedulerJobDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return schedulerJobDao.queryTotal(map);
    }

    @Transactional(readOnly = false)
    public void save(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        schedulerJobDao.save(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }


    @Transactional(readOnly = false)
    public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        schedulerJobDao.update(scheduleJob);
    }

    @Transactional(readOnly = false)
    public void deleteBatch(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        schedulerJobDao.deleteBatch(jobIds);
    }

    @Transactional(readOnly = false)
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap();
        map.put("list", jobIds);
        map.put("status", status);
        return schedulerJobDao.updateBatch(map);
    }

    @Transactional
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Transactional
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

    @Transactional
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }

    @Transactional(readOnly = false)
    public void updateByDeclareId(String id) {
        schedulerJobDao.updateByDeclareId(id);
    }

}
