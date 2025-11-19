package com.xcore.libs.event;

/**
 * 一分钟更新一次在事件
 * <p>
 * author: created by 闹闹 on 2022/12/3
 * version: v1.0.0
 */
public class UpdateTimeEvent {

    private String tid;

    public UpdateTimeEvent(String tid) {
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
