/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.dao;

import com.jxtii.oa.common.persistence.TreeDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.sys.entity.Office;

/**
 * 机构DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

}
