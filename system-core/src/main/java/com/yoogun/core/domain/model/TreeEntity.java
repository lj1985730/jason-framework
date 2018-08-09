package com.yoogun.core.domain.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

/**
 * 具有树形结构的实体基类，供树形实体类继承
 * @author Liu Jun at 2017-11-12 11:23:33
 * @since v1.0.0
 */
@Table
public class TreeEntity extends BaseEntity {

	public static final String ROOT_ID = "#";

    /**
     * 名称，树形显示内容
     */
    @NotBlank(message = "名称不可为空")
    @Length(max = 40, message = "名称长度不可大于40")
	@Column(name = "NAME")
	private String name;

	/**
	 * 父节点主键
	 */
	@Length(max = 36, message = "父节点内容异常")
	@Column(name = "PARENT_ID")
	private String parentId;

	/**
	 * 父节点
	 */
	protected TreeEntity parent;

	/**
	 * 排序
	 */
	@Digits(integer = 9, fraction = 0, message = "排序长度不能超过9")
	@Column(name = "SORT_NUMBER")
	private Integer sortNumber;

	/**
	 * 是否使用
	 */
	@NotBlank(message = "是否使用不可为空")
	@Column(name = "ENABLED")
	private Boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}