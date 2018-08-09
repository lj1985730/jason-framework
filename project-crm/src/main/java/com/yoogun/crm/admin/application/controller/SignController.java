/*
 * SignController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.SignService;
import com.yoogun.crm.admin.application.vo.SignVo;
import com.yoogun.crm.admin.domain.model.Sign;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 签到管理-控制器<br/>
 * @author Wang chong at 2018-03-06 15:39:52
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/sign")
class SignController extends BaseCurdController<Sign> {
	@Resource
	private SignService service;

	/**
	 * 页面的首页_PC端查询统计页面
	 */
	@RequiresPermissions("crm:sign:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/sign");
	}

	/**
	 * 页面的首页_手机端填报页面
	 */
	@RequestMapping(value = {"/phoneHomeView" }, method = RequestMethod.GET)
	public String phoneHome() {
		return pageView("/crm/admin/phoneSign");
	}

	/**
	 * 服务端获取签到信息：公司、部门、签到人、时间
	 */
	@RequestMapping(value = {"/getSignInfo"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult getSignInfo(HttpServletRequest request) {
		SignVo vo = new SignVo(request);
		return new JsonResult(service.getSignInfo(vo));
	}

	/**
	 * 签到日期判断是否签到过
	 */
	@RequestMapping(value = {"/isSigned"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult isSigned(HttpServletRequest request){
		SignVo vo = new SignVo(request);
		return new JsonResult(service.isSigned(vo));
	}


	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		SignVo vo = new SignVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

}