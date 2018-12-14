package com.jxtii.oa.modules.scheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by guolf on 17/4/28.
 */
@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());


    public void test(String params) {
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE,2);
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("0").append(" ")
                .append(calendar.get(Calendar.MINUTE)).append(" ")
                .append(calendar.get(Calendar.HOUR_OF_DAY)).append(" ")
                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ")
                .append(calendar.get(Calendar.MONTH) + 1).append(" ?");
        System.out.println("stringBuffer.toString() = " + stringBuffer.toString());

        System.out.println("calendar = " + calendar.get(Calendar.MONTH));

    }

    public void test2() {
        logger.info("我是不带参数的test2方法，正在被执行");
    }
}