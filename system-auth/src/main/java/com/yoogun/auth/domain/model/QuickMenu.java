package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-账户快速菜单表-实体
 * @author Liu Jun at 2016-9-22 21:30:45
 * @since v1.0.0
 */
@Table(name = "AUTH_QUICK_MENU")
public class QuickMenu extends BaseEntity {
	public static final long serialVersionUID = 1L;

	/**
	 * 关联账户（查询）
	 */
	private Account account;
	
	/**
	 * 关联账户Id（编辑）
	 */
	@NotBlank(message = "‘关联账户’不能为空！")
	@Length(max = 36, message = "‘关联账户’Id内容长度不能超过36!")
	@Column(name = "ACCOUNT_ID")
	private String accountId;

	/**
	 * 关联菜单（查询）
	 */
	private Menu menu;

	/**
	 * 关联菜单Id（编辑）
	 */
	@NotBlank(message = "‘关联菜单’不能为空！")
	@Length(max = 36, message = "‘关联菜单’Id内容长度不能超过36!")
	@Column(name = "MENU_ID")
	private String menuId;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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