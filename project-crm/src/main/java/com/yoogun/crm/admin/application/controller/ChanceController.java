/*
 * ChanceController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.ChanceService;
import com.yoogun.crm.admin.application.service.ScheduleService;
import com.yoogun.crm.admin.application.vo.ChanceVo;
import com.yoogun.crm.admin.application.vo.ScheduleVo;
import com.yoogun.crm.admin.domain.model.Chance;
import com.yoogun.crm.admin.domain.model.Schedule;
import com.yoogun.utils.infrastructure.ExportCacheUtils;
import com.yoogun.utils.infrastructure.excel.ExcelExporter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 商机-控制器<br/>
 * @author Sheng Baoyu at 2018-03-19 10:18:11
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/chance")
class ChanceController extends BaseCurdController<Chance> {

	@Resource
	private ChanceService service;

	@Resource
	private ScheduleService scheduleService;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:chance:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/chance");
	}

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		ChanceVo vo = new ChanceVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

	/**
	 * 查询客户下日程分页
	 * @param id 客户ID
	 * @return 客户下日程分页
	 */
	@RequestMapping(value = { "/{id}/schedules-page" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchSchedulePage(HttpServletRequest request, @PathVariable("id") String id) {
		ScheduleVo vo = new ScheduleVo(request, null, id);
		return new JsonResult(scheduleService.pageSearch(vo));
	}

	/**
	 * 导出列表excel
	 * @return 文件对象
	 */
	@RequiresPermissions("crm:chance:view")
	@RequestMapping(value = { "/listExcel" }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportListExcel() {
		return super.createFileAttachmentResponse(service.export(), "商机信息表.xlsx");
	}

}