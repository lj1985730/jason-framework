package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限-角色菜单关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_ROLE_MENU")
public class RoleMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@NotBlank(message = "‘角色ID’不能为空")
	@Length(max = 36, message = "‘角色ID’长度不能超过36")
	@Column(name = "ROLE_ID")
	private String roleId;

	/**
	 * 菜单
	 */
	@NotNull(message = "‘菜单’不能为空")
	private Menu menu;

	/**
	 * 菜单ID
	 */
	@NotBlank(message = "‘菜单ID’不能为空")
	@Length(max = 36, message = "‘菜单ID’内容长度不能超过36")
	@Column(name = "MENU_ID")
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}