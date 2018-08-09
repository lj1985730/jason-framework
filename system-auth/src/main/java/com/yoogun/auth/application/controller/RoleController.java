package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.MenuService;
import com.yoogun.auth.application.service.RoleRlatService;
import com.yoogun.auth.application.service.RoleService;
import com.yoogun.auth.application.vo.RoleVo;
import com.yoogun.auth.domain.model.Role;
import com.yoogun.auth.domain.model.RoleRlat;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 权限-角色-控制层
 * @author Liu Jun at 2016-8-16 20:47:42
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/role")
class RoleController extends BaseCurdController<Role> {
	
	@Resource
	private RoleService service;

	@Resource
	private MenuService menuService;

	@Resource
	private RoleRlatService roleRlatService;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/auth/role");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		RoleVo vo = new RoleVo(request);
		return new JsonResult(service.pageSearch(vo));
    }

	/**
	 * 角色启用/禁用
	 * @param roleId 角色Id
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{roleId}/toggleEnable" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult toggleEnable(@PathVariable("roleId") String roleId) {
		service.toggleEnable(roleId);
		return new JsonResult();
	}

	/**
	 * 获取全部可分配的菜单和按钮
	 * @return 全部可分配的菜单和按钮
	 */
	@RequestMapping(value = { "/menusAndButtons" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchAllAssignableMenuAndBtn() {
		return new JsonResult(service.searchAllAssignableMenuAndBtn());
	}

	/**
	 * 根据角色查询菜单和按钮
	 * @param roleId 角色Id
	 * @return 角色菜单和按钮
	 */
	@RequestMapping(value = { "/{roleId}/menusAndButtons" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchMenuAndButtonByRole(@PathVariable("roleId") String roleId) {
		return new JsonResult(service.searchMenuAndButtonByRole(roleId));
	}

	/**
	 * 设置角色菜单以及按钮
	 * @param roleId 角色ID
	 * @param datas 请求数据
	 * @return 操作结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/{roleId}/menusAndButtons" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult bindMenuAndButtonToRole(@PathVariable String roleId, @RequestBody Map<String, List<String>> datas) {
		List<String> menuIds = datas.get("menuIds");
		List<String> buttonIds = datas.get("buttonIds");
		service.bindMenuAndButtonToRole(roleId, menuIds, buttonIds);
		return new JsonResult(true, "保存成功！");
	}

	/**
	 * 设置角色关系
	 * @param roleId 角色id
	 * @param datas 下级角色,key = roleRlats
	 */
	@RequestMapping(value = { "/{roleId}/roleRlat" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setRoleRlat(@PathVariable String roleId, @RequestBody Map<String,List<RoleRlat>> datas){
		List<RoleRlat> roleRlats = datas.get("roleRlats");
		roleRlatService.setRoleRlat(roleId,roleRlats);
		return new JsonResult(true, "保存成功！");
	}
}