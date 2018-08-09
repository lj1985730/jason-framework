package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-账户角色关系-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @version v1.0.0
 */
@Table(name = "AUTH_ACCOUNT_ROLE")
public class AccountRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录用户
	 */
	private Account account;

	/**
	 * 登录用户ID
	 **/
	@NotBlank(message = "‘用户ID’不能为空")
	@Length(max = 36, message = "‘用户ID’内容长度不能超过36")
	@Column(name = "ACCOUNT_ID")
	private String accountId;

	/**
	 * 角色
	 */
	private Role role;

	/**
	 * 角色ID
	 **/
	@NotBlank(message = "‘角色ID’不能为空")
	@Length(max = 36, message = "‘角色ID’内容长度不能超过36")
	@Column(name = "ROLE_ID")
	private String roleId;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}