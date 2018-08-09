package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限-角色-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_ROLE")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	@NotBlank(message = "‘角色名称’不能为空")
	@Length(max = 100, message = "‘角色名称’内容长度不能超过100")
	@Column(name = "NAME")
	private String name;

	/**
	 * 角色编码
	 */
	@Length(max = 100, message = "‘角色编码’内容长度不能超过100")
	@Column(name = "CODE")
	private String code;

	/**
	 * 是否使用
	 */
	@NotNull(message = "‘是否使用’不能为空")
	@Column(name = "ENABLED")
	private Boolean enabled;

	/**
	 * 是否系统角色
	 */
	@NotNull(message = "‘是否系统角色’不能为空")
	@Column(name = "IS_SYSTEM")
	private Boolean isSystem;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "‘备注’ 内容长度不能超过1000")
	@Column(name = "REMARK")
	private String remark;

	/**
	 * 角色关系
	 */
	private List<RoleRlat> roleRlats;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<RoleRlat> getRoleRlats() {
		return roleRlats;
	}

	public void setRoleRlats(List<RoleRlat> roleRlats) {
		this.roleRlats = roleRlats;
	}
}