package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.domain.model.QuickMenu;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-用户便捷菜单-业务层
 * @author liujun at 2016-3-4 11:10:59
 * @version v1.0.0
 */
@Service
public class QuickMenuService extends BaseAuthService<QuickMenu> {

	/**
	 * 获取快速访问菜单
	 * @return 快速访问菜单
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> getQuickMenu() {
		//结果列表
		List<Menu> quickMenuList = new ArrayList<>();
		
		List<QuickMenu> quickModules = this.getQuickMenuModel();
		
		//将快速访问页面加入队列
		quickModules.forEach(quickModule -> quickMenuList.add(quickModule.getMenu()));

		return quickMenuList;
	}
	
	/**
	 * 获取快速访问菜单（关系对象）
	 * @return 快速访问菜单（关系对象）
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<QuickMenu> getQuickMenuModel() {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		Account account = AuthCache.account();
		if(account == null) {
			return null;
		}

		sql.WHERE("ACCOUNT_ID = '" + account.getId() + "'");

		sql.ORDER_BY("LAST_MODIFY_TIME DESC");	//默认操作时间倒序

		return this.search(sql);
	}
	
	/**
	 * 保存快捷表单
	 * @param menuIds	菜单id数组
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void createQuickMenu(String[] menuIds) {
		int count = menuIds.length;
		if(count == 0) {
			return;
		}
		//取旧数据
		List<QuickMenu> oldQuickMenus = this.getQuickMenuModel();
		//删除全部旧数据
		if(oldQuickMenus != null && !oldQuickMenus.isEmpty()) {
			oldQuickMenus.forEach(oldQuickMenu -> {
				super.destroy(oldQuickMenu.getId());	//物理删除
			});
		}

		//新增数据
		QuickMenu quickMenu;
		for (String menuId : menuIds) {
			quickMenu = new QuickMenu();
			quickMenu.setMenuId(menuId);
			this.create(quickMenu);
		}
	}
	
	/**
	 * 删除快捷菜单，物理删除
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Serializable id) {

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");

		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}

		sql.WHERE("ACCOUNT_ID = '" + account.getId() + "'");
		sql.WHERE("MENU_ID = '" + id + "'");
		List<QuickMenu> quickModules = this.search(sql);

		quickModules.forEach(quickModule -> {
			super.destroy(quickModule.getId());	//物理删除
		});
	}
}