/*
 * CustomerExtVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;


/**
 * EntityExt-查询VO<br/>
 * @author Wang Chong at 2018-03-22 10:13:42
 * @since v1.0.0
 */
public final class EntityExtVo extends TableParam {

	public EntityExtVo(HttpServletRequest request) {
		super(request);
		// 初始化其它属性
		this.entityId = request.getParameter("entityId");
	}

	/**
	 * 客户id
	 */
	private String entityId;

	public String getEntityId() {
		return entityId;
	}
}