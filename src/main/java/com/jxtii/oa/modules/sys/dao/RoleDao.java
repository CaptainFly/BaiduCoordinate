/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.sys.entity.Role;

import java.util.List;

/**
 * 角色DAO接口
 *
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

    public Role getByName(Role role);

    public Role getByEnname(Role role);

    /**
     * 维护角色与菜单权限关系
     *
     * @param role
     * @return
     */
    public int deleteRoleMenu(Role role);

    public int insertRoleMenu(Role role);

    /**
     * 维护角色与公司部门关系
     *
     * @param role
     * @return
     */
    public int deleteRoleOffice(Role role);

    public int insertRoleOffice(Role role);

    List<Role> findRolesByUser(String id);

}
