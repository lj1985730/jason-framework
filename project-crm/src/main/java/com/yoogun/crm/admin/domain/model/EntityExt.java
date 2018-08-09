/*
 * CustomerExt.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * Customerext-实体<br/>
 * @author  Wang Chong at 2018-03-22 10:13:42
 * @since v1.0.0
 */
@Table(name = "CU_ENTITY_EXT")
public class EntityExt extends BaseEntity {
	/**
	 * 实体主键主键
	 **/
	@NotBlank(message = "客户主键不能为空！")
	@Length(max = 36, message = "‘客户主键’长度不能超过36!")
	@Column(name = "ENTITY_ID")
	private String entityId;

	/**
	 * 附加信息主键
	 **/
	@NotBlank(message = "附加信息主键不能为空！")
	@Length(max = 36, message = "‘附加信息主键’长度不能超过36!")
	@Column(name = "EXT_ID")
	private String extId;

	/**
	 * 附加信息值
	 **/
	@NotBlank(message = "附加信息值不能为空！")
	@Length(max = 200, message = "‘附加信息值’长度不能超过200!")
	@Column(name = "EXT_VALUE")
	private String extValue;

	/**
	 * 排序号
	 **/
	@NotNull(message = "排序号不能为空！")
	@Column(name = "SORT_NUMBER")
	private Integer sortNumber;

	/**
	 * 附加信息实体类
	 */
	private Ext ext;

	public static final long serialVersionUID = 1L;

	/**
	 * 获取附加信息实体
	 */
	public Ext getExt() {
		return ext;
	}

	/**
	 * 设置附加信息实体
	 */
	public void setExt(Ext ext) {
		this.ext = ext;
	}

	/**
	 * 获取客户主键
	 **/
	public String getEntityId() {
		return entityId;
	}

	/**
	 * 设置客户主键
	 **/
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * 获取附加信息主键
	 **/
	public String getExtId() {
		return extId;
	}

	/**
	 * 设置附加信息主键
	 **/
	public void setExtId(String extId) {
		this.extId = extId;
	}

	/**
	 * 获取附加信息值
	 **/
	public String getExtValue() {
		return extValue;
	}

	/**
	 * 设置附加信息值
	 **/
	public void setExtValue(String extValue) {
		this.extValue = extValue;
	}

	/**
	 * 获取排序号
	 **/
	public Integer getSortNumber() {
		return sortNumber;
	}

	/**
	 * 设置排序号
	 **/
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}
}