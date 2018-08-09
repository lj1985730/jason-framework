/*
 * Contact.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.utils.infrastructure.excel.CellAlign;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * CRM-客户联系人-实体<br/>
 * @author  Liu Jun at 2018-7-9 14:49:44
 * @since v1.0.0
 */
@Table(name = "CRM_CONTACT")
public class Contact extends BaseEntity {

	/**
	 * 客户ID
	 **/
	@NotBlank(message = "‘客户ID’不能为空！")
	@Length(max = 36, message = "‘客户ID’长度不能超过36!")
	@Column(name = "CUSTOMER_ID")
	private String customerId;

	/**
	 * 姓名
	 **/
	@ExcelExport(title = "姓名", align = CellAlign.CENTER, sort = 1)
	@NotBlank(message = "‘姓名’不能为空！")
	@Length(max = 30, message = "‘姓名’长度不能超过30!")
	@Column(name = "NAME")
	private String name;

	/**
	 * 性别
	 */
	@NotNull(message = "‘性别’不能为空！")
	@Digits(integer = 9, fraction = 0, message = "‘性别’位数不能超过9！")
	@Column(name = "GENDER")
	private Integer gender;

	private String genderText;

	/**
	 * 生日
	 **/
	@ExcelExport(title = "生日", align = CellAlign.CENTER,  sort = 6)
	@Length(max = 10, message = "‘生日’长度不能超过10！")
	@Column(name = "BIRTHDATE")
	private String birthdate;

	/**
	 * 部门
	 */
	@Digits(integer = 9, fraction = 0, message = "‘部门’位数不能超过9！")
	@Column(name = "DEPARTMENT")
	private Integer department;

	private String departmentText;

	/**
	 * 职务
	 */
	@Digits(integer = 9, fraction = 0, message = "‘职务’位数不能超过9！")
	@Column(name = "POST")
	private Integer post;

	private String postText;

	/**
	 * 办公电话
	 **/
	@ExcelExport(title = "办公电话", align = CellAlign.CENTER,  sort = 8)
	@Length(max = 20, message = "‘办公电话’长度不能超过20！")
	@Column(name = "OFFICE_NUMBER")
	private String officeNumber;

	/**
	 * 手机号
	 **/
	@ExcelExport(title = "手机号", align = CellAlign.CENTER,  sort = 8)
	@Length(max = 20, message = "‘手机号’长度不能超过20！")
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	/**
	 * 微信号
	 **/
	@Length(max = 20, message = "‘微信号’长度不能超过20！")
	@Column(name = "WEIXIN")
	private String weixin;

	/**
	 * 邮箱
	 **/
	@ExcelExport(title = "邮箱", align = CellAlign.CENTER,  sort = 10)
	@Length(max = 100, message = "‘邮箱’长度不能超过100！")
	@Column(name = "EMAIL")
	private String email;

	/**
	 * QQ号码
	 **/
	@ExcelExport(title = "QQ号码", align = CellAlign.CENTER,  sort = 11)
	@Length(max = 20, message = "‘QQ号码’长度不能超过20！")
	@Column(name = "QQ")
	private String qq;

	/**
	 * 性格特点
	 **/
	@Length(max = 200, message = "‘性格特点’长度不能超过200！")
	@Column(name = "CHARACTER_TRAIT")
	private String characterTrait;

	/**
	 * 家庭情况
	 **/
	@Length(max = 200, message = "‘家庭情况’长度不能超过200！")
	@Column(name = "FAMILY")
	private String family;

	/**
	 * 所学专业
	 **/
	@Length(max = 200, message = "‘所学专业’长度不能超过200！")
	@Column(name = "PROFESSION")
	private String profession;

	/**
	 * 背景
	 **/
	@Length(max = 200, message = "‘背景’长度不能超过200！")
	@Column(name = "BACKGROUND")
	private String background;

	/**
	 * 公司内派系
	 **/
	@Length(max = 200, message = "‘公司内派系’长度不能超过200！")
	@Column(name = "FACTIONS")
	private String factions;

	/**
	 * 联系人类型
	 **/
	@Length(max = 20, message = "‘联系人类型’长度不能超过20！")
	@Column(name = "TYPE")
	private String type;

	/**
	 * 最后修改人
	 */
	private String modifierName;

	/**
	 * 最后修改人账户
	 */
	private String modifierAccount;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getGenderText() {
		return genderText;
	}

	public void setGenderText(String genderText) {
		this.genderText = genderText;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public String getDepartmentText() {
		return departmentText;
	}

	public void setDepartmentText(String departmentText) {
		this.departmentText = departmentText;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getCharacterTrait() {
		return characterTrait;
	}

	public void setCharacterTrait(String characterTrait) {
		this.characterTrait = characterTrait;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getFactions() {
		return factions;
	}

	public void setFactions(String factions) {
		this.factions = factions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public String getModifierAccount() {
		return modifierAccount;
	}

	public void setModifierAccount(String modifierAccount) {
		this.modifierAccount = modifierAccount;
	}
}