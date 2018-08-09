package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限-角色-查询VO
 * @author Liu Jun at 2017-11-12 20:06:22
 * @since v1.0.0
 */
public final class RoleVo extends TableParam {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    public RoleVo(HttpServletRequest request) {
        super(request);
        this.name = request.getParameter("name");
        this.code = request.getParameter("code");
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
