/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.cms.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.cms.entity.Link;

import java.util.List;

/**
 * 链接DAO接口
 *
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface LinkDao extends CrudDao<Link> {

    public List<Link> findByIdIn(String[] ids);
//	{
//		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
//	}

    public int updateExpiredWeight(Link link);
//	{
//		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
//	public List<Link> fjindListByEntity();
}
