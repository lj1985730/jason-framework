package com.yoogun.base.domain.model;

import com.yoogun.core.domain.model.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 系统-文件夹-实体
 * @author Liu Jun at 2017-12-19 09:57:16
 * @since V1.0.0
 */
@Table(name = "SYS_FOLDER")
public class SysFolder extends TreeEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@Length(max = 500, message = "‘编号’内容长度不能超过500")
    @Column(name = "CODE")
	private String code;

	/**
	 * 是否系统文件夹
	 */
    @NotBlank(message = "‘是否系统文件夹’不能为空")
    @Column(name = "CONTENT")
	private Boolean isSystem;

    /**
     * 权限字符串
     */
    @Length(max = 500, message = "‘权限字符串’内容长度不能超过500")
    @Column(name = "PERMISSION")
    private String permission;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getSystem() {
        return isSystem;
    }

    public void setSystem(Boolean system) {
        isSystem = system;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}