/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.cms.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 *
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}
