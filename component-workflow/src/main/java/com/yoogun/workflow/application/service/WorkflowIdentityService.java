package com.yoogun.workflow.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Role;
import com.yoogun.auth.infrastructure.event.AccountRoleChangedEvent;
import com.yoogun.auth.infrastructure.event.RoleChangedEvent;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkflowIdentityService {

    @Resource
    private IdentityService identityService;

    /**
     * 角色数据修改监听
     * @param event 事件对象
     */
    @EventListener
    public void roleChangedListener(RoleChangedEvent event) {
        switch (event.getChangeType()) {
            case CREATE:
                this.createGroup(event.getTarget());
                break;
            case MODIFY:
                this.modifyGroup(event.getTarget());
                break;
            case REMOVE:
                this.removeGroup(event.getTarget());
                break;
        }
    }

    /**
     * 新增组
     * @param role 角色
     */
    private void createGroup(Role role) {
        Group group = identityService.newGroup(role.getId());
        group.setName(role.getName());
        group.setType(role.getTenantId());
        identityService.saveGroup(group);
    }

    /**
     * 修改组
     * @param role 角色
     */
    private void modifyGroup(Role role) {
        Group group = identityService.createGroupQuery()
                .groupId(role.getId())
                .groupType(role.getTenantId())
                .singleResult();
        if(group == null) {
            group = identityService.newGroup(role.getId());
            group.setType(role.getTenantId());
        }

        group.setName(role.getName());
        identityService.saveGroup(group);
    }

    /**
     * 删除组
     * @param role 角色
     */
    private void removeGroup(Role role) {
        identityService.deleteGroup(role.getId());
    }

    /**
     * 账户-角色数据修改监听
     * @param event 事件对象
     */
    @EventListener
    public void accountRoleChangedHandler(AccountRoleChangedEvent event) {
        Account account = event.getTarget();
        if(account == null) {
            throw new BusinessException("AccountRole同步工作流事件：数据为空！");
        }
        String accountId = account.getId();
        if(StringUtils.isBlank(accountId)) {
            throw new BusinessException("AccountRole同步工作流事件：账户ID为空！");
        }

        // 先查询出全部现有的membership
        List<Group> groups = identityService.createGroupQuery()
                .groupMember(accountId)
                .groupType(account.getTenantId()).list();
        // 删除全部现有的membership
        for(Group group : groups) {
            identityService.deleteMembership(accountId, group.getId());
        }

        //重新创建membership
        String[] roleIds = account.getRoles();
        for(String roleId : roleIds) {
            identityService.createMembership(accountId, roleId);
        }

    }
}
