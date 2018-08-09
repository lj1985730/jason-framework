/*
 * Chance.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.utils.infrastructure.excel.CellAlign;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


/**
 * Chance-实体<br/>
 * @author  Sheng Baoyu at 2018-03-19 10:18:11
 * @since v1.0.0
 */
@Table(name = "CU_CHANCE")
public class Chance extends BaseEntity {
	/**
	 * 名称
	 **/
	@ExcelExport(title = "商机名称", align = CellAlign.CENTER, sort = 2)
	@Length(max = 256, message = "‘机会名称’长度不能超过256!")
	@Column(name = "NAME")
	private String name;

	/**
	 * 机会参与人
	 **/
	@Length(max = 256, message = "‘机会参与人’长度不能超过256!")
	@Column(name = "CHANCE_PARTIN")
	private String chancePartin;

	@ExcelExport(title = "参与人", align = CellAlign.CENTER, sort = 3)
	private String participant;

	/**
	 * 所属公司
	 **/
	@Length(max = 64, message = "‘所属公司’长度不能超过64!")
	@Column(name = "COMPANY_ID")
	private String companyId;

	/**
	 * 负责部门
	 **/
	@Length(max = 64, message = "‘所属部门’长度不能超过64!")
	@Column(name = "COMPANY_DEPT_ID")
	private String companyDeptId;

	@ExcelExport(title = "负责部门", align = CellAlign.CENTER, sort = 6)
	private String companyDeptName;

	/**
	 * 销售ID
	 **/
	@Length(max = 32, message = "‘销售ID’长度不能超过36!")
	@Column(name = "SALESMAN_ID")
	private String salesmanId;

	@ExcelExport(title = "销售", align = CellAlign.CENTER, sort = 5)
	private String salesmanName;

	/**
	 * 售前
	 **/
	@Length(max = 32, message = "‘售前ID’长度不能超过36!")
	@Column(name = "PRE_SELLERS")
	private String preSellers;

	@ExcelExport(title = "售前", align = CellAlign.CENTER, sort = 4)
	private String preSellerName;

	/**
	 * 客户编号
	 **/
	@NotBlank(message = "客户编号不能为空！")
	@Length(max = 64, message = "‘客户编号’长度不能超过64!")
	@Column(name = "CUSTOMER_ID")
	private String customerId;

	@ExcelExport(title = "客户", align = CellAlign.CENTER, sort = 1)
	private String customerName;

	/**
	 * 预计合同金额
	 **/
	@ExcelExport(title = "预计合同金额（元）", align = CellAlign.CENTER, sort = 7)
	@Column(name = "CHANCE_MONEY")
	private Integer chanceMoney;

	/**
	 * 销售阶段
	 **/
	@Column(name = "CHANCE_STEP")
	private Integer chanceStep;

	/**
	 * 销售阶段JSON
	 */
	@Length(max = 2048, message = "‘机会描述’长度不能超过2048!")
	@Column(name = "CHANCE_STEP_JSON")
	private String chanceStepJson;

	@ExcelExport(title = "销售阶段", align = CellAlign.CENTER, sort = 8)
	private String chanceStepName;

	/**
	 * 录入日期
	 **/
	@ExcelExport(title = "录入日期", align = CellAlign.CENTER, sort = 9)
	@Length(max = 19, message = "‘录入日期’长度不能超过19!")
	@Column(name = "CHANCE_RESULT_DATE")
	private String chanceResultDate;

	/**
	 * 机会描述
	 **/
	@Length(max = 2048, message = "‘机会描述’长度不能超过2048!")
	@Column(name = "CHANCE_DESC")
	private String chanceDesc;

	/**
	 * 查看权限
	 **/
	@Column(name = "CHANCE_RIGHT")
	private Short chanceRight;

	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE")
	@NotNull(message = "创建日期不可为空！")
	private LocalDate createDate;

	public static final long serialVersionUID = 1L;

	/**
	 * 获取机会名称
	 **/
	public String getName() {
		return name;
	}

	/**
	 * 设置机会名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	public String getChanceStepJson() {
		return chanceStepJson;
	}

	public void setChanceStepJson(String chanceStepJson) {
		this.chanceStepJson = chanceStepJson;
	}

	/**
	 * 获取机会参与人
	 **/
	public String getChancePartin() {
		return chancePartin;
	}

	/**
	 * 设置机会参与人
	 **/
	public void setChancePartin(String chancePartin) {
		this.chancePartin = chancePartin;
	}

	public String getParticipant() {
		participant = EntityUtils.loadJsonProperties(chancePartin, "text");
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	/**
	 * 获取所属公司
	 **/
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 设置所属公司
	 **/
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * 获取所属部门
	 **/
	public String getCompanyDeptId() {
		return companyDeptId;
	}

	/**
	 * 设置所属部门
	 **/
	public void setCompanyDeptId(String companyDeptId) {
		this.companyDeptId = companyDeptId;
	}

	/**
	 * 获取销售ID
	 **/
	public String getSalesmanId() {
		return salesmanId;
	}

	/**
	 * 设置销售ID
	 **/
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
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

	/**
	 * 获取客户编号
	 **/
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * 设置客户编号
	 **/
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * 获取销售金额
	 **/
	public Integer getChanceMoney() {
		return chanceMoney;
	}

	/**
	 * 设置销售金额
	 **/
	public void setChanceMoney(Integer chanceMoney) {
		this.chanceMoney = chanceMoney;
	}

	/**
	 * 获取销售阶段
	 **/
	public Integer getChanceStep() {
		return chanceStep;
	}

	/**
	 * 设置销售阶段
	 **/
	public void setChanceStep(Integer chanceStep) {
		this.chanceStep = chanceStep;
	}

	public String getChanceStepName() {
		return chanceStepName;
	}

	public void setChanceStepName(String chanceStepName) {
		this.chanceStepName = chanceStepName;
	}

	/**
	 * 获取结单日期
	 **/
	public String getChanceResultDate() {
		return chanceResultDate;
	}

	/**
	 * 设置结单日期
	 **/
	public void setChanceResultDate(String chanceResultDate) {
		this.chanceResultDate = chanceResultDate;
	}

	/**
	 * 获取机会描述
	 **/
	public String getChanceDesc() {
		return chanceDesc;
	}

	/**
	 * 设置机会描述
	 **/
	public void setChanceDesc(String chanceDesc) {
		this.chanceDesc = chanceDesc;
	}

	/**
	 * 获取查看权限
	 **/
	public Short getChanceRight() {
		return chanceRight;
	}

	/**
	 * 设置查看权限
	 **/
	public void setChanceRight(Short chanceRight) {
		this.chanceRight = chanceRight;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyDeptName() {
		return companyDeptName;
	}

	public void setCompanyDeptName(String companyDeptName) {
		this.companyDeptName = companyDeptName;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}
}