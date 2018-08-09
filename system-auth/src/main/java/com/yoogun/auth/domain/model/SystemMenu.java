package com.yoogun.auth.domain.model;

import com.yoogun.core.domain.model.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 权限-系统菜单关系-实体
 * @author Liu Jun at 2017-10-01 23:18:51
 * @since v1.0.0
 */
@Table(name = "AUTH_SYSTEM_MENU")
public class SystemMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private com.yoogun.auth.domain.model.System system;

    /**
     * 系统ID
     **/
    @NotBlank(message = "‘系统ID’不能为空")
    @Length(max = 36, message = "‘系统ID’内容长度不能超过36")
    @Column(name = "SYSTEM_ID")
    private String systemId;

    private Menu menu;

    /**
     * 菜单ID
     **/
    @NotBlank(message = "‘菜单ID’不能为空")
    @Length(max = 36, message = "‘菜单ID’内容长度不能超过36")
    @Column(name = "MENU_ID")
    private String menuId;

    public com.yoogun.auth.domain.model.System getSystem() {
        return system;
    }

    public void setSystem(com.yoogun.auth.domain.model.System system) {
        this.system = system;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu sysMenu) {
        this.menu = menu;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}