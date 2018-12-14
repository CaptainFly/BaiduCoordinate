/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.cms.dao;

import com.jxtii.oa.common.persistence.CrudDao;
import com.jxtii.oa.common.persistence.annotation.MyBatisDao;
import com.jxtii.oa.modules.cms.entity.Article;
import com.jxtii.oa.modules.cms.entity.Category;

import java.util.List;

/**
 * 文章DAO接口
 *
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {

    public List<Article> findByIdIn(String[] ids);
//	{
//		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
//	}

    public int updateHitsAddOne(String id);
//	{
//		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
//	}

    public int updateExpiredWeight(Article article);

    public List<Category> findStats(Category category);
//	{
//		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}

}
