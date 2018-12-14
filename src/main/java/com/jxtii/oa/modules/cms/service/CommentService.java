/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.cms.service;

import com.jxtii.oa.common.persistence.Page;
import com.jxtii.oa.common.service.CrudService;
import com.jxtii.oa.modules.cms.dao.CommentDao;
import com.jxtii.oa.modules.cms.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论Service
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentDao, Comment> {

    public Page<Comment> findPage(Page<Comment> page, Comment comment) {
//		DetachedCriteria dc = commentDao.createDetachedCriteria();
//		if (StringUtils.isNotBlank(comment.getContentId())){
//			dc.add(Restrictions.eq("contentId", comment.getContentId()));
//		}
//		if (StringUtils.isNotEmpty(comment.getTitle())){
//			dc.add(Restrictions.like("title", "%"+comment.getTitle()+"%"));
//		}
//		dc.add(Restrictions.eq(Comment.FIELD_DEL_FLAG, comment.getDelFlag()));
//		dc.addOrder(Order.desc("id"));
//		return commentDao.find(page, dc);
        comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));

        return super.findPage(page, comment);
    }

    @Transactional(readOnly = false)
    public void delete(Comment entity, Boolean isRe) {
        super.delete(entity);
    }
}
