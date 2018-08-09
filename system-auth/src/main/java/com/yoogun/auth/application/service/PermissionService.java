package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Button;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.AccountDao;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.Reflections;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 权限-权限-业务层
 * @author Liu Jun at 2017-11-8 17:18:57
 * @version v1.0.0
 */
@Service
@Lazy(false)
public class PermissionService extends BaseAuthService<Account> implements InitializingBean {

	@Resource
	private AccountDao accountDao;

	@Resource
	private MenuService menuService;

	@Resource
	private ButtonService buttonService;

	@Value("#{authInitializeProperties['SUPER_TENANT_ID_KEY']}")
	public String SUPER_TENANT_ID_KEY;	//用友企业ID

	@Value("#{authInitializeProperties['SUPER_ADMIN_ID_KEY']}")
	public String SUPER_ADMIN_ID_KEY;	//超管账户ID

	@Value("#{authInitializeProperties['DEFAULT_PASSWORD_KEY']}")
	public String DEFAULT_PASSWORD_KEY;	//默认密码

	@Value("#{authInitializeProperties['DEFAULT_SALT_KEY']}")
	public String DEFAULT_SALT_KEY;	//默认盐值

	/**
	 * 管理企业ID
	 */
	public static String superTenantId;

	/**
	 * 超管ID
	 */
	public static String superAdminId;

	/**
	 * 默认密码
	 */
	public static String defaultPassword ;

	/**
	 * 默认公盐
	 */
	public static String defaultSalt;

	/**
	 * 获取账户全部权限
	 * @param account 账户
	 * @return 全部权限
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Set<String> searchAllPermissions(Account account) {
		Set<String> permissions = this.searchAllMenuPermissions(account);
		Set<String> buttonPermissions = this.searchAllButtonPermissions(account);
		if(permissions == null || permissions.isEmpty()) {
			return buttonPermissions;
		}
		if(buttonPermissions != null && !buttonPermissions.isEmpty()) {
			permissions.addAll(buttonPermissions);
		}
		return permissions;
	}

	/**
	 * 获取账户全部菜单权限
	 * @param account 账户
	 * @return 全部菜单权限
	 */
	private Set<String> searchAllMenuPermissions(Account account) {

		List<Menu> menuList;
		if(account.getIsAdmin()) {	//管理员可查看全部菜单
			menuList = menuService.searchAll();
		} else {
			//获取权限下菜单
			menuList = menuService.searchPermissionMenu(account.getId());
		}

		if(menuList == null || menuList.isEmpty()) {
			return null;
		}
		//循环取出权限字符
		Set<String> permissions = new HashSet<>();
		for (Menu menu : menuList) {
			if(StringUtils.isNotBlank(menu.getPermission())) {
				permissions.add(menu.getPermission());
			}
		}
		return permissions;
	}

	/**
	 * 获取账户全部按钮权限
	 * @param account 账户
	 * @return 全部按钮权限
	 */
	private Set<String> searchAllButtonPermissions(Account account) {

		List<Button> buttonList;
		if(account.getIsAdmin()) {	//管理员可查看全部菜单
			buttonList = buttonService.searchAll();
		} else {
			//获取权限下菜单
			buttonList = buttonService.searchPermissionButtons(account.getId());
		}
		if(buttonList == null || buttonList.isEmpty()) {
			return null;
		}
		Set<String> permissions = new HashSet<>();
		//循环取出权限字符
		for (Button button : buttonList) {
			if(StringUtils.isNotBlank(button.getPermission())) {
				permissions.add(button.getPermission());
			}
		}
		return permissions;
	}

	/**
	 *  <p>获取数据权限
	 *  <p>获取逻辑：当前Account -> 当前Role -> 下级Role -> 下级Account
	 * @param accountId 账户Id
	 * @return 数据权限下可控的账户集合
	 */
	public List<Account> searchDataPermission(String accountId) {
		return this.searchDataOrAuditPermission(accountId, 1);
	}

	/**
	 *  <p>获取审核权限
	 *  <p>获取逻辑：当前Account -> 当前Role -> 下级Role -> 下级Account
	 * @param accountId 账户Id
	 * @return 数据权限下可控的账户集合
	 */
	public List<Account> searchAuditPermission(String accountId) {
		return this.searchDataOrAuditPermission(accountId, 2);
	}

	/**
	 *  <p>获取数据或者审核权限
	 *  <p>获取逻辑：当前Account -> 当前Role -> 下级Role -> 下级Account
	 * @param accountId 账户Id
	 * @param flag 1 数据权限；2 审核权限
	 * @return 数据或者审核权限下可控的账户Id集合
	 */
	private List<Account> searchDataOrAuditPermission(String accountId, Integer flag) {
		if(StringUtils.isBlank(accountId) || flag == null) {
			return null;
		}
		Object tableNameObj = Reflections.readClassAnnotationProp(Account.class, Table.class, "name");
		if(tableNameObj == null) {
			throw new NullPointerException("未检索到账户表名，请核实Account.class的@Table注解是否配置正确！");
		}
		SQL sql = new SQL().SELECT_DISTINCT("ACCOUNT.ID").FROM(tableNameObj.toString() + " ACCOUNT");
		// 账户角色关系
		sql.INNER_JOIN("AUTH_ACCOUNT_ROLE ACCOUNT_ROLE ON ACCOUNT_ROLE.ACCOUNT_ID = ACCOUNT.ID " +
				"AND ACCOUNT_ROLE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND ACCOUNT_ROLE.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 角色
		sql.INNER_JOIN("AUTH_ROLE ROLE ON ROLE.ID = ACCOUNT_ROLE.ROLE_ID " +
				"AND ROLE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND ROLE.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 角色上下级关系
		sql.INNER_JOIN("AUTH_ROLE_RLAT ROLE_RLAT ON ROLE_RLAT.SUBORDINATE_ID = ROLE.ID " +
				"AND ROLE_RLAT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND ROLE_RLAT.TENANT_ID = '" + AuthCache.tenantId() + "' " +
				"AND ROLE_RLAT." + (flag == 1 ? "DATA_PERMISSION" : "AUDIT_PERMISSION") + " = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		// 上级角色
		sql.INNER_JOIN("AUTH_ROLE SUPERIOR ON SUPERIOR.ID = ROLE_RLAT.SUPERIOR_ID " +
				"AND SUPERIOR." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND SUPERIOR.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 上级账户角色关系
		sql.INNER_JOIN("AUTH_ACCOUNT_ROLE SUPERIOR_ACCOUNT_ROLE ON SUPERIOR_ACCOUNT_ROLE.ROLE_ID = SUPERIOR.ID " +
				"AND SUPERIOR_ACCOUNT_ROLE." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND SUPERIOR_ACCOUNT_ROLE.TENANT_ID = '" + AuthCache.tenantId() + "'");
		// 上级账户
		sql.INNER_JOIN(tableNameObj.toString() + " SUPERIOR_ACCOUNT ON SUPERIOR_ACCOUNT.ID = SUPERIOR_ACCOUNT_ROLE.ACCOUNT_ID " +
				"AND SUPERIOR_ACCOUNT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND SUPERIOR_ACCOUNT.TENANT_ID = '" + AuthCache.tenantId() + "' " +
				"AND SUPERIOR_ACCOUNT.ID = '" + accountId + "'");

		sql.WHERE("ACCOUNT." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "' " +
				"AND ACCOUNT.TENANT_ID = '" + AuthCache.tenantId() + "'");
		return accountDao.search(sql);
	}

	/**
	 *  启动加载相关系统参数
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.loadCoreConfigFromSysConfig();
	}

	/**
	 * 查询系统配置表中存储的默认密码
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void loadCoreConfigFromSysConfig() {
		superTenantId = this.loadSysConfig(SUPER_TENANT_ID_KEY);
		superAdminId = this.loadSysConfig(SUPER_ADMIN_ID_KEY);
		defaultPassword = this.loadSysConfig(DEFAULT_PASSWORD_KEY);
		defaultSalt = this.loadSysConfig(DEFAULT_SALT_KEY);
	}

	/**
	 *  加载系统核心配置
	 * @param key 配置KEY
	 * @return 配置值
	 */
	private String loadSysConfig(String key) {
		SQL sql = new SQL()
				.SELECT("CFG_VALUE")
				.FROM("SYS_CONFIG")
				.WHERE(BaseEntity.DELETE_PARAM  + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		if(StringUtils.isNotBlank(key)) {
			sql.WHERE("CFG_KEY = '" + key + "'");
		}
		List<Map<String, Object>> sysConfigs = dao.sqlSearchNoMapped(sql.toString());
		if(sysConfigs == null || sysConfigs.isEmpty()) {
			return null;
		}
		Map<String, Object> sysConfig = sysConfigs.get(0);
		if(!sysConfig.containsKey("CFG_VALUE")) {
			return null;
		}
		return sysConfig.get("CFG_VALUE") == null ? null : sysConfig.get("CFG_VALUE").toString();
	}
}