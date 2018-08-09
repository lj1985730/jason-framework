package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限-按钮-查询VO
 * @author Liu Jun at 2017-11-12 19:42:37
 * @since v1.0.0
 */
public final class ButtonVo extends TableParam {

    private String menuId;

    public ButtonVo(HttpServletRequest request) {
        super(request);
        this.menuId = request.getParameter("menuId");
    }

    public String getMenuId() {
        return menuId;
    }
}
