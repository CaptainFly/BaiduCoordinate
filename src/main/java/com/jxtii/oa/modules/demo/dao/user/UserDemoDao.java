/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.demo.dao.user;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.demo.entity.user.UserDemo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户管理DAO接口
 * @author glf
 * @version 2017-07-07
 */
@MyBatisDao
public interface UserDemoDao extends CrudDao<UserDemo> {

    public UserDemo getByLoginName(String loginName);

    public UserDemo getByLoginNameAndName(@Param("loginName") String loginName,@Param("name") String name);
}