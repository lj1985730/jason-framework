package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限-菜单-实体
 * @author Liu Jun at 2016-8-8 22:37:59
 * @since v1.0.0
 */
@Table(name = "AUTH_MENU")
public class Menu extends TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单资源标识符
     */
    @Length(max = 160, message = "‘菜单资源标识符’长度不能超过160")
    @Column(name = "URI")
    private String uri;

    /**
     * 图标名称
     */
    @Length(max = 30, message = "‘图标名称’内容长度不能超过30")
    @Column(name = "ICON")
    private String icon;

    /**
     * 大图标名称
     */
    @Length(max = 30, message = "‘大图标名称’长度不能超过30")
    @Column(name = "BIG_ICON")
    private String bigIcon;

    /**
     * 描述
     */
    @Length(max = 300, message = "‘描述’内容长度不能超过300")
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 菜单级别
     */
    @Digits(integer = 2, fraction = 0, message = "‘菜单级别’数值位数不能超过2")
    @Column(name = "LEVEL")
    private Integer level;

    /**
     * 快捷键
     */
    @Length(max = 100, message = "‘快捷键’内容长度不能超过100")
    @Column(name = "SHORTCUT")
    private String shortcut;

    /**
     * 菜单性质：0全部；1前台；2后台
     */
    @Digits(integer = 1, fraction = 0, message = "‘菜单性质’数值位数不能超过1")
    @Column(name = "IS_PUBLIC")
    private Integer isPublic;

    /**
     * 是否可分配
     */
    @NotNull(message = "‘是否可分配’不能为空")
    @Column(name = "ASSIGNABLE")
    private Boolean assignable;

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

    /**
     * 按钮
     */
    private List<Button> buttons;

    public Menu getParent() {
        return (Menu)parent;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getAssignable() {
        return assignable;
    }

    public void setAssignable(Boolean assignable) {
        this.assignable = assignable;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }
}