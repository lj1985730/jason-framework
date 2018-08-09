package com.yoogun.workflow.infrastructure;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 流程状态枚举
 * @author Liu Jun at 2017-09-28 16:29:01
 * @since v1.0.0
 */
public enum ProcessState {
    NOT_BOUND("未绑定流程"),    //未绑定流程
    START("流程开始"),       //开始
    PENDING("审核中"),    //审核中
    WAITING("等待定时触发"),     //等待定时触发
    SUSPENDED("被挂起"),  //挂起
    REJECTED("被驳回"),    //被驳回
    RETRY("再次提交"),       //再次提交
    FINISHED("结束"),   //结束
    DELETED("已删除"),   //删除
    UNKNOWN("未知状态")     //未知状态
    ;


    private String text;

    ProcessState(String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
