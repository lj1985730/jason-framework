package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.QuickMenuService;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 权限-便捷菜单-控制层
 * @author Liu Jun at 2016-9-22 21:39:42
 * @since v1.0.0
 */
@Controller
@RequestMapping(value="**/auth/module")
class ModuleController extends BaseController {

	@Resource
	private QuickMenuService service;

	/**
	 * 获取全部快捷菜单
	 * @return 快捷菜单数据集
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult findMenus(HttpServletRequest request) {
		
		//返回数据集
		Map<String, List<Menu>> resMap = new HashMap<>();
		
		//加入上次访问结果集
//		resMap.put("last", service.getLastMenu(request));
		
		//加入快速访问结果集
		resMap.put("quick", service.getQuickMenu());

		return new JsonResult(resMap);
	}

	/**
	 * 根据主键查询单条数据
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult load(@PathVariable("id") String id) {
		return new JsonResult(service.searchById(id));
	}

	/**
	 * 新增数据
	 * @param bodyMap 表单数据
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Map<String, String[]> bodyMap) {
		service.createQuickMenu(bodyMap.get("menuIds"));
		return new JsonResult("保存成功！");
	}
}