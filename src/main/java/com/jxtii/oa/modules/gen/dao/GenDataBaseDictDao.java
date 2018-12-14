/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.gen.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.gen.entity.GenTable;
import com.jxtii.oa.modules.gen.entity.GenTableColumn;

import java.util.List;

/**
 * 业务表字段DAO接口
 *
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

    /**
     * 查询表列表
     *
     * @param genTable
     * @return
     */
    List<GenTable> findTableList(GenTable genTable);

    /**
     * 获取数据表字段
     *
     * @param genTable
     * @return
     */
    List<GenTableColumn> findTableColumnList(GenTable genTable);

    /**
     * 获取数据表主键
     *
     * @param genTable
     * @return
     */
    List<String> findTablePK(GenTable genTable);

}
