/*
 * ContactController.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.crm.admin.domain.model.Informationization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 企业-信息化详情-控制器<br/>
 * @author Liu Jun at 2018-7-20 15:44:18
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/crm/informationization")
class InformationizationController extends BaseCurdController<Informationization> {

//	@Resource
//	private InformationizationService service;
//
//	/**
//	 * 页面的首页
//	 */
//	@RequiresPermissions("crm:contact:view")
//	@RequestMapping(value = {"/homeView" }, method = RequestMethod.GET)
//	public String home() {
//		return pageView("/crm/admin/contact");
//	}

//	/**
//	 * 查询列表
//	 * @return 列表数据
//	 */
//	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
//	@ResponseBody
//	public Object searchPage(String customerId) {
//		if (customerId != null){
//			return service.pageSearch(customerId);
//		}else {
//			return new Contact();
//		}
//	}
}