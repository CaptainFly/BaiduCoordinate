/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.demo.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxtii.oa.common.utils.DateUtils;
import com.jxtii.oa.common.utils.excel.ExportExcel;
import com.jxtii.oa.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jxtii.oa.common.config.Global;
import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.web.BaseController;
import com.jxtii.oa.common.utils.StringUtils;
import com.jxtii.oa.modules.demo.entity.user.UserDemo;
import com.jxtii.oa.modules.demo.service.user.UserDemoService;

/**
 * 用户管理Controller
 * @author glf
 * @version 2017-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/demo/user/userDemo")
public class UserDemoController extends BaseController {

	@Autowired
	private UserDemoService userDemoService;
	
	@ModelAttribute
	public UserDemo get(@RequestParam(required=false) String id) {
		UserDemo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userDemoService.get(id);
		}
		if (entity == null){
			entity = new UserDemo();
		}
		return entity;
	}
	
	@RequiresPermissions("demo:user:userDemo:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserDemo userDemo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserDemo> page = userDemoService.findPage(new Page<UserDemo>(request, response), userDemo); 
		model.addAttribute("page", page);
		return "modules/demo/user/userDemoList";
	}

	@RequiresPermissions("demo:user:userDemo:view")
	@RequestMapping(value = "form")
	public String form(UserDemo userDemo, Model model) {
		model.addAttribute("userDemo", userDemo);
		return "modules/demo/user/userDemoForm";
	}

	@RequiresPermissions("demo:user:userDemo:edit")
	@RequestMapping(value = "save")
	public String save(UserDemo userDemo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userDemo)){
			return form(userDemo, model);
		}
		try {
			userDemoService.save(userDemo);
		}catch (Exception ex){
			if (ex instanceof DuplicateKeyException){
				addMessage(redirectAttributes, "登录名已存在");
				return "redirect:"+Global.getAdminPath()+"/demo/user/userDemo/?repage";
			}
		}
		addMessage(redirectAttributes, "保存用户成功");
		return "redirect:"+Global.getAdminPath()+"/demo/user/userDemo/?repage";
	}
	
	@RequiresPermissions("demo:user:userDemo:delete")
	@RequestMapping(value = "delete")
	public String delete(UserDemo userDemo, RedirectAttributes redirectAttributes) {
		userDemoService.delete(userDemo);
		addMessage(redirectAttributes, "删除用户成功");
		return "redirect:"+Global.getAdminPath()+"/demo/user/userDemo/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "exits")
	public  String exits(String loginName){
		UserDemo userDemo = userDemoService.getByLoginName(loginName);
		if(userDemo == null){
			return "true";
		}
		return "false";
	}


	@RequiresPermissions("demo:user:userDemo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(UserDemo user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<UserDemo> page = userDemoService.findPage(new Page<UserDemo>(request, response, -1), user);
			new ExportExcel("用户数据", UserDemo.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/demo/user/userDemoList?repage";
	}


}