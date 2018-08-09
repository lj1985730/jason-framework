/*
 * SubjectVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * CMS-主题（栏目）-查询VO<br/>
 * @author Liu Jun at 2018-6-5 14:09:21
 * @since v1.0.0
 */
public final class SubjectVo extends TableParam {

	public SubjectVo(HttpServletRequest request) {
		super(request);
	}
}