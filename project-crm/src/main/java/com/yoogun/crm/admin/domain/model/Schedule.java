/*
 * Schedule.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.utils.infrastructure.excel.CellAlign;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


/**
 * Schedule-实体<br/>
 * @author  Sheng Baoyu at 2018-03-20 14:59:38
 * @since v1.0.0
 */
@Table(name = "CU_SCHEDULE")
public class Schedule extends BaseEntity {

	public static final long serialVersionUID = 1L;

	/**
	 * 客户ID
	 **/
	@Length(max = 64, message = "‘客户ID’长度不能超过36!")
	@Column(name = "CUSTOMER_ID")
	private String customerId;

	/**
	 * 客户名称
	 **/
	@ExcelExport(title = "客户", align = CellAlign.CENTER, sort = 1)
	private String customerName;

	/**
	 * 所属部门
	 **/
	@Length(max = 64, message = "‘所属部门’长度不能超过64!")
	@Column(name = "COMPANY_DEPT_ID")
	private String companyDeptId;

	/**
	 * 所属部门名称
	 **/
	@ExcelExport(title = "所属部门", align = CellAlign.CENTER, sort = 2)
	private String deptName;

	/**
	 * 关联商机
	 **/
	@Length(max = 64, message = "‘关联机会’长度不能超过36!")
	@Column(name = "CHANCE_ID")
	private String chanceId;

	/**
	 * 商机名称
	 **/
	@ExcelExport(title = "相关商机", align = CellAlign.CENTER, sort = 3)
	private String chanceName;

	/**
	 * 日程标题
	 **/
	@ExcelExport(title = "日程标题", align = CellAlign.CENTER, sort = 4)
	@NotBlank(message = "日程标题不能为空！")
	@Length(max = 128, message = "‘日程标题’长度不能超过128!")
	@Column(name = "SCHEDULE_TITLE")
	private String scheduleTitle;

	/**
	 * 参与人
	 **/
	@Length(max = 256, message = "‘参与人’长度不能超过256!")
	@Column(name = "SCHEDULE_PARTIN")
	private String schedulePartin;

	/**
	 * 参与人名
	 **/
	@ExcelExport(title = "参与人", align = CellAlign.CENTER, sort = 5)
	private String participant;

	private String preSellers;

	/**
	 * 售前
	 */
	@ExcelExport(title = "售前", align = CellAlign.CENTER, sort = 6)
	private String preSellerName;

	/**
	 * 拜访类型
	 **/
	@Column(name = "SCHEDULE_VISIT")
	private Integer scheduleVisit;

	@ExcelExport(title = "拜访类型", align = CellAlign.CENTER, sort = 7)
	private String scheduleVisitStr;

	/**
	 * 当前阶段
	 */
	@ExcelExport(title = "当前阶段", align = CellAlign.CENTER, sort = 8)
	private String currentStage;

	/**
	 * 时间起
	 **/
	@ExcelExport(title = "开始时间", align = CellAlign.CENTER, sort = 9)
	@Length(max = 19, message = "‘开始时间’长度不能超过19!")
	@Column(name = "SCHEDULE_START")
	private String scheduleStart;

	/**
	 * 时间止
	 **/
	@ExcelExport(title = "结束时间", align = CellAlign.CENTER, sort = 10)
	@Length(max = 19, message = "‘结束时间’长度不能超过19!")
	@Column(name = "SCHEDULE_END")
	private String scheduleEnd;

	/**
	 * 日程内容
	 **/
	@ExcelExport(title = "日程内容", align = CellAlign.LEFT, sort = 11)
	@Length(max = 2048, message = "‘日程内容’长度不能超过2048!")
	@Column(name = "SCHEDULE_CONTENT")
	private String scheduleContent;

	/**
	 * 日程地点
	 **/
	@Length(max = 256, message = "‘日程地点’长度不能超过256!")
	@Column(name = "SCHEDULE_ADDRESS")
	private String scheduleAddress;

	/**
	 * 纬度
	 **/
	@Length(max = 256, message = "‘纬度’长度不能超过256!")
	@Column(name = "SCHEDULE_LATITUDE")
	private String scheduleLatitude;

	/**
	 * 经度
	 **/
	@Length(max = 256, message = "‘经度’长度不能超过256!")
	@Column(name = "SCHEDULE_LONGITUDE")
	private String scheduleLongitude;

	/**
	 * 日程照片
	 **/
	@Length(max = 2048, message = "‘日程照片’长度不能超过2048!")
	@Column(name = "SCHEDULE_PHOTO")
	private String schedulePhoto;

	/**
	 * 创建人
	 **/
	@Length(max = 64, message = "‘创建人’长度不能超过64!")
	@Column(name = "SCHEDULE_CREATE_ID")
	private String scheduleCreateId;

	private String createStr;

	/**
	 * 创建时间
	 **/
	@Length(max = 19, message = "‘创建时间’长度不能超过19!")
	@Column(name = "SCHEDULE_CREATE_TIME")
	private String scheduleCreateTime;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyDeptId() {
		return companyDeptId;
	}

	public void setCompanyDeptId(String companyDeptId) {
		this.companyDeptId = companyDeptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getChanceId() {
		return chanceId;
	}

	public void setChanceId(String chanceId) {
		this.chanceId = chanceId;
	}

	public String getChanceName() {
		return chanceName;
	}

	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public String getSchedulePartin() {
		return schedulePartin;
	}

	public void setSchedulePartin(String schedulePartin) {
		this.schedulePartin = schedulePartin;
	}

	public String getParticipant() {
		participant = EntityUtils.loadJsonProperties(schedulePartin, "text");
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getPreSellers() {
		return preSellers;
	}

	public void setPreSellers(String preSellers) {
		this.preSellers = preSellers;
	}

	public String getPreSellerName() {
		preSellerName = EntityUtils.loadJsonProperties(preSellers, "text");
		return preSellerName;
	}

	public void setPreSellerName(String preSellerName) {
		this.preSellerName = preSellerName;
	}

	public Integer getScheduleVisit() {
		return scheduleVisit;
	}

	public void setScheduleVisit(Integer scheduleVisit) {
		this.scheduleVisit = scheduleVisit;
	}

	public String getScheduleVisitStr() {
		return scheduleVisitStr;
	}

	public void setScheduleVisitStr(String scheduleVisitStr) {
		this.scheduleVisitStr = scheduleVisitStr;
	}

	public String getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}

	public String getScheduleStart() {
		return scheduleStart;
	}

	public void setScheduleStart(String scheduleStart) {
		this.scheduleStart = scheduleStart;
	}

	public String getScheduleEnd() {
		return scheduleEnd;
	}

	public void setScheduleEnd(String scheduleEnd) {
		this.scheduleEnd = scheduleEnd;
	}

	public String getScheduleContent() {
		return scheduleContent;
	}

	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}

	public String getScheduleAddress() {
		return scheduleAddress;
	}

	public void setScheduleAddress(String scheduleAddress) {
		this.scheduleAddress = scheduleAddress;
	}

	public String getScheduleLatitude() {
		return scheduleLatitude;
	}

	public void setScheduleLatitude(String scheduleLatitude) {
		this.scheduleLatitude = scheduleLatitude;
	}

	public String getScheduleLongitude() {
		return scheduleLongitude;
	}

	public void setScheduleLongitude(String scheduleLongitude) {
		this.scheduleLongitude = scheduleLongitude;
	}

	public String getSchedulePhoto() {
		return schedulePhoto;
	}

	public void setSchedulePhoto(String schedulePhoto) {
		this.schedulePhoto = schedulePhoto;
	}

	public String getScheduleCreateId() {
		return scheduleCreateId;
	}

	public void setScheduleCreateId(String scheduleCreateId) {
		this.scheduleCreateId = scheduleCreateId;
	}

	public String getCreateStr() {
		return createStr;
	}

	public void setCreateStr(String createStr) {
		this.createStr = createStr;
	}

	public String getScheduleCreateTime() {
		return scheduleCreateTime;
	}

	public void setScheduleCreateTime(String scheduleCreateTime) {
		this.scheduleCreateTime = scheduleCreateTime;
	}

}