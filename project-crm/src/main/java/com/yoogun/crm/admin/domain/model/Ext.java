/*
 * Ext.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;


/**
 * Ext-实体<br/>
 * @author  Wang Chong at 2018-03-22 10:11:47
 * @since v1.0.0
 */
@Table(name = "CU_EXT")
public class Ext extends BaseEntity {
	/**
	 * 模块名字
	 **/
	@Length(max = 200, message = "‘模块名字’长度不能超过200!")
	@Column(name = "MODULE")
	private String module;

	/**
	 * 附加主表的名字
	 **/
	@Length(max = 200, message = "‘附加主表的名字’长度不能超过200!")
	@Column(name = "TABLE_NAME")
	private String tableName;

	/**
	 * 附加信息key
	 **/
	@Length(max = 200, message = "‘附加信息key’长度不能超过200!")
	@Column(name = "EXT_KEY")
	private String extKey;

	/**
	 * 附加信息名字
	 **/
	@Length(max = 200, message = "‘附加信息名字’长度不能超过200!")
	@Column(name = "EXT_NAME")
	private String extName;

	/**
	 * 附件信息类型
	 **/
	@Length(max = 200, message = "‘附件信息类型’长度不能超过200!")
	@Column(name = "EXT_TYPE")
	private String extType;

	/**
	 * 附加信息前台展示类型
	 **/
	@Length(max = 200, message = "‘附加信息前台展示类型’长度不能超过200!")
	@Column(name = "EXT_SHOW_TYPE")
	private String extShowType;

	/**
	 * 附件信息排序号
	 **/
	@Column(name = "SORT_NUMBER")
	private Integer sortNumber;

	/**
	 * 备注
	 **/
	@Length(max = 1000, message = "‘备注’长度不能超过1000!")
	@Column(name = "REMARK")
	private String remark;

	public static final long serialVersionUID = 1L;

	/**
	 * 获取模块名字
	 **/
	public String getModule() {
		return module;
	}

	/**
	 * 设置模块名字
	 **/
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 获取附加主表的名字
	 **/
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置附加主表的名字
	 **/
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取附加信息key
	 **/
	public String getExtKey() {
		return extKey;
	}

	/**
	 * 设置附加信息key
	 **/
	public void setExtKey(String extKey) {
		this.extKey = extKey;
	}

	/**
	 * 获取附加信息名字
	 **/
	public String getExtName() {
		return extName;
	}

	/**
	 * 设置附加信息名字
	 **/
	public void setExtName(String extName) {
		this.extName = extName;
	}

	/**
	 * 获取附件信息类型
	 **/
	public String getExtType() {
		return extType;
	}

	/**
	 * 设置附件信息类型
	 **/
	public void setExtType(String extType) {
		this.extType = extType;
	}

	/**
	 * 获取附加信息前台展示类型
	 **/
	public String getExtShowType() {
		return extShowType;
	}

	/**
	 * 设置附加信息前台展示类型
	 **/
	public void setExtShowType(String extShowType) {
		this.extShowType = extShowType;
	}

	/**
	 * 获取附件信息排序号
	 **/
	public Integer getSortNumber() {
		return sortNumber;
	}

	/**
	 * 设置附件信息排序号
	 **/
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	/**
	 * 获取备注
	 **/
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 **/
	public void setRemark(String remark) {
		this.remark = remark;
	}
}