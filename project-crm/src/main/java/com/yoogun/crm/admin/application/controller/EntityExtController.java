/*
 * EntityExtController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.crm.admin.application.service.EntityExtService;
import com.yoogun.crm.admin.application.vo.EntityExtVo;
import com.yoogun.crm.admin.domain.model.EntityExt;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 客户附加信息-控制器<br/>
 * @author Wang Chong at 2018-03-22 10:13:42
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/entityExt")
class EntityExtController extends BaseCurdController<EntityExt> {
	@Resource
	private EntityExtService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:entityExt:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/entityExt");
	}

	/**
	 * 分页查询列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		EntityExtVo vo = new EntityExtVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

	/**
	 * 根据客户id查询客户附加信息
	 * @return 该用户的附加信息列表
	 */
	@RequestMapping(value = "/searchByEntityId", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchByCusId(HttpServletRequest request){
		EntityExtVo vo = new EntityExtVo(request);
		return new JsonResult(service.searchByCusId(vo.getEntityId()));
	}

	/**
	 * 客户附加信息保存方法
	 * @param entityExts 附加信息集合
	 */
	@RequestMapping(value = "/ext", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody List<EntityExt> entityExts){
		service.create(entityExts);
		return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
	}

	/**
	 * 客户附加信息更新方法
	 * @param entityExts 附加信息集合
	 */
	@RequestMapping(value = "/ext", method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody List<EntityExt> entityExts) {
		service.modify(entityExts);
		return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
	}
}