/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.demo.entity.user;

import com.jxtii.oa.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import com.jxtii.oa.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.jxtii.oa.common.persistence.DataEntity;

/**
 * 用户管理Entity
 * @author glf
 * @version 2017-07-07
 */
public class UserDemo extends DataEntity<UserDemo> {
	
	private static final long serialVersionUID = 1L;
	private Office company;		// 归属公司
	private Office office;		// 归属部门
	private String loginName;		// 登录名
	private String name;		// 姓名
	private String email;		// 邮箱
	private String mobile;		// 手机
	private String photo;		// 用户头像
	private String sex;		// 性别
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public UserDemo() {
		super();
	}

	public UserDemo(String id){
		super(id);
	}

	@ExcelField(title = "公司",sort = 3, type = 1)
	@NotNull(message="归属公司不能为空")
	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	@ExcelField(title = "部门",sort = 4, type = 1)
	@NotNull(message="归属部门不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@ExcelField(title = "登录名",sort = 1, type = 1)
	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@ExcelField(title = "姓名",sort = 2, type = 1)
	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title = "邮箱",sort = 10 , type = 1)
	@Length(min=0, max=200, message="邮箱长度必须介于 0 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="手机长度必须介于 0 和 200 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=1000, message="用户头像长度必须介于 0 和 1000 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}