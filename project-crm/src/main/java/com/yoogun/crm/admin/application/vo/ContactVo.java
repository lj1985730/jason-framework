/*
 * CustomerVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 客户联系人-查询VO<br/>
 * @author Liu Jun at 2018-4-25 17:11:17
 * @since v1.0.0
 */
public final class ContactVo extends TableParam {

	private String customerId;

	public ContactVo(HttpServletRequest request) {
		super(request);
		this.customerId = request.getParameter("customerId");
	}

	public ContactVo(HttpServletRequest request, String customerId) {
		super(request);
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}
}