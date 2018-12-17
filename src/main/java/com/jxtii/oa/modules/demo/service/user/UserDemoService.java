/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.demo.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.service.CrudService;
import com.jxtii.oa.modules.demo.entity.user.UserDemo;
import com.jxtii.oa.modules.demo.dao.user.UserDemoDao;

/**
 * 用户管理Service
 * @author Fly
 * @version 2018-12-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class UserDemoService extends CrudService<UserDemoDao, UserDemo> {

	@Autowired
	private UserDemoDao demoDao;

	@Override
	public UserDemo get(String id) {
		return super.get(id);
	}

	/**
	 * 根据登录名查询用户
	 * @param loginName
	 * @return
	 */
	public UserDemo getByLoginName(String loginName){
		return demoDao.getByLoginName(loginName);
	}
	
	@Override
	public List<UserDemo> findList(UserDemo userDemo) {
		return super.findList(userDemo);
	}
	
	@Override
	public Page<UserDemo> findPage(Page<UserDemo> page, UserDemo userDemo) {
		return super.findPage(page, userDemo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void save(UserDemo userDemo) {
		super.save(userDemo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void delete(UserDemo userDemo) {
		super.delete(userDemo);
	}
	
}