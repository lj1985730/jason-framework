/*
 * AuthRoleRlat.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


/**
 * 角色关系-实体<br/>
 * @author  Wang Chong at 2018-05-01 13:27:08
 * @since v1.0.0
 */
@Table(name = "AUTH_ROLE_RLAT")
public class RoleRlat extends BaseEntity {
	/**
	 * 上级ID
	 **/
	@NotBlank(message = "上级ID不能为空！")
	@Length(max = 36, message = "‘上级ID’长度不能超过36!")
	@Column(name = "SUPERIOR_ID")
	private String superiorId;

	/**
	 * 下级ID
	 **/
	@NotBlank(message = "下级ID不能为空！")
	@Length(max = 36, message = "‘下级ID’长度不能超过36!")
	@Column(name = "SUBORDINATE_ID")
	private String subordinateId;

	/**
	 * 上级对下级是否有数据权限
	 **/
	@Length(max = 1, message = "‘上级对下级是否有数据权限’长度不能超过1!")
	@Column(name = "DATA_PERMISSION")
	private String dataPermission;

	/**
	 * 上级对下级是否有审核权限
	 **/
	@Length(max = 1, message = "‘上级对下级是否有审核权限’长度不能超过1!")
	@Column(name = "AUDIT_PERMISSION")
	private String auditPermission;

	/**
	 * 备注
	 **/
	@Length(max = 1000, message = "‘备注’长度不能超过1000!")
	@Column(name = "REMARK")
	private String remark;

	public static final long serialVersionUID = 1L;

	/**
	 * 获取上级ID
	 **/
	public String getSuperiorId() {
		return superiorId;
	}

	/**
	 * 设置上级ID
	 **/
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	/**
	 * 获取下级ID
	 **/
	public String getSubordinateId() {
		return subordinateId;
	}

	/**
	 * 设置下级ID
	 **/
	public void setSubordinateId(String subordinateId) {
		this.subordinateId = subordinateId;
	}

	/**
	 * 获取上级对下级是否有数据权限
	 **/
	public String getDataPermission() {
		return dataPermission;
	}

	/**
	 * 设置上级对下级是否有数据权限
	 **/
	public void setDataPermission(String dataPermission) {
		this.dataPermission = dataPermission;
	}

	/**
	 * 获取上级对下级是否有审核权限
	 **/
	public String getAuditPermission() {
		return auditPermission;
	}

	/**
	 * 设置上级对下级是否有审核权限
	 **/
	public void setAuditPermission(String auditPermission) {
		this.auditPermission = auditPermission;
	}

	/**
	 * 获取备注
	 **/
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 **/
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 重写equals方法，除了判断Id外，还可判断上下级id
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || o.getClass() != RoleRlat.class) return false;
		RoleRlat entity = (RoleRlat) o;
		if(StringUtils.isNotBlank(entity.getId()) && StringUtils.isNotBlank(id)) {
			return Objects.equals(entity.getId(), id);  //如果两对像Id都存在则比较Id
		} else {
			return Objects.equals(superiorId, entity.getSuperiorId())   //否则比较上下级
					&& Objects.equals(subordinateId, entity.getSubordinateId());
		}
	}
}