package com.yoogun.auth.infrastructure.event;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.AccountRole;
import com.yoogun.core.infrastructure.ObjectChangedEvent;

/**
 * 账户角色修改事件
 * @author Liu Jun at 2018-4-23 10:50:05
 * @since v1.0.0
 */
public class AccountRoleChangedEvent extends ObjectChangedEvent<Account> {

    public AccountRoleChangedEvent(Account target, ChangeType changeType) {
        super(target, changeType);
    }
}
