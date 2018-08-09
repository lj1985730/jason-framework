package com.yoogun.base.application.controller;

import com.yoogun.base.application.service.CommonTreeService;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 树形实体业务控制器的抽象父类<br>
 * 继承BaseController
 * @author Liu Jun
 */
@Controller
@RequestMapping(value = "**/base")
public class TreeController extends BaseController {

	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(TreeController.class);

	/**
	 * 统一注入DAO层
	 */
	@Autowired    //此处需要使用Autowired来保证按照类型选择Service
	protected CommonTreeService commonTreeService;

	/**
	 * 查询数据
	 * @param request :type 数据分类
	 */
	@RequestMapping(value = { "/treeDatas" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchTree(HttpServletRequest request) {
		String entityName = request.getParameter("entityName");
		String rootId = request.getParameter("rootId");
		return new JsonResult(commonTreeService.searchTreeview(entityName, rootId));
	}
}