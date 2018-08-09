/*
 * AuthrolerlatVo.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;


/**
 * 角色关系-查询VO<br/>
 * @author Wang Chong at 2018-05-01 13:27:08
 * @since v1.0.0
 */
public final class RoleRlatVo extends TableParam {

	public RoleRlatVo(HttpServletRequest request) {
		super(request);
		// 初始化其它属性
	}
}