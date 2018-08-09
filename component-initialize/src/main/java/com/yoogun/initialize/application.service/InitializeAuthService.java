package com.yoogun.initialize.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.AccountRole;
import com.yoogun.auth.domain.model.Role;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.auth.repository.AccountDao;
import com.yoogun.auth.repository.AccountRoleDao;
import com.yoogun.auth.repository.RoleDao;
import com.yoogun.auth.repository.TenantDao;
import com.yoogun.initialize.infrastructure.SqlRecover;
import org.apache.shiro.authc.credential.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 初始化-权限-业务层
 * @author Liu Jun at 2017-12-7 09:12:39
 * @since v1.0.0
 */
@Service
public class InitializeAuthService extends InitializeService {

	private Logger logger = LoggerFactory.getLogger(InitializeAuthService.class);

	private static final String roleName = "管理员";

	@Resource
	private PasswordService passwordService;

	@Resource
	private TenantDao tenantDao;

	@Resource
	private AccountDao accountDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private AccountRoleDao accountRoleDao;

	/**
	 * 初始化方法，不使用父类的默认方法
	 * @param tenantId 租户ID
	 */
	@Override
	public void init(String tenantId) {
	}

	/**
	 * 初始化方法，使用自己的初始化方法
	 * @param tenantId 租户ID
	 * @param adminName 企业管理员账户名
	 */
	public void init(String tenantId, String adminName) {
		this.initTenant(tenantId);
		String roleId = this.initRole(tenantId);
		String accountId = this.initAccount(tenantId, adminName);	//初始化账户
		this.initAccountRole(tenantId, accountId, roleId);	//初始化角色
		this.initMenuAndButton(tenantId);	//初始化菜单和按钮
	}

	/**
	 * 初始化租户
	 * @param tenantId 租户ID
	 */
	private void initTenant(String tenantId) {
		Tenant tenant = new Tenant();
		tenant.setId(tenantId);
		tenant.setLevel(1);
		tenant.setName("新建企业");
		tenant.setEnabled(true);
		tenant.setDeleted(false);
		tenant.setLastModifyAccountId(PermissionService.superAdminId);
		tenant.setLastModifyTime(LocalDateTime.now());
		tenantDao.insert(tenant);
	}

	/**
	 * 初始化账户
	 * @param tenantId 租户ID
	 * @param adminName 企管账户名
	 */
	private String initAccount(String tenantId, String adminName) {
		Account account = new Account();
		account.setTenantId(tenantId);
		account.setIsAdmin(true);
		account.setName(adminName);
		account.setPassword(passwordService.encryptPassword(PermissionService.defaultPassword));
		account.setEnabled(true);
		account.setLocked(false);
		account.setLevel(0);
		account.setSalt(PermissionService.defaultSalt);
		account.setDeleted(false);
		account.setLastModifyAccountId(PermissionService.superAdminId);
		account.setLastModifyTime(LocalDateTime.now());
		accountDao.insert(account);
		return account.getId();
	}

	/**
	 * 初始化角色
	 * @param tenantId 租户ID
	 */
	private String initRole(String tenantId) {
		Role role = new Role();
		role.setTenantId(tenantId);
		role.setName(roleName);
		role.setEnabled(true);
		role.setIsSystem(true);
		role.setDeleted(false);
		role.setLastModifyAccountId(PermissionService.superAdminId);
		role.setLastModifyTime(LocalDateTime.now());
		roleDao.insert(role);
		return role.getId();
	}

	/**
	 * 初始化账户角色关系
	 * @param tenantId 租户ID
	 */
	private void initAccountRole(String tenantId, String accountId, String roleId) {
		AccountRole accountRole = new AccountRole();
		accountRole.setTenantId(tenantId);
		accountRole.setAccountId(accountId);
		accountRole.setRoleId(roleId);
		accountRole.setDeleted(false);
		accountRole.setLastModifyAccountId(accountId);
		accountRole.setLastModifyTime(LocalDateTime.now());
		accountRoleDao.insert(accountRole);
	}

	/**
	 * 初始化菜单和按钮
	 * @param tenantId 租户ID
	 */
	private void initMenuAndButton(String tenantId) {
		SqlRecover recover = this.buildRecover(tenantId);
		runSqlInScript(recover, "/auth/T_AUTH_MENU.sql", 2);
		runSqlInScript(recover, "/auth/T_AUTH_BUTTON.sql");
	}
}