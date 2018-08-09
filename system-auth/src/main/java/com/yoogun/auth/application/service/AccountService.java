package com.yoogun.auth.application.service;

import com.yoogun.auth.application.vo.RoleVo;
import com.yoogun.auth.domain.model.*;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.*;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.application.vo.TableParam;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限-账户-业务层
 * @author Liu Jun at 2016-7-31 14:55:07
 * @since v1.0.0
 */
@Service
public class AccountService extends BaseAuthService<Account> {

	@Resource
	private PasswordService passwordService;

	@Resource
	private AccountRoleService accountRoleService;

	@Resource
	private PermissionService permissionService;

	@Resource
	private TenantDao tenantDao;

	@Resource
	private AccountDao accountDao;

	@Resource
	private PersonDao personDao;

	@Resource
	private RoleDao roleDao;

	@Resource
	private DepartmentDao departmentDao;

	/**
	 * Table分页查询
	 * @param tableParam 查询参数
	 * @param hasAdmin 是否包含管理员
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Account> pageSearch(TableParam tableParam, boolean hasAdmin) {

		SQL sql = new SQL().SELECT("A.*, B.NAME AS PERSON_NAME").FROM(tableName + " A");
		sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");

		sql.LEFT_OUTER_JOIN("AUTH_PERSON B ON A.PERSON_ID = B.ID");

		if(StringUtils.isNotBlank(tableParam.getSearch())) {
			sql.AND();
			sql.WHERE("A.FIRST_ like '%" + tableParam.getSearch() + "%'");
			sql.OR();
			sql.WHERE("B.NAME like '%" + tableParam.getSearch() + "%'");
			sql.AND();
		}

		//区分是否查询管理员
		if(!hasAdmin) {
			sql.WHERE("A.IS_ADMIN = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		}

		//排序
		if(StringUtils.isNotBlank(tableParam.getSort()) && StringUtils.isNotBlank(tableParam.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(tableParam.getSort()) + " " + tableParam.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("A.IS_ADMIN ASC, A.FIRST_ ASC");
		}

		//由于驼峰无法转换前面或者或者后面带_的字段，例如（ID_）,自己写查询方法
		Page<Account> page = new Page<>(tableParam.getOffset(), tableParam.getLimit());
		List <Account> accounts = accountDao.pageSearch(sql,tableParam.getOffset(), tableParam.getLimit());

		for(Account account : accounts) {
			List<Role> roles = accountRoleService.searchRoleByAccount(account.getId());
			if(roles == null || roles.isEmpty()) {
				continue;
			}
			List<String> ids = EntityUtils.getAllId(roles);
			account.setRoles(ids.toArray(new String[ids.size()]));
		}

		Integer total = accountDao.countSearch(sql);
		page.setRows(accounts);	// 单页数据
		page.setTotal(total);	// 当前条件下的总数
		return page;
	}

	/**
	 * 根据名称获取账户
	 * @param name 账户名称
	 * @return 账户
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Account searchByName(String name) {
		//因为account的表字段和实体属性有一定偏差，因此可以自己在Dao中编写特殊的查询接口
		List<Account> accounts = accountDao.searchByName(name);
		if (accounts.size() == 0) {
			return null;
		} else {
			return accounts.get(0);
		}
	}

	/**
	 * 修改密码
	 * @param oldPass	提交的旧密码明文
	 * @param newPass	提交的新密码明文
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void modifyPassword(final String oldPass, final String newPass) {
		// 查询当前用户数据库中密码
		Object accountObj = AuthCache.get(AuthCache.LoginInfo.ACCOUNT);
		if(accountObj == null) {
			throw new UnknownAccountException("未知的账户！");
		}

		Account account = (Account)accountObj;
		String savedPass = account.getPassword();

		//提交的新旧密码加密
		String submitOldPass = passwordService.encryptPassword(oldPass);
		String submitNewPass = passwordService.encryptPassword(newPass);

		if (StringUtils.isBlank(savedPass) && !StringUtils.isBlank(submitOldPass)) {
			throw new BusinessException("修改密码失败，原密码输入错误！");
		} else if (savedPass != null && !passwordService.passwordsMatch(oldPass, savedPass)) {
			throw new BusinessException("修改密码失败，原密码输入错误！");
		} else { // 修改密码
			account.setPassword(submitNewPass);
			this.modify(account);
		}
	}

	/**
	 * 密码重置
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(String accountId) {
		Account account = this.searchById(accountId);
		String defaultPassword = PermissionService.defaultPassword;
		if(StringUtils.isBlank(defaultPassword)) {
			defaultPassword = "123123";
		}
		account.setPassword(passwordService.encryptPassword(defaultPassword));//初始化登录密码
		this.modify(account);
	}

	/**
	 *  锁定账户
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void lock(String accountId) {
		Account account = this.searchById(accountId);
		account.setLocked(true);
		this.modify(account);
	}

	/**
	 * 解锁账户
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void unlock(String accountId) {
		Account account = this.searchById(accountId);
		account.setLocked(false);
		this.modify(account);
	}

	/**
	 * 切换账户是否启用状态
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void toggleEnable(String accountId) {
		Account account = this.searchById(accountId);
		account.setEnabled(account.getEnabled() == null || !account.getEnabled());
		this.modify(account);
	}

	/**
	 * 启用账户
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void enable(String accountId) {
		Account account = this.searchById(accountId);
		account.setEnabled(true);
		this.modify(account);
	}

	/**
	 * 禁用账户
	 * @param accountId 账户ID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void disable(String accountId) {
		Account account = this.searchById(accountId);
		account.setEnabled(false);
		this.modify(account);
	}

	/**
	 * 检验人员是否注册过登录账户
	 * @param personId 人员名称
	 * @return true 注册过；false 未注册
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String searchAccountNamesByPerson(final String personId) {
		String sql = "SELECT FIRST_ AS NAME FROM " + tableName
				+ " WHERE " + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'"
				+ " AND TENANT_ID = '" + AuthCache.tenantId() + "'"
				+ " AND PERSON_ID = '" + personId + "'";

		List<Map<String, Object>> accountNameList = dao.sqlSearchNoMapped(sql);
		StringBuilder accountNames = new StringBuilder();
		for(Map<String, Object> name : accountNameList) {	//拼接账户名
			accountNames.append(name.get("NAME")).append("/");
		}
		return StringUtils.removeEnd(accountNames.toString(), "/");
	}
	
	/**
	 * 检验账户名是否重复<br/>
	 * 多租户环境下，账户名需要全局唯一
	 * @param name 待验证名称
	 * @param id 待验证主键（非必须）
	 * @return true 重复；false 不重复
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean nameExist(final String name, String id) {
		SQL sql = new SQL().SELECT("1").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("FIRST_ = '" + name + "'");
		if(StringUtils.isNotBlank(id)) {
			sql.WHERE("ID <> '" + id + "'");
		}
		return dao.countSearch(sql) > 0;
	}

	/**
	 * 缓存账户信息
	 * @param accountName 账户名称
	 */
	public void cacheAccountInfo(String accountName) {
		Account account = this.searchByName(accountName);	//查询账户
		this.cacheAccountInfo(account);
	}

	/**
	 * 设置超管信息
	 * @param tenantId 租户ID
	 */
	@CacheEvict(cacheNames = { EhCacheUtils.MENU_CACHE }, allEntries=true)
	public void updateAccountTenant(String tenantId) {
		if(StringUtils.isBlank(tenantId)) {
			throw new NullPointerException("请选择租户！");
		}
		Account account = AuthCache.account();	//读取缓存的账户
		if(account == null) {
			throw new AuthenticationException("用户未登录！");
		}
		if(!account.isSuperAdmin()) {
			throw new AuthenticationException("非超管禁止此操作！");
		}
		Tenant tenant = tenantDao.searchById(Tenant.class, tenantId);	//检索租户信息
		if(tenant == null) {
			throw new NullPointerException("数据库未找到此租户！");
		}
		AuthCache.put(AuthCache.LoginInfo.TENANT, tenant);//再次缓存账户
	}

	/**
	 * 缓存账户信息
	 * @param account 账户
	 */
	public void cacheAccountInfo(Account account) {

		//缓存账户
		AuthCache.put(AuthCache.LoginInfo.ACCOUNT, account);

		//缓存租户
		Tenant tenant = tenantDao.searchById(Tenant.class, account.getTenantId());
		AuthCache.put(AuthCache.LoginInfo.TENANT, tenant);
		Person person = account.getPerson();
		if(person == null) {
			person = personDao.searchById(Person.class, account.getPersonId());
		}
		if(person != null) {
			//缓存人员
			AuthCache.put(AuthCache.LoginInfo.PERSON, person);
			Department department = departmentDao.searchById(Department.class,person.getDepartmentId());
			if(department != null) {
				//缓存部门
				AuthCache.put(AuthCache.LoginInfo.DEPARTMENT, department);
			}
		}
		//数据和审核权限
		List<Account> dataPermission = permissionService.searchDataPermission(account.getId());
		AuthCache.put(AuthCache.LoginInfo.DATA_PERMISSION, dataPermission);
		List<Account> auditPermission = permissionService.searchAuditPermission(account.getId());
		AuthCache.put(AuthCache.LoginInfo.AUDIT_PERMISSION, auditPermission);
	}

	@Override
	protected void beforeCreate(Account entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录！");
		}

		if(this.nameExist(entity.getName(), entity.getId())) {
			throw new AuthenticationException("用户名“" + entity.getName() + "”已被使用！");
		}

		entity.setId_(entity.getId());	//保证activiti的主键一致
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());
		entity.setUpdateNum(0);

		String defaultPassword = PermissionService.defaultPassword;
		if(StringUtils.isBlank(defaultPassword)) {
			defaultPassword = "123123";
		}
		entity.setPassword(passwordService.encryptPassword(defaultPassword));//初始化登录密码
		entity.setEnabled(entity.getEnabled() == null ? true : entity.getEnabled());	//默认启用
		entity.setLocked(entity.getLocked() == null ? false : entity.getLocked());	//默认未锁定
		entity.setIsAdmin(false);
	}

	@Override
	protected void beforeModify(Account entity) {
		if(this.nameExist(entity.getName(), entity.getId())) {
			throw new AuthenticationException("用户名“" + entity.getName() + "”已被使用！");
		}
		entity.setUpdateNum(entity.getUpdateNum() + 1);
	}

	@Override
	protected void beforeRemove(Account entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());

		SQL sql = new SQL().SELECT("COUNT(1)").FROM("AUTH_ACCOUNT_ROLE");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("ACCOUNT_ID = '" + entity.getId() + "'");

		if (dao.countSearch(sql) > 0) {
			throw new BusinessException("删除失败,该用户在使用中");
		}
	}

	/**
	 *  查询可被选择的全部角色，在一页中显示
	 * @param vo 查询条件
	 * @return 可被选择的全部角色
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<Role> searchRoles(RoleVo vo) {
		SQL sql = new SQL().SELECT("*").FROM("AUTH_ROLE");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("ENABLED = '" + BooleanTypeHandler.BOOL_TRUE  + "'");	//启用状态

		//模糊查询，放在过滤条件最后，否则需要判定是否加and
		if(StringUtils.isNotBlank(vo.getSearch())) {
			sql.AND();
			sql.WHERE("NAME like '%" + vo.getSearch() + "%' OR CODE like '%" + vo.getSearch() + "%' " );
		}

		//排序
		if(StringUtils.isNotBlank(vo.getSort()) && StringUtils.isNotBlank(vo.getOrder())) {
			try {
				sql.ORDER_BY(this.entityClass.newInstance().getColName(vo.getSort()) + " " + vo.getOrder());
			} catch (Exception e) {
				throw new BusinessException("获取'" + this.entityClass + "'的实例出错！", e);
			}
		} else {	//默认排序
			sql.ORDER_BY("CODE ASC, NAME ASC");
		}

		//由于驼峰无法转换前面或者或者后面带_的字段，例如（ID_）,自己写查询方法
		Page<Role> page = new Page<>(0, 1000);
		List <Role> roles = roleDao.pageSearch(sql, 0, 1000);
		page.setRows(roles);	// 数据
		page.setTotal(roles.size());	// 当前条件下的总数
		return page;
	}

}