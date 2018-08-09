/*
 * ChanceController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.ChanceService;
import com.yoogun.crm.admin.application.vo.ChanceVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * CRM-图表-控制器<br/>
 * @author Liu Jun at 2018-5-11 14:51:24
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/chart")
class ChartController extends BaseController {

	@Resource
	private ChanceService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:chart:view")
	@RequestMapping(value = {"/homeView"}, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/chart");
	}

	/**
     * chart-人员商机对比
	 * @param request 查询体
	 * @return chart data
	 */
	@RequestMapping(value = {"/personChance"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPersonChanceBar(HttpServletRequest request) {
		ChanceVo vo = new ChanceVo(request);
		return new JsonResult(service.searchPersonChanceBar(vo));
	}

	/**
	 * chart-人员日程对比
	 * @param request 查询体
	 * @return chart data
	 */
	@RequestMapping(value = {"/personSchedule"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPersonScheduleBar(HttpServletRequest request) {
		ChanceVo vo = new ChanceVo(request);
		return new JsonResult(service.searchPersonScheduleBar(vo));
	}
}