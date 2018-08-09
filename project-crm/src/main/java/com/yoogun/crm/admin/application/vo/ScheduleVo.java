/*
 * ScheduleVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * CRM-日程-查询VO<br/>
 * @author Liu Jun
 * @since v1.0.0
 */
public final class ScheduleVo extends TableParam {

	private String customerId;	//客户ID
	private String chanceId;	//商机ID

	public ScheduleVo(HttpServletRequest request) {
		super(request);
		this.customerId = request.getParameter("customerId");
		this.chanceId = request.getParameter("chanceId");
	}

	public ScheduleVo(HttpServletRequest request, String customerId) {
		super(request);
		this.customerId = customerId;
	}

	public ScheduleVo(HttpServletRequest request, String customerId, String chanceId) {
		super(request);
		this.customerId = customerId;
		this.chanceId = chanceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getChanceId() {
		return chanceId;
	}
}