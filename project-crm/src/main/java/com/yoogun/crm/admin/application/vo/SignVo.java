/*
 * SignVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * Sign-查询VO<br/>
 * @author Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 */
public final class SignVo extends TableParam {

	public SignVo(HttpServletRequest request) {
		super(request);
		// 初始化其它属性
		this.signDate = request.getParameter("signDate");
		this.inOrOut = request.getParameter("inOrOut");

	}

	/**
	 * 签到日期
	 */
	private String signDate;

	/**
	 * 签退或者签到标识，0表示签到，1表示签退
	 */
	private String inOrOut;

	public String getInOrOut() {
		return inOrOut;
	}

	public String getSignDate() {
		return signDate;
	}

}