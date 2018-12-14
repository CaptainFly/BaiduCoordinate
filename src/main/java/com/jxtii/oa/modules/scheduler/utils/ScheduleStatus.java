package com.jxtii.oa.modules.scheduler.utils;

/**
 * Created by guolf on 17/4/28.
 */
public enum ScheduleStatus {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 暂停
     */
    PAUSE(1),

    /**
     * 已结束
     */
    COMPLETED(-1);

    private int value;

    private ScheduleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}