/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.common.utils.excel.fieldtype;

import java.util.List;

import com.jxtii.oa.common.utils.SpringContextHolder;
import com.jxtii.oa.common.utils.StringUtils;
import com.jxtii.oa.modules.sys.entity.Office;
import com.jxtii.oa.modules.sys.service.OfficeService;
import com.jxtii.oa.modules.sys.utils.UserUtils;

/**
 * 机构
 * 字段类型转换
 * @author Fly
 * @version 2018-12-01
 */
public class OfficeType {
	
	
	private static OfficeService officeService = SpringContextHolder.getBean(OfficeService.class);
	

	
	/**
	 * 数据获取（模板）
	 * @param val
	 * @return
	 */
	public static List<Office> getDataList() {
		List <Office> office = officeService.findList(true);
		return office;
	}
	
	
	
    /**
     * 获取对象值（导入）
     */
    public static Object getValue(String val) {
        for (Office e : UserUtils.getOfficeList()) {
            if (StringUtils.trimToEmpty(val).equals(e.getName())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 设置对象值（导出）
     */
    public static String setValue(Object val) {
        if (val != null && ((Office) val).getName() != null) {
            return ((Office) val).getName();
        }
        return "";
    }
}
