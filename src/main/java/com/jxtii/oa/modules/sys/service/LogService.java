/**
 * Copyright &copy; 2012-2013 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.sys.service;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.service.CrudService;
import com.jxtii.oa.common.utils.DateUtils;
import com.jxtii.oa.modules.sys.dao.LogDao;
import com.jxtii.oa.modules.sys.entity.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志Service
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class LogService extends CrudService<LogDao, Log> {

	@Override
    public Page<Log> findPage(Page<Log> page, Log log) {

        // 设置默认时间范围，默认当前月
        if (log.getBeginDate() == null) {
            log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (log.getEndDate() == null) {
            log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
        }

        return super.findPage(page, log);

    }

}
