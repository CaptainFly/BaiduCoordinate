/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.oa.service;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.service.CrudService;
import com.jxtii.oa.modules.oa.dao.OaNotifyDao;
import com.jxtii.oa.modules.oa.dao.OaNotifyRecordDao;
import com.jxtii.oa.modules.oa.entity.OaNotify;
import com.jxtii.oa.modules.oa.entity.OaNotifyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 通知通告Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyService extends CrudService<OaNotifyDao, OaNotify> {

    @Autowired
    private OaNotifyRecordDao oaNotifyRecordDao;

    public OaNotify get(String id) {
        OaNotify entity = dao.get(id);
        return entity;
    }

    /**
     * 获取通知发送记录
     *
     * @param oaNotify
     * @return
     */
    public OaNotify getRecordList(OaNotify oaNotify) {
        if (oaNotify.getType().equals("4")) {
            oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findScoreApproveList(new OaNotifyRecord(oaNotify)));
        } else {
            oaNotify.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(oaNotify)));
        }
        return oaNotify;
    }

    public Page<OaNotify> find(Page<OaNotify> page, OaNotify oaNotify) {
        oaNotify.setPage(page);
        page.setList(dao.findList(oaNotify));
        return page;
    }

    /**
     * 获取通知数目
     *
     * @param oaNotify
     * @return
     */
    public Long findCount(OaNotify oaNotify) {
        return dao.findCount(oaNotify);
    }

    @Transactional(readOnly = false)
    public void save(OaNotify oaNotify) {
        super.save(oaNotify);

        // 更新发送接受人记录
        oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
        if (oaNotify.getOaNotifyRecordList().size() > 0) {
            oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
        }
    }

    /**
     * 更新阅读状态
     */
    @Transactional(readOnly = false)
    public void updateReadFlag(OaNotify oaNotify) {
        OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
        oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
        oaNotifyRecord.setReadDate(new Date());
        oaNotifyRecord.setReadFlag("1");
        oaNotifyRecordDao.update(oaNotifyRecord);
    }
}