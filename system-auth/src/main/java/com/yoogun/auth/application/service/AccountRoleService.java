package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.AccountRole;
import com.yoogun.auth.domain.model.Role;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.infrastructure.event.AccountRoleChangedEvent;
import com.yoogun.auth.repository.AccountDao;
import com.yoogun.auth.repository.RoleDao;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限-账户角色关系-业务层
 * @author Liu Jun at 2016-8-16 22:30:34
 * @version v1.0.0
 */
@Service
public class AccountRoleService extends BaseAuthService<AccountRole> {

	@Resource
	private AccountDao accountDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private ApplicationEventPublisher publisher;

	/**
	 * 获取角色下全部账户
	 * @param roleId 账户Id
	 * @return 角色下全部账户
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Account> searchAccountByRole(String roleId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_ACCOUNT A", "AUTH_ACCOUNT_ROLE B", "AUTH_ROLE C")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.ACCOUNT_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ROLE_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + roleId + "'");

		return accountDao.search(sql);
	}

	/**
	 * 获取账户全部角色
	 * @param accountId 账户Id
	 * @return 角色对象集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Role> searchRoleByAccount(String accountId) {
		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_ROLE A", "AUTH_ACCOUNT_ROLE B", "ACT_ID_USER C")
				.WHERE("A.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.ROLE_ID")
				.WHERE("B.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ACCOUNT_ID = C.ID")
				.WHERE("C.DELETED = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + accountId + "'");

		return roleDao.search(sql);
	}

	/**
	 * 设置账户的角色
	 * @param accountId		账户Id
	 * @param roleIds		角色Id集合
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void setAccountRole(String accountId, String[] roleIds) {
		List<String> removedRoleIdList = this.removeAllRole(accountId);	//先删除全部已绑定的角色

		List<String> newRoleIdList = Arrays.asList(roleIds);	//需要绑定的角色

		List<String> updateRoleIdList = new ArrayList<>();	//绑定时需要更新的角色
		List<String> createRoleIdList = new ArrayList<>();	//绑定时需要新增的角色

		newRoleIdList.forEach(newRoleId -> {	//saveOrUpdate分发
			if(removedRoleIdList.contains(newRoleId)) {
				updateRoleIdList.add(newRoleId);
			} else {
				createRoleIdList.add(newRoleId);
			}
		});

		this.updateAccountRole(accountId, updateRoleIdList);

		this.createAccountRole(accountId, createRoleIdList);

		Account account = new Account();
		account.setId(accountId);
		account.setRoles(roleIds);
		account.setTenantId(AuthCache.tenantId());
		publisher.publishEvent(new AccountRoleChangedEvent(account, AccountRoleChangedEvent.ChangeType.MODIFY));
	}

	/**
	 * 删除账户的全部角色
	 * @param accountId		账户Id
	 * @return 被删除的角色Id
	 */
	private List<String> removeAllRole(String accountId) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE("ACCOUNT_ID = '" + accountId + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		List<AccountRole> list = this.search(sql);

		List<String> removedRoleId = new ArrayList<>();

		list.forEach(accountRole -> {
			this.remove(accountRole);
			removedRoleId.add(accountRole.getRoleId());
		});

		return removedRoleId;
	}

	/**
	 * 恢复账户的角色
	 * @param accountId		账户Id
	 * @param roleIdList 待操作角色Id集合
	 */
	private void updateAccountRole(String accountId, List<String> roleIdList) {

		if(StringUtils.isBlank(accountId) || roleIdList.isEmpty()) {
			return;
		}

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		// 构造条件
		sql.WHERE("ACCOUNT_ID = '" + accountId + "'");
		sql.WHERE("ROLE_ID IN (" + EntityUtils.inSql(roleIdList) + ")");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<AccountRole> list = this.search(sql);

		list.forEach(accountRole -> {
			accountRole.setDeleted(false);
			this.modify(accountRole);
		});
	}

	/**
	 * 新增账户的角色
	 * @param accountId		账户Id
	 * @param roleIdList 待操作角色Id集合
	 */
	private void createAccountRole(String accountId, List<String> roleIdList) {
		if(StringUtils.isBlank(accountId) || roleIdList.isEmpty()) {
			return;
		}
		AccountRole accountRole;
		for(String roleId : roleIdList) {
			accountRole = new AccountRole();
			accountRole.setAccountId(accountId);
			accountRole.setRoleId(roleId);
			this.create(accountRole);
		}
	}
}