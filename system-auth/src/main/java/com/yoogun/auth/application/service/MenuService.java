package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.utils.infrastructure.EhCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限-菜单-业务层
 * @author Liu Jun
 * @version 2016-7-31 14:55:07
 */
@Service
@CacheConfig(cacheNames = { EhCacheUtils.MENU_CACHE })
public class MenuService extends BaseAuthTreeService<Menu> {

	/**
	 * 根据用户权限获取系统菜单(前台)——非管理员登录时加载菜单使用<br />
	 * 关系链路Account -> AccountRole -> Role -> RoleMenu -> Menu
	 * @param accountId 用户Id
	 * @return	用户菜单
	 */
	@Cacheable(key = "'permissionMenu$' + #accountId")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> searchPermissionMenu(String accountId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM(tableName + " A", "AUTH_ROLE_MENU B",
						"AUTH_ROLE C", "AUTH_ACCOUNT_ROLE D",
						"ACT_ID_USER E")
				.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("A.ID = B.MENU_ID")
				.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ROLE_ID = C.ID")
				.WHERE("C." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = D.ROLE_ID")
				.WHERE("D." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("D.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("D.ACCOUNT_ID = E.ID")
				.WHERE("E." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("E.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("E.ID = '" + accountId + "'")
				.ORDER_BY("A.SORT_NUMBER ASC");
		return this.search(sql);
	}
	
	/**
	 * 获取企业下全部前台菜单-管理员登录前台时加载菜单使用
	 * @param systemId 系统ID
	 * @return 全部前台菜单
	 */
	@Cacheable(key = "'allFrontMenu$' + #accountId + '$' + #systemId")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> searchAllFrontMenu(String accountId, String systemId) {
		return searchAllMenu(systemId, true);
	}
	
	/**
	 * 获取企业下全部后台菜单-超级管理员登录后台时加载菜单使用
	 * @param systemId 系统ID
	 * @return 全部后台菜单
	 */
	@Cacheable(key = "'backMenu$' + #systemId")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> searchAllBackMenu(String systemId) {
		return searchAllMenu(systemId, false);
	}
	
	/**
	 * 获取系统下全部菜单
	 * @param systemId 系统ID
	 * @param isPublic	true 前台；false 后台
	 * @return 全部企业菜单
	 */
	private List<Menu> searchAllMenu(String systemId, Boolean isPublic) {

		SQL sql = new SQL();
		sql.SELECT("A.*").FROM(tableName + " A");

		if(StringUtils.isNotBlank(systemId)) {
			sql.FROM("AUTH_SYSTEM_MENU B");
		}
		sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("A.ENABLED = '" + BooleanTypeHandler.BOOL_TRUE + "'");

		if(isPublic) {	//前台或后台
			sql.WHERE("A.IS_PUBLIC IN (0, 1)");
		} else {
			sql.WHERE("A.IS_PUBLIC IN (0, 2)");
		}
		if(StringUtils.isNotBlank(systemId)) {
			sql.WHERE("A.ID = B.MENU_ID")
					.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
					.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
					.WHERE("B.SYSTEM_ID = '" + systemId + "'");
		}
		sql.ORDER_BY("A.SORT_NUMBER ASC");

		return this.search(sql);
	}
	
	/**
	 * 获取企业下全部可分配菜单-功能角色分配菜单时使用
	 * @param systemId 系统ID
	 * @return 全部可分配菜单
	 */
	@Cacheable(key = "'allAssignableMenu$' + #systemId")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> searchAllAssignableMenu(String systemId) {

		SQL sql = new SQL();
		sql.SELECT("A.*").FROM(tableName + " A");
		if(StringUtils.isNotBlank(systemId)) {
			sql.FROM("AUTH_SYSTEM_MENU B", "AUTH_SYSTEM C");
		}

		sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");
		sql.WHERE("A.ASSIGNABLE = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("A.IS_PUBLIC IN (0, 1)");
		if(StringUtils.isNotBlank(systemId)) {
			sql.WHERE("A.ID = B.MENU_ID");
			sql.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
			sql.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'");
			sql.WHERE("B.SYSTEM_ID = C.ID");
			sql.WHERE("C." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
			sql.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'");
			sql.WHERE("C.ID = '" + systemId + "'");
		}

		sql.ORDER_BY("A.SORT_NUMBER ASC");	//排序

		return this.search(sql);

	}

	/**
	 * 切换菜单是否启用状态
	 * @param id 按钮ID
	 */
	@CacheEvict(allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void toggleEnable(String id) {
		Menu menu = this.searchById(id);
		menu.setEnabled(menu.getEnabled() == null || !menu.getEnabled());
		this.modify(menu);
	}

	/**
	 *  对post的操作会清空departmentPost树的缓存，下同
	 *  DepartmentService中有相同配置
	 * @param entity	实体对象
	 */
	@Override
	@CacheEvict(allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void create(Menu entity) {
		super.create(entity);
	}

	@Override
	@CacheEvict(allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void modify(Menu entity) {
		super.modify(entity);
	}

	@Override
	@CacheEvict(allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(String id, String accountId) {
		super.remove(id, accountId);
	}

	@Override
	@CacheEvict(allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Menu entity) {
		super.remove(entity);
	}
}