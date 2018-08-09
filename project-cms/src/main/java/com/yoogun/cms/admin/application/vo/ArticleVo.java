/*
 * ArticleVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * CMS-文章-查询VO<br/>
 * @author Liu Jun at 2018-6-5 14:35:23
 * @since v1.0.0
 */
public final class ArticleVo extends TableParam {

	public ArticleVo(HttpServletRequest request) {
		super(request);
	}
}