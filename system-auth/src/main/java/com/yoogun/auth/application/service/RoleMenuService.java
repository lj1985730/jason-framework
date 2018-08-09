package com.yoogun.auth.application.service;

import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.domain.model.RoleMenu;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.repository.MenuDao;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-角色菜单关系-业务层
 * @author Liu Jun
 * @version 2016-8-14 22:36:02
 */
@Service
public class RoleMenuService extends BaseAuthService<RoleMenu> {

	@Resource
	private MenuDao menuDao;

	/**
	 * 获取角色的菜单，这里只取最长叶子节点
	 * @param roleId 角色ID
	 * @return 角色的菜单
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> searchLeafMenuByRole(String roleId) {

		SQL sql = new SQL();
		sql.SELECT("A.*")
				.FROM("AUTH_MENU A", "AUTH_ROLE_MENU B", "AUTH_ROLE C")
				.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'")
                // 排除掉带有子节点的节点
				.WHERE("NOT EXISTS(SELECT D.ID FROM AUTH_MENU D WHERE D.PARENT_ID = A.ID)")
				.WHERE("A.ID = B.MENU_ID")
				.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("B.ROLE_ID = C.ID")
				.WHERE("C." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'")
				.WHERE("C.TENANT_ID = '" + AuthCache.tenantId() + "'")
				.WHERE("C.ID = '" + roleId + "'");

		return menuDao.search(sql);
	}

	/**
	 * 设置角色的菜单
	 * @param roleId 账户Id
	 * @param menuIds 角色Id集合
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void bindMenuToRole(String roleId, List<String> menuIds) {
		List<String> removedIdList = removeAllMenu(roleId);	//先删除全部已绑定的数据

		List<String> updateIdList = new ArrayList<>();	//绑定时需要更新的数据
		List<String> createIdList = new ArrayList<>();	//绑定时需要新增的数据

		menuIds.forEach(menuId -> {	//saveOrUpdate分发
			if(removedIdList.contains(menuId)) {
				updateIdList.add(menuId);
			} else {
				createIdList.add(menuId);
			}
		});

		updateRoleMenu(roleId, updateIdList);

		createRoleMenu(roleId, createIdList);
	}

	/**
	 * 删除角色的全部菜单
	 * @param roleId 角色Id
	 * @return 被删除的菜单Id
	 */
	private List<String> removeAllMenu(String roleId) {
		SQL sql = new SQL().SELECT("*").FROM(tableName);
		sql.WHERE("ROLE_ID = '" + roleId + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<RoleMenu> list = this.search(sql);

		List<String> removedMenuId = new ArrayList<>();

		list.forEach(roleMenu -> {
			this.remove(roleMenu);
			removedMenuId.add(roleMenu.getMenuId());
		});

		return removedMenuId;
	}

	/**
	 * 恢复角色的菜单
	 * @param roleId 角色Id
	 * @param menuIdList 待操作菜单Id集合
	 */
	private void updateRoleMenu(String roleId, List<String> menuIdList) {

		if(StringUtils.isBlank(roleId) || menuIdList.isEmpty()) {
			return;
		}

		SQL sql = new SQL().SELECT("*").FROM(tableName);
		// 构造条件
		sql.WHERE("ROLE_ID = '" + roleId + "'");
		sql.WHERE("MENU_ID IN (" + EntityUtils.inSql(menuIdList) + ")");
		sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_TRUE + "'");
		sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
		List<RoleMenu> list = this.search(sql);

		list.forEach(roleMenu -> {
			roleMenu.setDeleted(false);
			this.modify(roleMenu);
		});
	}

	/**
	 * 新增角色的菜单
	 * @param roleId		角色Id
	 * @param menuIdList 待操作菜单Id集合
	 */
	private void createRoleMenu(String roleId, List<String> menuIdList) {
		RoleMenu roleMenu;
		for(String menuId : menuIdList) {
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			this.create(roleMenu);
		}
	}
}