/*
 * SubjectController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.cms.admin.application.service.SubjectService;
import com.yoogun.cms.admin.domain.model.Subject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;


/**
 * CMS-主题（栏目）-控制器<br/>
 * @author Liu Jun at 2018-6-5 14:16:37
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/cms/subject")
class SubjectController extends BaseCurdController<Subject> {

	@Resource
	private SubjectService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("cms:subject:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/cms/admin/subject");
	}

}