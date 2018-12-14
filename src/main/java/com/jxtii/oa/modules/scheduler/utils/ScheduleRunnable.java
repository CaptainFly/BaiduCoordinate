package com.jxtii.oa.modules.scheduler.utils;

import com.jxtii.oa.common.utils.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created by guolf on 17/4/28.
 * 执行定时任务
 */
public class ScheduleRunnable implements Runnable {

    private Logger logger = Logger.getLogger(getClass());

    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
