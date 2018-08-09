/*
 * DocController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.DocService;
import com.yoogun.crm.admin.application.vo.DocVo;
import com.yoogun.crm.admin.domain.model.Doc;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 文档管理-控制器<br/>
 * @author Wang chong at 2018-03-08 13:50:37
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/doc")
class DocController extends BaseCurdController<Doc> {
	@Resource
	private DocService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:doc:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/doc");
	}

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		DocVo vo = new DocVo(request);
		return new JsonResult(service.pageSearch(vo));
	}
}