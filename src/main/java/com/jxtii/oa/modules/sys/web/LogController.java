/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.web;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.web.BaseController;
import com.jxtii.oa.modules.sys.entity.Log;
import com.jxtii.oa.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志Controller
 *
 * @author ThinkGem
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = {"list", ""})
    public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log);
        model.addAttribute("page", page);
        return "modules/sys/logList";
    }

}
