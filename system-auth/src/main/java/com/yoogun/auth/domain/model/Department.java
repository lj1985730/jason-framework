package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

/**
 * 权限-部门-实体
 * @author Liu Jun
 * @since v1.0.0
 */
@Table(name = "AUTH_DEPARTMENT")
public class Department extends TreeEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 英文名称
	 */
	@Length(max = 100, message = "‘英文名称’内容长度不能超过100")
	@Column(name = "EN_NAME")
	private String enName;

	/**
	 * 部门编码
	 */
	@Length(max = 100, message = "‘部门编码’内容长度不能超过100")
	@Column(name = "CODE")
	private String code;

	/**
	 * 部门简称
	 */
	@Length(max = 100, message = "‘部门简称’内容长度不能超过100")
	@Column(name = "SHORT_NAME")
	private String shortName;

	/**
	 * 部门类型
	 */
	@Length(max = 200, message = "‘部门类型’内容长度不能超过200")
	@Column(name = "CATEGORY")
	private String category;

	/**
	 * 联系人
	 */
	@Length(max = 100, message = "‘联系人’内容长度不能超过100")
	@Column(name = "LINKMAN")
	private String linkman;

	/**
	 * 传真
	 */
	@Length(max = 100, message = "‘传真’内容长度不能超过100")
	@Column(name = "FAX")
	private String fax;

	/**
	 * 电话
	 */
	@Length(max = 100, message = "‘电话’内容长度不能超过100")
	@Column(name = "PHONE")
	private String phone;

	/**
	 * 电子邮件
	 */
	@Length(max = 100, message = "‘电子邮件’内容长度不能超过100")
	@Column(name = "EMAIL")
	private String email;

	/**
	 * 部门等级
	 */
	@Digits(integer = 2, fraction = 0, message = "‘部门等级’数值位数不能超过2")
	@Column(name = "LEVEL")
	private Integer level;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "‘备注’内容长度不能超过1000")
	@Column(name = "REMARK")
	private String remark;

	public Department getParent() {
		return (Department)parent;
	}

	public <E extends TreeEntity> void setParent(E parent) {
		this.parent = parent;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}