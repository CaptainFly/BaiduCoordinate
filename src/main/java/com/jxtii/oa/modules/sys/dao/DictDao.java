/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.sys.entity.Dict;

import java.util.List;

/**
 * 字典DAO接口
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

    public List<String> findTypeList(Dict dict);

}
