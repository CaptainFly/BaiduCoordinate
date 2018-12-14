/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.gen.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 *
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {

    public void deleteByGenTableId(String genTableId);
}
