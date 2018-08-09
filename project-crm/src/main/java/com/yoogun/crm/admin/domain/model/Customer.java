/*
 * Customer.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.base.infrastructure.DictUtils;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.utils.infrastructure.excel.CellAlign;
import com.yoogun.utils.infrastructure.excel.annotation.ExcelExport;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CRM-客户-实体
 * @author  Liu Jun at 2018-5-3 13:37:02
 * @since v1.0.0
 */
@Table(name = "CRM_CUSTOMER")
public class Customer extends BaseEntity {

	/**
	 * 客户名称
	 **/
	@ExcelExport(title = "客户名称", align = CellAlign.CENTER, sort = 1)
	@Length(max = 30, message = "‘客户名称’长度不能超过30！")
	@Column(name = "NAME")
	private String name;

	/**
	 * 客户简称
	 **/
	@Length(max = 30, message = "‘客户简称’长度不能超过30！")
	@Column(name = "SHORT_NAME")
	private String shortName;

	/**
	 * 统一社会信用码
	 */
	@Pattern(regexp = "[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}", message = "‘统一社会信用代码’格式不正确！")
	@Column(name = "USCC")
	private String uscc;

	/**
	 * 数据来源编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘数据来源’位数不能超过9！")
	@Column(name = "ORIGIN")
	private Integer origin;

	private String originText;

	/**
	 * 所属区域编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘所属区域’位数不能超过9！")
	@Column(name = "AREA")
	private Integer area;

	private String areaText;

	/**
	 * 所属省份编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘所属省份’位数不能超过9！")
	@Column(name = "PROVINCE")
	private Integer province;

	private String provinceText;

	/**
	 * 所属市编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘所属市’位数不能超过9！")
	@Column(name = "CITY")
	private Integer city;

	private String cityText;

	/**
	 * 所属区县编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘所属区县’位数不能超过9！")
	@Column(name = "DISTRICT")
	private Integer district;

	private String districtText;

	/**
	 * 地址
	 **/
	@Length(max = 150, message = "‘地址’长度不能超过150！")
	@Column(name = "ADDRESS")
	private String address;

	/**
	 * 公司电话
	 **/
	@Length(max = 20, message = "‘公司电话’长度不能超过20！")
	@Column(name = "TEL")
	private String tel;

	/**
	 * 公司传真
	 **/
	@Length(max = 20, message = "‘公司传真’长度不能超过20！")
	@Column(name = "FAX")
	private String fax;

	/**
	 * 公司主页
	 **/
	@Length(max = 100, message = "‘公司主页’长度不能超过100！")
	@Column(name = "WEBSITE")
	private String website;

	/**
	 * 法定代表人
	 */
	@Length(max = 30, message = "‘法定代表人’内容长度不能超过30")
	@Column(name = "LEGAL_REPRESENTATIVE")
	private String legalRepresentative;

	/**
	 * 法人电话
	 **/
	@Length(max = 20, message = "‘法人电话’长度不能超过20！")
	@Column(name = "LR_NUMBER")
	private String lrNumber;

	/**
	 * 成立日期
	 */
	@Column(name = "ESTABLISHMENT_DATE")
	private LocalDate establishmentDate;

	/**
	 * 注册资本
	 */
	@Digits(integer = 15, fraction = 2, message = "‘注册资本’整数位数最大15,小数位数最大2")
	@Column(name = "REGISTERED_CAPITAL")
	private BigDecimal registeredCapital;

	/**
	 * 企业性质编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘企业性质’位数不能超过9！")
	@Column(name = "NATURE")
	private Integer nature;

	private String natureText;

	/**
	 * 所属行业编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘所属行业’位数不能超过9！")
	@Column(name = "INDUSTRY")
	private Integer industry;

	private String industryText;

	/**
	 * 上市情况编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘上市情况’位数不能超过9！")
	@Column(name = "LISTING_STATUS")
	private Integer listingStatus;

	private String listingStatusText;

	/**
	 * 上级单位
	 **/
	@Length(max = 30, message = "‘上级单位’长度不能超过30！")
	@Column(name = "SUPERIOR")
	private String superior;

	/**
	 * 是否集团企业
	 **/
	@Column(name = "IS_GROUP")
	private Boolean isGroup;

	/**
	 * 公司规模编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘公司规模’位数不能超过9！")
	@Column(name = "SIZE")
	private Integer size;

	private String sizeText;

	/**
	 * 总资产（万元）
	 */
	@Digits(integer = 15, fraction = 2, message = "‘总资产（万元）’整数位数最大15,小数位数最大2")
	@Column(name = "TOTAL_ASSETS")
	private BigDecimal totalAssets;

	/**
	 * 年产值（万元）
	 */
	@Digits(integer = 15, fraction = 2, message = "‘年产值（万元）’整数位数最大15,小数位数最大2")
	@Column(name = "ANNUAL_OUTPUT_VALUE")
	private BigDecimal annualOutputValue;

	/**
	 * 年产值规模编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘年产值规模’位数不能超过9！")
	@Column(name = "ANNUAL_OUTPUT_SCALE")
	private Integer annualOutputScale;

	private String annualOutputScaleText;

	/**
	 * 用工规模编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘用工规模’位数不能超过9！")
	@Column(name = "EMPLOYEE_SIZE")
	private Integer employeeSize;

	private String employeeSizeText;

	/**
	 * 主营业务
	 **/
	@Length(max = 200, message = "‘主营业务’长度不能超过200！")
	@Column(name = "MAIN_BUSINESS")
	private String mainBusiness;

	/**
	 * 经营状况编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘经营状况’位数不能超过9！")
	@Column(name = "OPERATE_STATUS")
	private Integer operateStatus;

	private String operateStatusText;

	/**
	 * 市场地位编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘市场地位’位数不能超过9！")
	@Column(name = "MARKET_POSITION")
	private Integer marketPosition;

	private String marketPositionText;

	/**
	 * 发展潜力编号
	 */
	@Digits(integer = 9, fraction = 0, message = "‘发展潜力’位数不能超过9！")
	@Column(name = "POTENTIAL")
	private Integer potential;

	private String potentialText;

	/**
	 * 是否有IT部门
	 **/
	@Column(name = "HAS_IT_DEPARTMENT")
	private Boolean hasItDepartment;

	/**
	 * 信息主管
	 **/
	@Length(max = 20, message = "‘信息主管’长度不能超过20！")
	@Column(name = "IT_MANAGER")
	private String itManager;

	@Length(max = 20, message = "‘信息主管电话’长度不能超过20！")
	@Column(name = "IT_MANAGER_NUMBER")
	private String itManagerNumber;

	/**
	 * 信息化状况
	 **/
	@Length(max = 200, message = "‘信息化状况’长度不能超过200！")
	@Column(name = "IT_SITUATION")
	private String itSituation;

	/**
	 * 主要软件品牌
	 **/
	@Length(max = 1000, message = "‘主要软件品牌’长度不能超过1000！")
	@Column(name = "MAIN_SOFTWARE_BRAND")
	private String mainSoftwareBrand;

	/**
	 * 主要软件产品线
	 **/
	@Length(max = 1000, message = "‘主要软件产品线’长度不能超过1000！")
	@Column(name = "MAIN_SOFTWARE_LINE")
	private String mainSoftwareLine;

	/**
	 * 线索跟踪人
	 */
	@Length(max = 60, message = "‘线索跟踪人’长度不能超过60！")
	@Column(name = "ORIGIN_TRACKER")
	private String originTracker;

	/**
	 * 销售对应人
	 */
	@Length(max = 64, message = "‘销售对应人’长度不能超过20！")
	@Column(name = "SALESMAN")
	private String salesman;

	/**
	 * 原销售对应人
	 */
	@Length(max = 64, message = "‘原销售对应人’长度不能超过20！")
	@Column(name = "OLD_SALESMAN")
	private String oldSalesman;

	/**
	 * 服务对应人
	 */
	@Length(max = 64, message = "‘服务对应人’长度不能超过20！")
	@Column(name = "SERVICEMAN")
	private String serviceman;

	/**
	 * 创建时间
	 **/
	@NotNull(message = "‘创建时间’不可为空！")
	@Column(name = "CREATE_TIME")
	private LocalDateTime createTime;

	/**
	 * 备注
	 **/
	@Length(max = 800, message = "‘备注’长度不能超过800！")
	@Column(name = "REMARK")
	private String remark;

	/**
	 *  联系人集合
	 */
	private List<Contact> contacts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUscc() {
		return uscc;
	}

	public void setUscc(String uscc) {
		this.uscc = uscc;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public String getOriginText() {
		this.originText = DictUtils.getDictName(this.getOrigin(), "CRM_CSR_DATA_ORIGIN", "");
		return originText;
	}

	public void setOriginText(String originText) {
		this.originText = originText;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getAreaText() {
		this.areaText = DictUtils.getDictName(this.getArea(), "COM_GEO_AREA", "");
		return areaText;
	}

	public void setAreaText(String areaText) {
		this.areaText = areaText;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvinceText() {
		this.provinceText = DictUtils.getDictName(this.getProvince(), "COM_GEO_PROVINCE", "");
		return provinceText;
	}

	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityText() {
		this.cityText = DictUtils.getDictName(this.getCity(), "COM_GEO_CITY", "");
		return cityText;
	}

	public void setCityText(String cityText) {
		this.cityText = cityText;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public String getDistrictText() {
		this.districtText = DictUtils.getDictName(this.getDistrict(), "COM_GEO_DISTRICT", "");
		return districtText;
	}

	public void setDistrictText(String districtText) {
		this.districtText = districtText;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLrNumber() {
		return lrNumber;
	}

	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public LocalDate getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(LocalDate establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public BigDecimal getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(BigDecimal registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public Integer getNature() {
		return nature;
	}

	public void setNature(Integer nature) {
		this.nature = nature;
	}

	public String getNatureText() {
		this.natureText = DictUtils.getDictName(this.getNature(), "COM_ENT_NATURE", "");
		return natureText;
	}

	public void setNatureText(String natureText) {
		this.natureText = natureText;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getIndustryText() {
		this.industryText = DictUtils.getDictName(this.getIndustry(), "COM_ENT_INDUSTRY", "");
		return industryText;
	}

	public void setIndustryText(String industryText) {
		this.industryText = industryText;
	}

	public Integer getListingStatus() {
		return listingStatus;
	}

	public void setListingStatus(Integer listingStatus) {
		this.listingStatus = listingStatus;
	}

	public String getListingStatusText() {
		this.listingStatusText = DictUtils.getDictName(this.getListingStatus(), "COM_ENT_LISTING_STATUS", "");
		return listingStatusText;
	}

	public void setListingStatusText(String listingStatusText) {
		this.listingStatusText = listingStatusText;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean group) {
		isGroup = group;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getSizeText() {
		this.sizeText = DictUtils.getDictName(this.getSize(), "COM_ENT_SIZE", "");
		return sizeText;
	}

	public void setSizeText(String sizeText) {
		this.sizeText = sizeText;
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getAnnualOutputValue() {
		return annualOutputValue;
	}

	public void setAnnualOutputValue(BigDecimal annualOutputValue) {
		this.annualOutputValue = annualOutputValue;
	}

	public Integer getAnnualOutputScale() {
		return annualOutputScale;
	}

	public void setAnnualOutputScale(Integer annualOutputScale) {
		this.annualOutputScale = annualOutputScale;
	}

	public String getAnnualOutputScaleText() {
		this.annualOutputScaleText = DictUtils.getDictName(this.getAnnualOutputScale(), "COM_ENT_ANNUAL_OUTPUT_SCALE", "");
		return annualOutputScaleText;
	}

	public void setAnnualOutputScaleText(String annualOutputScaleText) {
		this.annualOutputScaleText = annualOutputScaleText;
	}

	public Integer getEmployeeSize() {
		return employeeSize;
	}

	public void setEmployeeSize(Integer employeeSize) {
		this.employeeSize = employeeSize;
	}

	public String getEmployeeSizeText() {
		this.employeeSizeText = DictUtils.getDictName(this.getEmployeeSize(), "COM_ENT_EMPLOYEE_SIZE", "");
		return employeeSizeText;
	}

	public void setEmployeeSizeText(String employeeSizeText) {
		this.employeeSizeText = employeeSizeText;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public Integer getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Integer operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getOperateStatusText() {
		this.operateStatusText = DictUtils.getDictName(this.getOperateStatus(), "COM_ENT_OPERATE_STATUS", "");
		return operateStatusText;
	}

	public void setOperateStatusText(String operateStatusText) {
		this.operateStatusText = operateStatusText;
	}

	public Integer getMarketPosition() {
		return marketPosition;
	}

	public void setMarketPosition(Integer marketPosition) {
		this.marketPosition = marketPosition;
	}

	public String getMarketPositionText() {
		this.marketPositionText = DictUtils.getDictName(this.getMarketPosition(), "COM_ENT_MARKET_POSITION", "");
		return marketPositionText;
	}

	public void setMarketPositionText(String marketPositionText) {
		this.marketPositionText = marketPositionText;
	}

	public Integer getPotential() {
		return potential;
	}

	public void setPotential(Integer potential) {
		this.potential = potential;
	}

	public String getPotentialText() {
		this.potentialText = DictUtils.getDictName(this.getPotential(), "COM_ENT_POTENTIAL", "");
		return potentialText;
	}

	public void setPotentialText(String potentialText) {
		this.potentialText = potentialText;
	}

	public Boolean getHasItDepartment() {
		return hasItDepartment;
	}

	public void setHasItDepartment(Boolean hasItDepartment) {
		this.hasItDepartment = hasItDepartment;
	}

	public String getItManager() {
		return itManager;
	}

	public void setItManager(String itManager) {
		this.itManager = itManager;
	}

	public String getItManagerNumber() {
		return itManagerNumber;
	}

	public void setItManagerNumber(String itManagerNumber) {
		this.itManagerNumber = itManagerNumber;
	}

	public String getItSituation() {
		return itSituation;
	}

	public void setItSituation(String itSituation) {
		this.itSituation = itSituation;
	}

	public String getMainSoftwareBrand() {
		return mainSoftwareBrand;
	}

	public void setMainSoftwareBrand(String mainSoftwareBrand) {
		this.mainSoftwareBrand = mainSoftwareBrand;
	}

	public String getMainSoftwareLine() {
		return mainSoftwareLine;
	}

	public void setMainSoftwareLine(String mainSoftwareLine) {
		this.mainSoftwareLine = mainSoftwareLine;
	}

	public String getOriginTracker() {
		return originTracker;
	}

	public void setOriginTracker(String originTracker) {
		this.originTracker = originTracker;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getOldSalesman() {
		return oldSalesman;
	}

	public void setOldSalesman(String oldSalesman) {
		this.oldSalesman = oldSalesman;
	}

	public String getServiceman() {
		return serviceman;
	}

	public void setServiceman(String serviceman) {
		this.serviceman = serviceman;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
}