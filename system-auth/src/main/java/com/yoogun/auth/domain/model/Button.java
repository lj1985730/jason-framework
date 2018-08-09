package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-按钮-实体
 * @author Liu Jun at 2016-7-31 14:14:44
 * @version v1.0.0
 */
@Table(name = "AUTH_BUTTON")
public class Button extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单
     */
    private Menu menu;

    /**
     * 菜单ID
     */
    @NotBlank(message = "‘菜单ID’不能为空")
    @Length(max = 36, message = "‘菜单ID’内容长度不能超过36")
    @Column(name = "MENU_ID")
    private String menuId;

    /**
     * 按钮名称
     */
    @NotBlank(message = "‘按钮名称’不能为空")
    @Length(max = 20, message = "‘按钮名称’内容长度不能超过20")
    @Column(name = "NAME")
    private String name;

    /**
     * HTML元素ID
     */
    @NotBlank(message = "‘HTML元素ID’不能为空")
    @Length(max = 20, message = "‘HTML元素ID’内容长度不能超过30")
    @Column(name = "ELEMENT_ID")
    private String elementId;

    /**
     * 按钮图标
     */
    @Length(max = 100, message = "‘按钮图标’内容长度不能超过100")
    @Column(name = "ICON")
    private String icon;

    /**
     * 是否使用
     */
    @NotBlank(message = "‘是否使用’不能为空")
    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * 备注
     */
    @Length(max = 300, message = "‘备注’内容长度不能超过300")
    @Column(name = "REMARK")
    private String remark;

    /**
     * 权限名称
     */
    @Length(max = 100, message = "‘权限名称’内容长度不能超过100")
    @Column(name = "PERMISSION")
    private String permission;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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