/**
 * Copyright &copy; 2012-2016 <a href="http://www.jxtii.com/">江西电信信息产业有限公司</a> All rights reserved.
 */
package com.jxtii.oa.modules.act.utils;

import java.util.Date;

/**
 * 属性数据类型
 *
 * @author ThinkGem
 * @version 2013-11-03
 */
public enum PropertyType {

    S(String.class),
    I(Integer.class),
    L(Long.class),
    F(Float.class),
    N(Double.class),
    D(Date.class),
    SD(java.sql.Date.class),
    B(Boolean.class);

    private Class<?> clazz;

    private PropertyType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getValue() {
        return clazz;
    }
}