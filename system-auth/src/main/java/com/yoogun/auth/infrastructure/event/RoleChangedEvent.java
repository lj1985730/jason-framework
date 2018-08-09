package com.yoogun.auth.infrastructure.event;

import com.yoogun.auth.domain.model.Role;
import com.yoogun.core.infrastructure.ObjectChangedEvent;

/**
 * 角色修改事件
 * @author Liu Jun at 2018-4-23 09:46:47
 * @since v1.0.0
 */
public class RoleChangedEvent extends ObjectChangedEvent<Role> {

    public RoleChangedEvent(Role object, ChangeType changeType) {
        super(object, changeType);
    }
}
