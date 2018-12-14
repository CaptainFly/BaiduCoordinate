package com.jxtii.oa.modules.scheduler.dao;

import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.scheduler.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:29:57
 */
@MyBatisDao
public interface ScheduleJobDao {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);


    List<ScheduleJobEntity> queryList(Map<String, Object> map);


    void save(ScheduleJobEntity t);

    void save(Map<String, Object> map);

    void saveBatch(List<ScheduleJobEntity> list);

    int update(ScheduleJobEntity t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    ScheduleJobEntity queryObject(Object id);


    List<ScheduleJobEntity> queryList(Object id);

    int queryTotal(Map<String, Object> map);

    int queryTotal();

    int updateByDeclareId(String id);
}
