/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.common.filter;

import com.jxtii.oa.common.utils.SpringContextHolder;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面高速缓存过滤器
 *
 * @author ThinkGem
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

    private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

    @Override
    protected CacheManager getCacheManager() {
        return cacheManager;
    }

}
