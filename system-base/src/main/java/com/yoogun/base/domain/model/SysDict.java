package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统-字典-实体
 * @author Liu Jun at 2017-11-8 15:54:02
 * @since V1.0.0
 */
@Table(name = "SYS_DICT")
public class SysDict extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotBlank(message = "‘名称’不能为空")
	@Length(max = 200, message = "‘名称’内容长度不能超过200")
	@Column(name = "NAME")
	private String name;

	/**
	 * 编码
	 */
	@NotNull(message = "‘编码’不能为空")
	@Digits(integer = 9, fraction = 0, message = "‘编码’数值位数不能超过9")
	@Column(name = "CODE")
	private Integer code;

	/**
	 * 父级编码
	 */
	@NotNull(message = "‘父级编码’不能为空")
	@Digits(integer = 9, fraction = 0, message = "‘父级编码’数值位数不能超过9")
	@Column(name = "PARENT_CODE")
	private Integer parentCode;

	/**
	 * 字典分类
	 */
	@NotBlank(message = "‘字典分类’不能为空")
	@Length(max = 200, message = "‘字典分类’内容长度不能超过200")
	@Column(name = "CATEGORY_KEY")
	private String categoryKey;

	/**
	 * 排序
	 */
	@NotNull(message = "‘排序’不能为空")
	@Digits(integer = 9, fraction = 0, message = "‘排序’数值位数不能超过9")
	@Column(name = "SORT_NUMBER")
	private Integer sortNumber;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "‘备注’内容长度不能超过1000")
	@Column(name = "REMARK")
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}

	public String getCategoryKey() {
		return categoryKey;
	}

	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}