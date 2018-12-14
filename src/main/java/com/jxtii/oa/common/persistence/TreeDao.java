/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.common.persistence;

import java.util.List;

/**
 * DAO支持类实现
 *
 * @param <T>
 * @author ThinkGem
 * @version 2014-05-16
 */
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

    /**
     * 找到所有子节点
     *
     * @param entity
     * @return
     */
    public List<T> findByParentIdsLike(T entity);

    /**
     * 更新所有父节点字段
     *
     * @param entity
     * @return
     */
    public int updateParentIds(T entity);

}