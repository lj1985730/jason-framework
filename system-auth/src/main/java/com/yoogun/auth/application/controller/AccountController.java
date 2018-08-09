package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.AccountRoleService;
import com.yoogun.auth.application.service.AccountService;
import com.yoogun.auth.application.vo.RoleVo;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Person;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.application.vo.TableParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * 权限-账户-控制层
 * @author Liu Jun at 2016-8-14 23:29:36
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/account")
class AccountController extends BaseController {
	
	@Resource
	private AccountService service;

	@Resource
	private AccountRoleService accountRoleService;

	/**
	 * 页面的首页
	 */
	@RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/auth/account");
	}

	/**
	 * 查询分页列表
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchPage(HttpServletRequest request) {
		TableParam tableParam = new TableParam(request);
		return new JsonResult(service.pageSearch(tableParam, false));
	}

	/**
	 * 管理员查询分页列表,与searchPage不同的是，查询结果中包含管理员
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult adminSearchPage(HttpServletRequest request) {
		TableParam tableParam = new TableParam(request);
		return new JsonResult(service.pageSearch(tableParam, true));
	}

	/**
	 * 获取当前登录账户
	 * @return 当前登录账户
	 */
	@RequestMapping(value = { "/currentName" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchCurrentName() {
		Person person = AuthCache.person();
		return new JsonResult(person == null ? null : person.getName());
	}

	/**
	 * 新增账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody Account account) {
		//单独设置UUID,方便ID_赋值
		account.setId(UUID.randomUUID().toString().toUpperCase());
		service.create(account);
		return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
	}

	/**
	 * 更新账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody Account account) {
		service.modify(account);
		return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
	}

	/**
	 * 删除账户
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult remove(@PathVariable("id") String id) {
		service.remove(id, AuthCache.accountId());
		return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
	}

	/**
	 * 修改密码
	 * @param passMap 密码
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/modifyPassword" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult modifyPassword(@RequestBody Map<String, String> passMap) {
		String oldPass = HtmlUtils.htmlEscape(passMap.get("oldPass"));
		String newPass = HtmlUtils.htmlEscape(passMap.get("newPass"));
		service.modifyPassword(oldPass, newPass);
		return new JsonResult(true, "修改成功！");
	}

	/**
	 * 密码重置
	 * @param accountId 账户Id
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{accountId}/resetPassword" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult resetPassword(@PathVariable("accountId") String accountId) {
		service.resetPassword(accountId);
		return new JsonResult();
	}

	/**
	 * 密码解锁
	 * @param accountId 账户Id
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{accountId}/unlock" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult unlock(@PathVariable("accountId") String accountId) {
		service.unlock(accountId);
		return new JsonResult();
	}

	/**
	 * 账户启用/禁用
	 * @param accountId 账户Id
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{accountId}/toggleEnable" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult toggleEnable(@PathVariable("accountId") String accountId) {
		service.toggleEnable(accountId);
		return new JsonResult();
	}
	
	/**
	 * 根据personId检验人员是否注册过登录账户
	 * @param personId	人员Id
	 * @return 检验结果
	 */
	@RequestMapping(value = { "/person/{personId}/accountNames" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchAccountNamesByPerson(@PathVariable("personId") String personId) {
		return new JsonResult(service.searchAccountNamesByPerson(personId));
	}

	/**
	 * 查询全部数据，在选择角色功能使用
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/roles" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchRoles(HttpServletRequest request) {
		RoleVo vo = new RoleVo(request);
		return new JsonResult(service.searchRoles(vo));
	}

	/**
	 * 查询账户角色
	 * @param accountId 用户Id
	 * @return 用户角色
	 */
	@RequestMapping(value = { "/{accountId}/roles" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchAccountRole(@PathVariable("accountId") String accountId) {
		return new JsonResult(accountRoleService.searchRoleByAccount(accountId));
	}

	/**
	 * 修改账户角色
	 * @param request 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/roles" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setAccountRole(HttpServletRequest request, @RequestBody Map<String, String> params) {
		String accountId = params.get("accountId");
		String roleIds = params.get("roleIds");
		String[] roleIdArr = roleIds.split(",");
		accountRoleService.setAccountRole(accountId, roleIdArr);
		return new JsonResult(true, "保存成功！");
	}

	/**
	 * 设置租户ID，超管可操作
	 * @param tenantId 请求体
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/setTenant/{tenantId}" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult setTenant(@PathVariable String tenantId) {
		service.updateAccountTenant(tenantId);
		return new JsonResult(true, "操作成功！");
	}
}