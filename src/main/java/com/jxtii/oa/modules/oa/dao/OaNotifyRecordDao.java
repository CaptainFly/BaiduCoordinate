/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.oa.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.oa.entity.OaNotifyRecord;

import java.util.List;

/**
 * 通知通告记录DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

    /**
     * 插入通知记录
     *
     * @param oaNotifyRecordList
     * @return
     */
    public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);

    /**
     * 根据通知ID删除通知记录
     *
     * @param oaNotifyId 通知ID
     * @return
     */
    public int deleteByOaNotifyId(String oaNotifyId);

    List<OaNotifyRecord> findScoreApproveList(OaNotifyRecord oaNotifyRecord);

}