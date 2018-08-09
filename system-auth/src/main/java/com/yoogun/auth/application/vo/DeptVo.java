package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限-部门-查询VO
 * @author Wang Chong at 2017-12-25 22:33
 * @since V1.0.0
 */
public final class DeptVo extends TableParam {

    private String parentDeptId;//上级部门id

    private String level;//部门等级

    /**
     * 构造器，根据request获取参数
     * @param request 请求体
     */
    public DeptVo(HttpServletRequest request) {
        super(request);
        this.parentDeptId = request.getParameter("parentDeptId");
        this.level = request.getParameter("level");
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }

    public void setParentDeptId(String parentDeptId) {
        this.parentDeptId = parentDeptId;
    }
}
