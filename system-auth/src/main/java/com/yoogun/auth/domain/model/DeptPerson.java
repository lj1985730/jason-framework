/*
 * AuthDeptPerson.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


/**
 * 部门-人员-实体<br/>
 * @author  Wang Chong at 2018-04-19 11:29:59
 * @since v1.0.0
 */
@Table(name = "AUTH_DEPARTMENT_PERSON")
public class DeptPerson extends BaseEntity {
	/**
	 * 部门主键
	 **/
	@NotBlank(message = "部门主键不能为空！")
	@Length(max = 36, message = "‘部门主键’长度不能超过36!")
	@Column(name = "DEPARTMENT_ID")
	private String departmentId;

	/**
	 * 人员主键
	 **/
	@NotBlank(message = "人员主键不能为空！")
	@Length(max = 36, message = "‘人员主键’长度不能超过36!")
	@Column(name = "PERSON_ID")
	private String personId;

	/**
	 * 部门实体
	 */
	private Department department;

	/**
	 * 人员实体
	 */
	private Person person;

	/**
	 * 人员性别字典名字
	 */
	private String genderName;

	/**
	 * 人员状态字典名字
	 */
	private String stateName;

	/**
	 * 人员类别字典名字
	 */
	private String natureName;

	public static final long serialVersionUID = 1L;

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * 获取部门主键
	 **/
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * 设置部门主键
	 **/
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 获取人员主键
	 **/
	public String getPersonId() {
		return personId;
	}

	/**
	 * 设置人员主键
	 **/
	public void setPersonId(String personId) {
		this.personId = personId;
	}
}