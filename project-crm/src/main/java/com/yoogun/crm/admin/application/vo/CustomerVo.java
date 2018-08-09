/*
 * CustomerVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * Customer-查询VO<br/>
 * @author Liu Jun at 2018-7-24 13:40:33
 * @since v1.0.0
 */
public final class CustomerVo extends TableParam {

	private String name;	//客户ID
	private String origin;	//数据来源
	private String area;	//所属区域
	private String address;
	private String province;
	private String city;
	private String district;
	private String tracker;
	private String legalRepresentative;
	private String establishmentDate;
	private String nature;
	private String industry;
	private String minRegisteredCapital;
	private String maxRegisteredCapital;
	private String listingStatus;
	private String isGroup;
	private String size;
	private String minTotalAssets;
	private String maxTotalAssets;
	private String employeeSize;
	private String annualOutputScale;
	private String minAnnualOutputValue;
	private String maxAnnualOutputValue;
	private String operateStatus;
	private String marketPosition;
	private String potential;

	public CustomerVo(HttpServletRequest request) {
		super(request);
		this.name = request.getParameter("name");
		this.origin = request.getParameter("origin");
		this.area = request.getParameter("area");
		this.address = request.getParameter("address");
		this.province = request.getParameter("province");
		this.city = request.getParameter("city");
		this.district = request.getParameter("district");
		this.tracker = request.getParameter("tracker");
		this.legalRepresentative = request.getParameter("legalRepresentative");
		this.establishmentDate = request.getParameter("establishmentDate");
		this.nature = request.getParameter("nature");
		this.industry = request.getParameter("industry");
		this.minRegisteredCapital = request.getParameter("minRegisteredCapital");
		this.maxRegisteredCapital = request.getParameter("maxRegisteredCapital");
		this.listingStatus = request.getParameter("listingStatus");
		this.isGroup = request.getParameter("isGroup");
		this.size = request.getParameter("size");
		this.minTotalAssets = request.getParameter("minTotalAssets");
		this.maxTotalAssets = request.getParameter("maxTotalAssets");
		this.employeeSize = request.getParameter("employeeSize");
		this.annualOutputScale = request.getParameter("annualOutputScale");
		this.minAnnualOutputValue = request.getParameter("minAnnualOutputValue");
		this.maxAnnualOutputValue = request.getParameter("maxAnnualOutputValue");
		this.operateStatus = request.getParameter("operateStatus");
		this.marketPosition = request.getParameter("marketPosition");
		this.potential = request.getParameter("potential");
	}

	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public String getArea() {
		return area;
	}

	public String getAddress() {
		return address;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getDistrict() {
		return district;
	}

	public String getTracker() {
		return tracker;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public String getEstablishmentDate() {
		return establishmentDate;
	}

	public String getNature() {
		return nature;
	}

	public String getIndustry() {
		return industry;
	}

	public String getMinRegisteredCapital() {
		return minRegisteredCapital;
	}

	public String getMaxRegisteredCapital() {
		return maxRegisteredCapital;
	}

	public String getListingStatus() {
		return listingStatus;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public String getSize() {
		return size;
	}

	public String getMinTotalAssets() {
		return minTotalAssets;
	}

	public String getMaxTotalAssets() {
		return maxTotalAssets;
	}

	public String getEmployeeSize() {
		return employeeSize;
	}

	public String getAnnualOutputScale() {
		return annualOutputScale;
	}

	public String getMinAnnualOutputValue() {
		return minAnnualOutputValue;
	}

	public String getMaxAnnualOutputValue() {
		return maxAnnualOutputValue;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public String getMarketPosition() {
		return marketPosition;
	}

	public String getPotential() {
		return potential;
	}
}