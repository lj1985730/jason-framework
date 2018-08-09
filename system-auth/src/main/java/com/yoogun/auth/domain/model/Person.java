package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 权限-人员-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @since v1.0.0
 */
@Table(name = "AUTH_PERSON")
public class Person extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 姓名
	 */
	@NotBlank(message = "‘姓名’不能为空")
	@Length(max = 100, message = "‘姓名’内容长度不能超过100")
	@Column(name = "NAME")
	private String name;

	/**
	 * 英文名
	 */
	@Length(max = 100, message = "‘英文名’内容长度不能超过100")
	@Column(name = "EN_NAME")
	private String enName;

	/**
	 * 性别 1：男；2：女
	 */
	@NotNull(message = "‘性别’不能为空")
	@Column(name = "GENDER")
	private String gender;

	/**
	 * 性别名称
	 */
	private String genderName;

	/**
	 * 证件号
	 */
	@Length(max = 100, message = "‘证件号’内容长度不能超过100")
	@Column(name = "ID_NUMBER")
	private String idNumber;
	
	/**
	 * 国籍
	 */
	@Length(max = 100, message = "‘国籍’内容长度不能超过100")
	@Column(name = "COUNTRY")
	private String country;
	
	/**
	 * 民族编码
	 */
	@Length(max = 100, message = "‘民族’内容长度不能超过100")
	@Column(name = "NATIONALITY")
	private String nationality;

	/**
	 * 民族内容
	 */
	private String nationalityName;

	/**
	 * 出生地
	 */
	@Length(max = 100, message = "‘出生地’内容长度不能超过100")
	@Column(name = "BIRTH_PLACE")
	private String birthPlace;

	/**
	 * 出生日期
	 */
	@Column(name = "BIRTH_DATE")
	private LocalDate birthDate;

	/**
	 * 手机号码
	 */
	@Length(max = 100, message = "‘手机号码’内容长度不能超过100")
	@Column(name = "PHONE")
	private String phone;

	/**
	 * 办公号码
	 */
	@Length(max = 100, message = "‘办公号码’内容长度不能超过100")
	@Column(name = "OFFICE_NUMBER")
	private String officeNumber;

	/**
	 * 邮箱
	 */
	@Length(max = 100, message = "‘邮箱’内容长度不能超过100")
	@Column(name = "EMAIL")
	private String email;

	/**
	 * 家庭住址
	 */
	@Length(max = 100, message = "‘家庭住址’内容长度不能超过100")
	@Column(name = "ADDRESS")
	private String address;

	/**
	 * 人员状态  1在职；2离职；3公休
	 */
	@NotNull(message = "‘人员状态’不可为空")
	@Column(name = "STATE")
	private String state;

	/**
	 * 人员状态名称
	 */
	private String stateName;
	
	/**
	 * 人员分类
	 */
	@NotNull(message = "‘人员分类’不可为空")
	@Column(name = "CATEGORY")
	private String category;
	
	/**
	 * 人员性质 1自有；2借调；3临时；4外派
	 */
	@NotNull(message = "‘人员性质’不可为空")
	@Column(name = "NATURE")
	private String nature;

	/**
	 * 人员性质名称
	 */
	private String natureName;

	/**
	 * 部门
	 */
	private Department department;

	/**
	 * 主职部门ID
	 */
	@Column(name = "DEPARTMENT_ID")
	private String departmentId;

	/**
	 * 兼职部门ids
	 */
	private String partTimeDepartments;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 是否部门长
	 */
	private Boolean isLeader;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "‘备注’内容长度不能超过1000")
	@Column(name = "REMARK")
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getPartTimeDepartments() {
		return partTimeDepartments;
	}

	public void setPartTimeDepartments(String partTimeDepartments) {
		this.partTimeDepartments = partTimeDepartments;
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

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Boolean getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}
}