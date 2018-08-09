/*
 * ExtVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * Ext-查询VO<br/>
 * @author Wang Chong at 2018-03-22 10:11:47
 * @since v1.0.0
 */
public final class ExtVo extends TableParam {

	public ExtVo(HttpServletRequest request) {
		super(request);
		this.module = request.getParameter("module");
		this.tableName = request.getParameter("tableName");
	}

	/**
	 * 模块名字
	 */
	private String module;

	/**
	 * 数据库表名
	 */
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public String getModule() {
		return module;
	}
}