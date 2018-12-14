/**
 * There are <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> code generation
 */
package com.jxtii.oa.modules.oa.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.oa.entity.Leave;

/**
 * 请假DAO接口
 *
 * @author liuj
 * @version 2013-8-23
 */
@MyBatisDao
public interface LeaveDao extends CrudDao<Leave> {

    /**
     * 更新流程实例ID
     *
     * @param leave
     * @return
     */
    public int updateProcessInstanceId(Leave leave);

    /**
     * 更新实际开始结束时间
     *
     * @param leave
     * @return
     */
    public int updateRealityTime(Leave leave);

}
