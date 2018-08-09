package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限-岗位-查询VO
 * @author Liu Jun at 2017-11-12 19:42:37
 * @since v1.0.0
 */
public final class PostVo extends TableParam {

    private String departmentId;

    public PostVo(HttpServletRequest request) {
        super(request);
        this.departmentId = request.getParameter("departmentId");
    }

    public String getDepartmentId() {
        return departmentId;
    }
}
