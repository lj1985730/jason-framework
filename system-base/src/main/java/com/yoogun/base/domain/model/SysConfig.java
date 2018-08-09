package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统-配置-实体
 * @author Liu Jun
 * @since V1.0.0
 */
@Table(name = "SYS_CONFIG")
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 配置键
	 */
	@NotBlank(message = "‘配置键’不能为空")
	@Length(max = 200, message = "‘配置键’内容长度不能超过200")
    @Column(name = "CFG_KEY")
    private String cfgKey;

	/**
	 * 配置值
	 */
	@Length(max = 500, message = "‘配置值’内容长度不能超过500")
    @Column(name = "CFG_VALUE")
    private String cfgValue;

    /**
     * 是否生效
     */
    @NotNull(message = "‘是否生效’不能为空")
    @Column(name = "ENABLED")
    private Boolean enabled;

	/**
	 * 是否可在系统界面修改
	 */
    @NotNull(message = "‘是否可在系统界面修改’不能为空")
    @Column(name = "EDITABLE")
    private Boolean editable;

	/**
	 * 备注
	 */
	@Length(max = 1000, message = "‘备注’内容长度不能超过1000")
    @Column(name = "REMARK")
    private String remark;

    public String getCfgKey() {
        return cfgKey;
    }

    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}