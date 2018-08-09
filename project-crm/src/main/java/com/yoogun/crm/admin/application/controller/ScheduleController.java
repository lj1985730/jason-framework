/*
 * ScheduleController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.ScheduleService;
import com.yoogun.crm.admin.application.vo.ScheduleVo;
import com.yoogun.crm.admin.domain.model.Schedule;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 日程管理-控制器<br/>
 * @author Sheng Baoyu at 2018-03-20 14:59:38
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/schedule")
class ScheduleController extends BaseCurdController<Schedule> {

	@Resource
	private ScheduleService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:schedule:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/schedule");
	}

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		ScheduleVo vo = new ScheduleVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

	/**
	 * 导出列表excel
	 * @return 文件对象
	 */
	@RequiresPermissions("crm:schedule:view")
	@RequestMapping(value = { "/listExcel" }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportListExcel() {
		return super.createFileAttachmentResponse(service.export(), "日程信息表.xlsx");
	}
}