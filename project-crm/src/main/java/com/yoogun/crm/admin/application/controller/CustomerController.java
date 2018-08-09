/*
 * CustomerController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.*;
import com.yoogun.crm.admin.application.vo.*;
import com.yoogun.crm.admin.domain.model.Customer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 客户-控制器<br/>
 * @author Sheng Baoyu at 2018-02-01 16:41:46
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/customer")
class CustomerController extends BaseCurdController<Customer> {

	@Resource
	private CustomerService service;

	@Resource
	private ContactService contactService;

	@Resource
	private InformationizationService informationizationService;

	@Resource
	private ChanceService chanceService;

	@Resource
	private ScheduleService scheduleService;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:customer:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/customer");
	}

    /**
     * 综合查询首页
     */
    @RequiresPermissions("crm:customer-query:view")
    @RequestMapping(value = {"/queryHomeView" }, method = RequestMethod.GET)
    public String queryHome() {
        return pageView("/crm/admin/customer-query");
    }

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequiresPermissions("crm:customer:view")
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		return new JsonResult(service.pageSearch(new CustomerVo(request)));
	}

	/**
	 *  根据客户获取联系人
	 * @return 联系人
	 */
	@RequestMapping(value = { "/{id}/contacts" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchContactByCustomer(@PathVariable("id") String id) {
		return new JsonResult(contactService.searchByCustomer(id));
	}

	/**
	 *  根据客户获取信息化详情
	 * @return 信息化详情
	 */
	@RequestMapping(value = { "/{id}/infos" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchInfoByCustomer(@PathVariable("id") String id) {
		return new JsonResult(informationizationService.searchByCustomer(id));
	}

	/**
	 * 通用新增操作
	 * @return 操作结果
	 */
	@RequiresPermissions("crm:customer:create")
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Customer entity) {
		String id = service.createAndReturnId(entity);
		return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED).setData(id);
	}

	/**
	 * 通用更新操作
	 * @return 操作结果
	 */
	@RequiresPermissions("crm:customer:update")
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Customer entity) {
		return super.modify(entity);
	}

	/**
	 * 通用删除操作
	 * @return 操作结果
	 */
	@RequiresPermissions("crm:customer:delete")
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult remove(@PathVariable("id") String id) {
		service.remove(id);
		return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
	}

	/**
	 * 导出列表excel
	 * @return 文件对象
	 */
	@RequiresPermissions("crm:customer:view")
	@RequestMapping(value = { "/listExcel" }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> exportListExcel() {
		return super.createFileAttachmentResponse(service.export(), "客户信息表.xlsx");
	}

	/**
	 * 查询客户下联系人分页
	 * @param id 客户ID
	 * @return 客户下联系人分页
	 */
	@RequestMapping(value = { "/{id}/contacts-page" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchContactPage(HttpServletRequest request, @PathVariable("id") String id) {
		ContactVo vo = new ContactVo(request, id);
		return new JsonResult(contactService.pageSearch(vo));
	}

	/**
	 * 查询客户下商机分页
	 * @param id 客户ID
	 * @return 客户下商机分页
	 */
	@RequestMapping(value = { "/{id}/chances-page" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchChancePage(HttpServletRequest request, @PathVariable("id") String id) {
		ChanceVo vo = new ChanceVo(request, id);
		return new JsonResult(chanceService.pageSearch(vo));
	}

	/**
	 * 查询客户下日程分页
	 * @param id 客户ID
	 * @return 客户下日程分页
	 */
	@RequestMapping(value = { "/{id}/schedules-page" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchSchedulePage(HttpServletRequest request, @PathVariable("id") String id) {
		ScheduleVo vo = new ScheduleVo(request, id);
		return new JsonResult(scheduleService.pageSearch(vo));
	}

	/**
	 * 提交审核
	 * @param id 客户ID
	 */
	@RequestMapping(value = { "/{id}/workflowCommit" }, method = RequestMethod.PUT)
	public JsonResult startCustomerAdmittanceWorkflow(@PathVariable("id") String id) {
		service.startCustomerAdmittanceWorkflow(id);
		return new JsonResult(JsonResult.ResultType.COMMIT_SUCCEED);
	}

	/**
	 * 取消提交审核
	 * @param id 客户ID
	 */
	@RequestMapping(value = { "/{id}/workflowUndoCommit" }, method = RequestMethod.PUT)
	public JsonResult cancelCustomerAdmittanceWorkflow(@PathVariable("id") String id) {
		service.cancelCustomerAdmittanceWorkflow(id);
		return new JsonResult(JsonResult.ResultType.UNDO_COMMIT_SUCCEED);
	}
}