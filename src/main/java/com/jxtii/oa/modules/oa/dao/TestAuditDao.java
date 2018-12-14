/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.oa.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.oa.entity.TestAudit;

/**
 * 审批DAO接口
 *
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface TestAuditDao extends CrudDao<TestAudit> {

    public TestAudit getByProcInsId(String procInsId);

    public int updateInsId(TestAudit testAudit);

    public int updateHrText(TestAudit testAudit);

    public int updateLeadText(TestAudit testAudit);

    public int updateMainLeadText(TestAudit testAudit);

}
