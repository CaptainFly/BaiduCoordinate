/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.oa.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {

    /**
     * 获取通知数目
     *
     * @param oaNotify
     * @return
     */
    public Long findCount(OaNotify oaNotify);

}