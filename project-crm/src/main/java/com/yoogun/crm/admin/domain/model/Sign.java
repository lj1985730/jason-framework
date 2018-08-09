/*
 * Sign.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.core.domain.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


/**
 * Sign-实体<br/>
 * @author  Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 */
@Table(name = "CU_SIGN")
public class Sign extends BaseEntity {
	/**
	 * 签到公司
	 **/
	@Length(max = 64, message = "‘签到公司’长度不能超过64!")
	@Column(name = "SIGN_CORP")
	private String signCorp;

	/**
	 * 签到公司实体
	 */
	private Tenant tenant;

	/**
	 * 签到部门
	 **/
	@Length(max = 64, message = "‘签到部门’长度不能超过64!")
	@Column(name = "SIGN_DEPT")
	private String signDept;

	/**
	 * 签到部门实体
	 */
	private Department department;

	/**
	 * 签到人
	 **/
	@Length(max = 64, message = "‘签到人’长度不能超过64!")
	@Column(name = "SIGN_USER")
	private String signUser;

	/**
	 * 签到人实体
	 */
	private Person person;

	/**
	 * 签到日期
	 **/
	@Length(max = 10, message = "‘签到日期’长度不能超过10!")
	@Column(name = "SIGN_DATE")
	private String signDate;

	/**
	 * 签到时间
	 **/
	@Length(max = 19, message = "‘签到时间’长度不能超过19!")
	@Column(name = "SIGN_IN_TIME")
	private String signInTime;

	/**
	 * 签到地点
	 **/
	@Length(max = 256, message = "‘签到地点’长度不能超过256!")
	@Column(name = "SIGN_IN_ADDRESS")
	private String signInAddress;

	/**
	 * 签到纬度
	 **/
	@Length(max = 256, message = "‘签到纬度’长度不能超过256!")
	@Column(name = "SIGN_IN_LATITUDE")
	private String signInLatitude;

	/**
	 * 签到纬度签到经度
	 **/
	@Length(max = 256, message = "‘签到纬度签到经度’长度不能超过256!")
	@Column(name = "SIGN_IN_LONGITUDE")
	private String signInLongitude;

	/**
	 * 签退时间
	 **/
	@Length(max = 19, message = "‘签退时间’长度不能超过19!")
	@Column(name = "SIGN_OUT_TIME")
	private String signOutTime;

	/**
	 * 签退地点
	 **/
	@Length(max = 256, message = "‘签退地点’长度不能超过256!")
	@Column(name = "SIGN_OUT_ADDRESS")
	private String signOutAddress;

	/**
	 * 签退纬度
	 **/
	@Length(max = 256, message = "‘签退纬度’长度不能超过256!")
	@Column(name = "SIGN_OUT_LATITUDE")
	private String signOutLatitude;

	/**
	 * 签退经度
	 **/
	@Length(max = 256, message = "‘签退经度’长度不能超过256!")
	@Column(name = "SIGN_OUT_LONGITUDE")
	private String signOutLongitude;

	public static final long serialVersionUID = 1L;

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
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
	 * 获取签到公司
	 **/
	public String getSignCorp() {
		return signCorp;
	}

	/**
	 * 设置签到公司
	 **/
	public void setSignCorp(String signCorp) {
		this.signCorp = signCorp;
	}

	/**
	 * 获取签到部门
	 **/
	public String getSignDept() {
		return signDept;
	}

	/**
	 * 设置签到部门
	 **/
	public void setSignDept(String signDept) {
		this.signDept = signDept;
	}

	/**
	 * 获取签到人
	 **/
	public String getSignUser() {
		return signUser;
	}

	/**
	 * 设置签到人
	 **/
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}

	/**
	 * 获取签到日期
	 **/
	public String getSignDate() {
		return signDate;
	}

	/**
	 * 设置签到日期
	 **/
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	/**
	 * 获取签到时间
	 **/
	public String getSignInTime() {
		return signInTime;
	}

	/**
	 * 设置签到时间
	 **/
	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	/**
	 * 获取签到地点
	 **/
	public String getSignInAddress() {
		return signInAddress;
	}

	/**
	 * 设置签到地点
	 **/
	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}

	/**
	 * 获取签到纬度
	 **/
	public String getSignInLatitude() {
		return signInLatitude;
	}

	/**
	 * 设置签到纬度
	 **/
	public void setSignInLatitude(String signInLatitude) {
		this.signInLatitude = signInLatitude;
	}

	/**
	 * 获取签到纬度签到经度
	 **/
	public String getSignInLongitude() {
		return signInLongitude;
	}

	/**
	 * 设置签到纬度签到经度
	 **/
	public void setSignInLongitude(String signInLongitude) {
		this.signInLongitude = signInLongitude;
	}

	/**
	 * 获取签退时间
	 **/
	public String getSignOutTime() {
		return signOutTime;
	}

	/**
	 * 设置签退时间
	 **/
	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}

	/**
	 * 获取签退地点
	 **/
	public String getSignOutAddress() {
		return signOutAddress;
	}

	/**
	 * 设置签退地点
	 **/
	public void setSignOutAddress(String signOutAddress) {
		this.signOutAddress = signOutAddress;
	}

	/**
	 * 获取签退纬度
	 **/
	public String getSignOutLatitude() {
		return signOutLatitude;
	}

	/**
	 * 设置签退纬度
	 **/
	public void setSignOutLatitude(String signOutLatitude) {
		this.signOutLatitude = signOutLatitude;
	}

	/**
	 * 获取签退经度
	 **/
	public String getSignOutLongitude() {
		return signOutLongitude;
	}

	/**
	 * 设置签退经度
	 **/
	public void setSignOutLongitude(String signOutLongitude) {
		this.signOutLongitude = signOutLongitude;
	}
}