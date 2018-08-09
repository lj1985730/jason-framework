package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.DeptPersonService;
import com.yoogun.auth.application.service.PersonPostService;
import com.yoogun.auth.application.service.PersonService;
import com.yoogun.auth.application.vo.DeptPersonVo;
import com.yoogun.auth.application.vo.PersonVo;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 权限-人员-控制层
 * @author Liu Jun at 2016-8-16 22:13:53
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/person")
class PersonController extends BaseCurdController<Person> {

	@Resource
	private PersonService service;

	@Resource
	private PersonPostService personPostService;

	@Resource
	private DeptPersonService deptPersonService;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("auth:person:view")
	@RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/auth/person");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		PersonVo vo = new PersonVo(request);
		return new JsonResult(service.pageSearch(vo));
	}

	/**
	 * 根据部门id查询分页列表
	 * @return 部门下人员的分页数据
	 * @author wang chong
	 */
	@RequestMapping(value =  {"/searchPerByDept"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPerByDept(HttpServletRequest request){
		DeptPersonVo vo = new DeptPersonVo(request);
		return new JsonResult(deptPersonService.pageSearch(vo));
	}

	/**
	 * 查询人员所属岗位
	 * @param personId 人员ID
	 * @return 人员所属岗位
	 */
	@RequestMapping(value = { "/{personId}/posts" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPosts(@PathVariable String personId) {
		return new JsonResult(personPostService.searchPostByPerson(personId));
	}

	/**
	 * 设置人员所属岗位
	 * @param personId 人员ID
	 * @param datas 岗位关系集合key="posts"
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{personId}/posts" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult bindSubordinates(@PathVariable String personId, @RequestBody Map<String, String[]> datas) {
		personPostService.setPersonPost(personId, datas.get("posts"));
		return new JsonResult(true, "保存成功！");
	}

	/**
	 * 获取人员的兼职部门
	 * @param personId 人员ID
	 * @return 兼职部门
	 */
	@RequestMapping(value = { "/{personId}/partTimeDepartments" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPartTimeDepartments(@PathVariable String personId) {
		return new JsonResult(deptPersonService.searchPartTimeDepartments(personId));
	}
}