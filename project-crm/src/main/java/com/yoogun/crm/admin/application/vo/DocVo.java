/*
 * DocVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * Doc-查询VO<br/>
 * @author Wang chong at 2018-03-08 13:50:37
 * @since v1.0.0
 */
public final class DocVo extends TableParam {

	public DocVo(HttpServletRequest request) {
		super(request);
		// 初始化其它属性
	}
}