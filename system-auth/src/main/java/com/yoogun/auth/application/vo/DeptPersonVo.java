/*
 * AuthdeptpersonVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 部门人员-查询VO<br/>
 * @author Wang Chong at 2018-04-19 11:29:59
 * @since v1.0.0
 */
public final class DeptPersonVo extends TableParam {

	private String deptId;

	public DeptPersonVo(HttpServletRequest request) {
		super(request);
		this.deptId = request.getParameter("deptId");
	}

	public String getDeptId() {
		return deptId;
	}
}