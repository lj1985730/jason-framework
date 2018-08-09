package com.yoogun.auth.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 权限-账户-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "ACT_ID_USER")
public class Account extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * activiti主键
	 */
	@NotBlank(message = "‘activiti主键’不能为空")
	@Length(max = 100, message = "‘activiti主键’内容长度不能超过36")
	@Column(name = "ID_")
	private String id_;

	/**
	 * 用户名
	 */
	@NotBlank(message = "‘用户名’不能为空")
	@Length(max = 100, message = "‘用户名’内容长度不能超过100")
	@Column(name = "FIRST_")
	private String name;

	/**
	 * 手机号
	 */
	@Length(max = 20, message = "‘手机号’内容长度不能超过20")
	@Column(name = "PHONE")
	private String phone;

	/**
	 * 邮箱
	 */
	@Length(max = 100, message = "‘邮箱’内容长度不能超过100")
	@Column(name = "EMAIL_")
	private String email;

	/**
	 * 密码
	 */
	@NotBlank(message = "‘密码’不能为空")
	@Length(max = 100, message = "‘密码’内容长度不能超过100")
	@Column(name = "PWD_")
	private String password;

	/**
	 * 盐值
	 */
	@Length(max = 100, message = "‘盐值’内容长度不能超过100")
	@Column(name = "SALT")
	private String salt;

	/**
	 * 人员
	 */
	private Person person;

	/**
	 * 人员ID
	 */
	@Length(max = 36, message = "‘人员ID’内容长度不能超过36")
	@Column(name = "PERSON_ID")
	private String personId;

	/**
	 * 人员姓名
	 */
	private String personName;

	/**
	 * 是否管理员
	 */
	@NotNull(message = "‘是否管理员’不可为空")
	@Column(name = "IS_ADMIN")
	private Boolean isAdmin;

	/**
	 * 用户等级
	 */
	@Digits(integer = 9, fraction = 0, message = "‘用户等级’数值位数不能超过9")
	@Column(name = "LEVEL")
	private Integer level;

	/**
	 * 最后一次登录时间
	 */
	@Column(name = "LAST_LOGIN")
	private LocalDateTime lastLogin;

	/**
	 * 是否启用
	 */
	@NotNull(message = "‘是否启用’不能为空")
	@Column(name = "ENABLED")
	private Boolean enabled;

	/**
	 * 是否锁定
	 */
	@NotNull(message = "‘是否锁定’不能为空")
	@Column(name = "LOCKED")
	private Boolean locked;

	/**
	 * 更新次数
	 */
	@Column(name = "REV_")
	private int updateNum;

	private String[] roles;

	public int getUpdateNum() {
		return updateNum;
	}

	public void setUpdateNum(int updateNum) {
		this.updateNum = updateNum;
	}

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	/**
	 * 是否企业管理员
	 */
	public Boolean isTenantAdmin() {
		return this.getIsAdmin() && !this.getTenantId().equalsIgnoreCase(PermissionService.superTenantId);
	}

	/**
	 * 是否超管
	 */
	public Boolean isSuperAdmin() {
		return this.getIsAdmin() && this.getTenantId().equalsIgnoreCase(PermissionService.superTenantId);
	}

}