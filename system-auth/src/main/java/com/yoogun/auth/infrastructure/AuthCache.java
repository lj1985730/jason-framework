package com.yoogun.auth.infrastructure;

import com.yoogun.auth.domain.model.*;
import com.yoogun.auth.domain.model.System;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.util.List;

/**
 * 当前用户缓存
 * @author ThinkGem
 * @version 2013-5-29
 */
public class AuthCache {
	
	public enum LoginInfo {
		SYSTEM,
		MODE,
		ACCOUNT,
		PERSON,
		TENANT,
		DEPARTMENT,
		POST,
		ROLES,
		MENUS,
		DATA_PERMISSION,
		AUDIT_PERMISSION
	}

	private static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 获取系统缓存
	 * @param key 缓存KEY
	 * @return 系统缓存
	 */
	public static Object get(LoginInfo key) {
		return getSession().getAttribute(key);
	}
	
	/**
	 * 获取系统缓存，null则返回默认值
	 * @param key 缓存KEY
	 * @param defaultValue 默认值
	 * @return 系统缓存
	 */
	public static Object get(LoginInfo key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 写入缓存
	 * @param key 缓存KEY
	 * @param value 缓存
	 */
	public static void put(LoginInfo key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从缓存中移除
	 * @param key 缓存KEY
	 */
	public static void remove(LoginInfo key) {
		getSession().removeAttribute(key);
	}

	/**
	 * 获取当前登录系统
	 */
	public static System system() {
		Object obj = AuthCache.get(LoginInfo.SYSTEM);
		if(obj == null) {
			return null;
		}
		return ((System)obj);
	}

	/**
	 * 获取当前登录企业
	 */
	public static Tenant tenant() {
		Object obj = AuthCache.get(LoginInfo.TENANT);
		if(obj == null) {
			return null;
		}
		return (Tenant)obj;
	}

	/**
	 * 获取当前登录企业ID
	 */
	public static String tenantId() {
		Tenant tenant = tenant();
		if(tenant == null) {
			return null;
		}
		return tenant.getId();
	}

	/**
	 * 获取当前登录账户
	 */
	public static Account account() {
		Object obj = AuthCache.get(LoginInfo.ACCOUNT);
		if(obj == null) {
			return null;
		}
		return (Account)obj;
	}

	/**
	 * 获取当前登录账户ID
	 */
	public static String accountId() {
		Account account = account();
		if(account == null) {
			return null;
		}
		return account.getId();
	}

	/**
	 * 判断当前是否是超级管理员
	 */
	public static Boolean isAdmin() {
		Account account = account();
		if(account == null) {
			return false;
		}
		Boolean isAdmin = account.getIsAdmin();
		return isAdmin == null ? false : isAdmin;
	}

	/**
	 * 判断当前是否是租户管理员
	 */
	public static Boolean isTenantAdmin() {
		Account account = account();
		if(account == null) {
			return false;
		}
		return account.isTenantAdmin();
	}

	/**
	 * 判断当前是否是管理员
	 */
	public static Boolean isSuperAdmin() {
		Account account = account();
		if(account == null) {
			return false;
		}
		return account.isSuperAdmin();
	}

	/**
	 * 获取当前登录人员
	 */
	public static Person person() {
		Object obj = AuthCache.get(LoginInfo.PERSON);
		if(obj == null) {
			return null;
		}
		return (Person)obj;
	}

	/**
	 * 获取当前登录人员ID
	 */
	public static String personId() {
		Person person = person();
		if(person == null) {
			return null;
		}
		return person.getId();
	}

	/**
	 * 获取当前登录人员姓名
	 */
	public static String personName() {
		Person person = person();
		if(person == null) {
			return null;
		}
		return person.getName();
	}

	/**
	 * 获取当前登录人员所属部门
	 */
	public static Department department() {
		Object obj = AuthCache.get(LoginInfo.DEPARTMENT);
		if(obj == null) {
			return null;
		}
		return (Department)obj;
	}

	/**
	 * 获取当前登录人员所属岗位
	 */
	public static Post post() {
		Object obj = AuthCache.get(LoginInfo.POST);
		if(obj == null) {
			return null;
		}
		return (Post)obj;
	}

	/**
	 * 获取当前登录人员全部角色
	 */
	@SuppressWarnings("unchecked")
	public static List<Role> roles() {
		Object obj = AuthCache.get(LoginInfo.ROLES);
		if(obj == null) {
			return null;
		}
		return (List<Role>)obj;
	}

	/**
	 * 获取当前登录人员全部权限下按钮
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> menus() {
		Object obj = AuthCache.get(LoginInfo.MENUS);
		if(obj == null) {
			return null;
		}
		return (List<Menu>)obj;
	}

	/**
	 * 获取当前登录人员全部数据权限
	 */
	@SuppressWarnings("unchecked")
	public static List<Account> dataPermission() {
		Object obj = AuthCache.get(LoginInfo.DATA_PERMISSION);
		if(obj == null) {
			return null;
		}
		return (List<Account>)obj;
	}

	/**
	 * 获取当前登录人员全部审核权限
	 */
	@SuppressWarnings("unchecked")
	public static List<Account> auditPermission() {
		Object obj = AuthCache.get(LoginInfo.AUDIT_PERMISSION);
		if(obj == null) {
			return null;
		}
		return (List<Account>)obj;
	}
}
