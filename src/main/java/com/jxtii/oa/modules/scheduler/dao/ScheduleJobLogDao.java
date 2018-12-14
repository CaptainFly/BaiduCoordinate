package com.jxtii.oa.modules.scheduler.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.scheduler.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:30:02
 */
@MyBatisDao
public interface ScheduleJobLogDao extends CrudDao<ScheduleJobLogEntity> {

}
