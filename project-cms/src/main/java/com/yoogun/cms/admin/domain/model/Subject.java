/*
 * Subject.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.domain.model;

import com.sun.star.bridge.oleautomation.Decimal;
import com.yoogun.core.domain.model.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;


/**
 * CMS-主题（栏目）-实体<br/>
 * @author  Liu Jun at 2018-6-5 10:49:26
 * @since v1.0.0
 */
@Table(name = "CMS_SUBJECT")
public class Subject extends TreeEntity {

	/**
	 * 编码
	 **/
	@Length(max = 100, message = "‘编码’长度不能超过100!")
	@Column(name = "CODE")
	private String code;

	@Digits(integer = 9, fraction = 0, message = "‘编码’数值不能超过9位!")
	@Column(name = "CHANNEL")
	private Decimal channel;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Decimal getChannel() {
		return channel;
	}

	public void setChannel(Decimal channel) {
		this.channel = channel;
	}
}