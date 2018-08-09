/*
 * Doc.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.crm.admin.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yoogun.core.domain.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


/**
 * Doc-实体<br/>
 * @author  Wang chong at 2018-03-08 13:50:37
 * @since v1.0.0
 */
@Table(name = "CU_DOC")
public class Doc extends BaseEntity {
	/**
	 * 文档名称
	 **/
	@Length(max = 128, message = "‘文档名称’长度不能超过128!")
	@Column(name = "DOC_NAME")
	private String docName;

	/**
	 * 文件编号
	 **/
	@Length(max = 64, message = "‘文件编号’长度不能超过64!")
	@Column(name = "FILE_ID")
	private String fileId;

	/**
	 * 所属公司
	 **/
	@Length(max = 64, message = "‘所属公司’长度不能超过64!")
	@Column(name = "DOC_CORP")
	private String docCorp;

	/**
	 * 所属部门
	 **/
	@Length(max = 64, message = "‘所属部门’长度不能超过64!")
	@Column(name = "DOC_DEPT")
	private String docDept;

	/**
	 * 所属客户
	 **/
	@Length(max = 64, message = "‘所属客户’长度不能超过64!")
	@Column(name = "DOC_CUSTOMER")
	private String docCustomer;

	/**
	 * 个人文档
	 **/
	@Length(max = 64, message = "‘个人文档’长度不能超过64!")
	@Column(name = "DOC_PERSON")
	private String docPerson;

	/**
	 * 使用频率
	 **/
	@Column(name = "DOC_FREQUENCY")
	private Integer docFrequency;

	/**
	 * 属性名称
	 **/
	@Length(max = 64, message = "‘属性名称’长度不能超过64!")
	@Column(name = "DOC_CREATE_DATE")
	private String docCreateDate;

	/**
	 * 属性模块
	 **/
	@Length(max = 32, message = "‘属性模块’长度不能超过32!")
	@Column(name = "DOC_UPDATE_DATE")
	private String docUpdateDate;

	public static final long serialVersionUID = 1L;

	/**
	 * 获取文档名称
	 **/
	public String getDocName() {
		return docName;
	}

	/**
	 * 设置文档名称
	 **/
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * 获取文件编号
	 **/
	public String getFileId() {
		return fileId;
	}

	/**
	 * 设置文件编号
	 **/
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * 获取所属公司
	 **/
	public String getDocCorp() {
		return docCorp;
	}

	/**
	 * 设置所属公司
	 **/
	public void setDocCorp(String docCorp) {
		this.docCorp = docCorp;
	}

	/**
	 * 获取所属部门
	 **/
	public String getDocDept() {
		return docDept;
	}

	/**
	 * 设置所属部门
	 **/
	public void setDocDept(String docDept) {
		this.docDept = docDept;
	}

	/**
	 * 获取所属客户
	 **/
	public String getDocCustomer() {
		return docCustomer;
	}

	/**
	 * 设置所属客户
	 **/
	public void setDocCustomer(String docCustomer) {
		this.docCustomer = docCustomer;
	}

	/**
	 * 获取个人文档
	 **/
	public String getDocPerson() {
		return docPerson;
	}

	/**
	 * 设置个人文档
	 **/
	public void setDocPerson(String docPerson) {
		this.docPerson = docPerson;
	}

	/**
	 * 获取使用频率
	 **/
	public Integer getDocFrequency() {
		return docFrequency;
	}

	/**
	 * 设置使用频率
	 **/
	public void setDocFrequency(Integer docFrequency) {
		this.docFrequency = docFrequency;
	}

	/**
	 * 获取属性名称
	 **/
	public String getDocCreateDate() {
		return docCreateDate;
	}

	/**
	 * 设置属性名称
	 **/
	public void setDocCreateDate(String docCreateDate) {
		this.docCreateDate = docCreateDate;
	}

	/**
	 * 获取属性模块
	 **/
	public String getDocUpdateDate() {
		return docUpdateDate;
	}

	/**
	 * 设置属性模块
	 **/
	public void setDocUpdateDate(String docUpdateDate) {
		this.docUpdateDate = docUpdateDate;
	}
}