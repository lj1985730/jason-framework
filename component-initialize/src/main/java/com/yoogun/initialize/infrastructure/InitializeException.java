package com.yoogun.initialize.infrastructure;

import com.yoogun.core.infrastructure.exception.BusinessException;

/**
 * 初始化数据库异常
 * @author Liu Jun
 * @version 2016-10-13 10:39:15
 */
public class InitializeException extends BusinessException {

	private static final long serialVersionUID = -7196444398268947107L;

	public InitializeException(String message) {
		super(message);
	}

	public InitializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
