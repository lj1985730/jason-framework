package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 权限-系统-实体
 * @author Liu Jun at 2017-10-01 23:14:21
 * @since v1.0.0
 */
@Table(name = "AUTH_SYSTEM")
public class System extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源标识符
     */
    @Length(max = 500, message = "‘菜单资源标识符’内容长度不能超过500")
    @Column(name = "URI")
    private String uri;

    /**
     * 描述
     */
    @Length(max = 1000, message = "‘描述’内容长度不能超过1000")
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 备注
     */
    @Length(max = 1000, message = "‘备注’内容长度不能超过1000")
    @Column(name = "REMARK")
    private String remark;

    /**
     * 权限名称
     */
    @Length(max = 500, message = "‘权限名称’内容长度不能超过500")
    @Column(name = "PERMISSION")
    private String permission;

    public System getParent() {
        return (System)parent;
    }

    public <E extends TreeEntity> void setParent(E parent) {
        this.parent = parent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}