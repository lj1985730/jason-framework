/*
 * ArticleController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.cms.admin.application.service.ArticleService;
import com.yoogun.cms.admin.domain.model.Subject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;


/**
 * CMS-文章-控制器<br/>
 * @author Liu Jun at 2018-6-5 14:37:21
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/cms/article")
class ArticleController extends BaseCurdController<Subject> {

	@Resource
	private ArticleService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/cms/admin/article");
	}

}