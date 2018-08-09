package com.yoogun.core.infrastructure;

/**
 * 对象修改事件基类
 * @param <T> 对象泛型
 * @author Liu Jun at 2018-4-23 09:46:05
 * @since v1.0.0
 */
public abstract class ObjectChangedEvent<T> {

    protected ChangeType changeType;

    protected T target;

    public ObjectChangedEvent(T target, ChangeType changeType) {
        this.changeType = changeType;
        this.target = target;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public T getTarget() {
        return target;
    }

    /**
     * 修改类型
     */
    public enum ChangeType {
        CREATE, MODIFY, REMOVE
    }
}
