package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.AccountService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 权限-登录-控制层
 * @author Liu Jun at 2016-8-3 00:05:52
 * @since v1.0.0
 */
@Controller
public class LoginController extends BaseController {

	public enum LoginMode {
		FRONT, BACK, APP
	}

	@Resource
	private AccountService accountService;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index() {
		return "redirect:/login";
	}

	/**
	 * 登录页
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView loginView() {
		Account account = new Account();
		return pageView("/login", "account", account);
	}

	/**
	 * 后台登录页
	 */
	@RequestMapping(value = { "/loginBack" }, method = RequestMethod.GET)
	public String backLoginView() {
		return pageView("/loginBack");
	}

	/**
	 * 前台登录动作
	 * @param userMap	登录请求信息
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doLogin(@RequestBody Map<String, String> userMap) {
		String name = HtmlUtils.htmlEscape(userMap.get("name"));
		if(StringUtils.isBlank(name)) {
			return new JsonResult(false, "用户名不能为空！");
		}
		String pass = HtmlUtils.htmlEscape(userMap.get("pass"));
		if(StringUtils.isBlank(pass)) {
			return new JsonResult(false, "密码不能为空！");
		}

		Subject subject = SecurityUtils.getSubject();	//shiro主体
		UsernamePasswordToken token = new UsernamePasswordToken(name, pass);

		try {
			subject.login(token);
			AuthCache.put(AuthCache.LoginInfo.MODE, LoginMode.FRONT);
			//缓存租户
			accountService.cacheAccountInfo(name);  //缓存登录信息
			return new JsonResult(true, "登录成功！", SecurityUtils.getSubject().getSession().getId());
		} catch (IncorrectCredentialsException ice) {
			throw new AuthenticationException("密码错误！", ice);
		}
	}

	/**
	 * 登录动作
	 * @param loginMap 提交表单数据集合
	 * @return 登录结果
	 */
	@RequestMapping(value = { "/loginBack" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doLoginBack(@RequestBody Map<String,String> loginMap) {
		String name = HtmlUtils.htmlEscape(loginMap.get("name"));
		if(StringUtils.isBlank(name)) {
			return new JsonResult(false, "用户名不能为空！");
		}
		String pass = HtmlUtils.htmlEscape(loginMap.get("pass"));
		if(StringUtils.isBlank(pass)) {
			return new JsonResult(false, "密码不能为空！");
		}

		Account account = accountService.searchByName(name);
		if (!account.isSuperAdmin()) {
			return new JsonResult(false, "非超管禁止后台登录！");
		}

		Subject subject = SecurityUtils.getSubject();	//shiro主体
		UsernamePasswordToken token = new UsernamePasswordToken(name, pass);

		try {
			subject.login(token);
			AuthCache.put(AuthCache.LoginInfo.MODE, LoginMode.BACK);
			accountService.cacheAccountInfo(account);  //缓存登录信息
			return new JsonResult(true, "登录成功！", SecurityUtils.getSubject().getSession().getId());
		} catch (IncorrectCredentialsException ice) {
			throw new AuthenticationException("密码错误！", ice);
		}
	}

	/**
	 * App登录动作
	 * @param loginMap 提交表单数据集合
	 * @return 登录结果
	 */
	@RequestMapping(value = { "/loginApp" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult doLoginApp(@RequestBody Map<String,String> loginMap) {
		String name = HtmlUtils.htmlEscape(loginMap.get("name"));
		if(StringUtils.isBlank(name)) {
			return new JsonResult(false, "用户名不能为空！");
		}
		String pass = HtmlUtils.htmlEscape(loginMap.get("pass"));
		if(StringUtils.isBlank(pass)) {
			return new JsonResult(false, "密码不能为空！");
		}

		Account account = accountService.searchByName(name);

		Subject subject = SecurityUtils.getSubject();	//shiro主体
		UsernamePasswordToken token = new UsernamePasswordToken(name, pass);

		try {
			subject.login(token);
			AuthCache.put(AuthCache.LoginInfo.MODE, LoginMode.APP);
			accountService.cacheAccountInfo(account);  //缓存登录信息
			return new JsonResult(true, "登录成功！",SecurityUtils.getSubject().getSession().getId());//返回用户id
		} catch (IncorrectCredentialsException ice) {
			throw new AuthenticationException("密码错误！", ice);
		}
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/home");
	}

	/**
	 * 后台主页
	 */
	@RequestMapping(value = { "/back-home" }, method = RequestMethod.GET)
	public String backHome() {
		return pageView("/back-home");
	}

	/**
	 * 退出系统
	 * @return 重定向登录页
	 */
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();	//shiro主体
		//记录上次用户登陆时间 @author 王冲
		Account cachedAccount = AuthCache.account();
		if (cachedAccount != null) {
			Account account = new Account();
			account.setId(cachedAccount.getId());
			account.setLastLogin(LocalDateTime.now());
			accountService.modify(account);
		}
		subject.logout();
		return "redirect:/login";
	}

	/**
	 * api退出系统
	 */
	@RequestMapping(value = { "api/logout" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult apiLogout() {
		Subject subject = SecurityUtils.getSubject();	//shiro主体
		//记录上次用户登陆时间 @author 王冲
		Account cachedAccount = AuthCache.account();
		if (cachedAccount != null) {
			Account account = new Account();
			account.setLastLogin(LocalDateTime.now());
			account.setId(cachedAccount.getId());
			accountService.modify(account);
		}
		subject.logout();
		return new JsonResult(true, "注销成功！");
	}

	@RequestMapping(value = { "/top" }, method = RequestMethod.GET)
	public String top() {
		return pageView("/top");
	}

	@RequestMapping(value = { "/left" }, method = RequestMethod.GET)
	public String left() {
		return pageView("/left");
	}

	@RequestMapping(value = { "/bottom" }, method = RequestMethod.GET)
	public String bottom() {
		return pageView("/bottom");
	}

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error() {
		return pageView("/error");
	}

}