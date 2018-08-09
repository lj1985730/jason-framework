package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 系统-下拉框数据-实体
 * @author Liu Jun at 2017-11-8 15:56:31
 * @since V1.0.0
 */
@Table(name = "SYS_COMBO_DATA")
public class SysComboData extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务键
	 */
	@NotBlank(message = "‘业务键’不能为空")
	@Length(max = 200, message = "‘业务键’内容长度不能超过200")
    @Column(name = "BUSINESS_KEY")
	private String businessKey;

	/**
	 * 主体内容
	 */
    @NotBlank(message = "‘主体内容’不能为空")
    @Column(name = "CONTENT")
	private String content;

    /**
     * 条件
     */
    @Column(name = "CONDITIONS")
    private String conditions;

    /**
     * 排序
     */
    @Column(name = "ORDERBY")
    private String orderBy;

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}