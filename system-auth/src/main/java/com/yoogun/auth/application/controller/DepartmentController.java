package com.yoogun.auth.application.controller;


import com.yoogun.auth.application.service.DepartmentService;
import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-部门-控制层
 * @author Liu Jun at 2016-8-17 00:15:47
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/department")
class DepartmentController extends BaseCurdController<Department> {

	@Resource
	private DepartmentService service;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/homeView" })
	public String home() {
		return pageView("/auth/department");
	}

	/**
	 * 页面的首页_new
	 */
	@RequestMapping(value = { "/homeViewNew" })
	public String homeNew() {
		return pageView("/auth/departmentNew");
	}
	
	/**
	 * 查询机构
	 * @return 机构
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchTree() {

		//构建树根节点（企业）
		Tenant tenant = AuthCache.tenant();
		if(tenant == null) {
			return null;
		}
		Department department = new Department();
		department.setName(tenant.getName());
		department.setParentId("#");
		department.setSortNumber(0);
		department.setLevel(0);
		department.setId(tenant.getId());
		List<Department> list = new ArrayList<>();
		list.add(department);
		//查询部门
		list.addAll(service.searchAllSortable("sort_number",""));

		return new JsonResult(list);
	}
	
	/**
	 * 按id查询
	 * @param id 主键
	 * @return 部门
	 */
	@RequestMapping(value = { "/{id}" })
	@ResponseBody
	public JsonResult search(@PathVariable("id") String id) {
		return new JsonResult(service.searchById(id));
	}

	/**
	 * 部门启用/停用
	 * @param departmentId 部门Id
	 * @param enabled true 启用；false 停用；
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{departmentId}/trigger" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult triggerEnable(@PathVariable("departmentId") String departmentId, Boolean enabled) {
		service.triggerEnabled(departmentId, enabled);
		return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
	}

	/**
	 * 取当前节点同级的最大排序
	 * @param parentId 父节点Id
	 * @return 最大排序号
	 */
	@RequestMapping(value = { "/maxSort" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult searchMaxSort(String parentId) {
		return new JsonResult(service.searchMaxSort(parentId));
	}
}