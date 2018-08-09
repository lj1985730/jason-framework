/*
 * ContactController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.crm.admin.application.service.ContactService;
import com.yoogun.crm.admin.domain.model.Contact;
import javax.annotation.Resource;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



/**
 * 联系人-控制器<br/>
 * @author Sheng Baoyu at 2018-02-25 14:44:35
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/contact")
class ContactController extends BaseCurdController<Contact> {

	@Resource
	private ContactService service;

	/**
	 * 页面的首页
	 */
	@RequiresPermissions("crm:contact:view")
	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
	public String home() {
		return pageView("/crm/admin/contact");
	}

	/**
	 * 查询列表
	 * @return 列表数据
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	@ResponseBody
	public Object searchPage(String customerId) {
		if (customerId != null){
			return service.pageSearch(customerId);
		}else {
			return new Contact();
		}
	}
}