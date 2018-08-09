/*
 * ExtController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.ExtService;
import com.yoogun.crm.admin.application.vo.ExtVo;
import com.yoogun.crm.admin.domain.model.Ext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 附加信息-控制器<br/>
 * @author Wang Chong at 2018-03-22 10:11:47
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/ext")
class ExtController extends BaseCurdController<Ext> {
	@Resource
	private ExtService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:ext:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/ext");
	}

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		ExtVo vo = new ExtVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

	/**
	 * 根据主表名字查询对应附加信息
	 * @return 符合条件的列表数据
	 */
	@RequestMapping(value = "/searchByTable", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchByTable(HttpServletRequest request){
		ExtVo vo = new ExtVo(request);
		return new JsonResult(service.searchByProp("tableName",vo.getTableName(), AuthCache.tenantId()));
	}
}