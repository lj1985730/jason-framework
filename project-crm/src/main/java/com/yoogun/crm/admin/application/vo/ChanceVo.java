/*
 * ChanceVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * Chance-查询VO<br/>
 * @author Sheng Baoyu at 2018-03-19 10:18:11
 *  modify by Liu Jun at 2018-4-25 18:13:10 增加客户ID属性
 * @since v1.0.0
 */
public final class ChanceVo extends TableParam {

	private String customerId;

	private String beginDate;
	private String endDate;

	public ChanceVo(HttpServletRequest request) {
		super(request);
		this.customerId = request.getParameter("customerId");
		this.beginDate = request.getParameter("beginDate");
		this.endDate = request.getParameter("endDate");
	}

	public ChanceVo(HttpServletRequest request, String customerId) {
		super(request);
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getEndDate() {
		return endDate;
	}
}