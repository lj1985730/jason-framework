package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限-人员-查询VO
 * @author Liu Jun at 2017-11-12 19:42:37
 * @since v1.0.0
 */
public final class PersonVo extends TableParam {

    private String name;

    private String code;

    private String category;

    private String state;

    private String nature;

    private String orgId;

    private String orgType;



    public PersonVo(HttpServletRequest request) {
        super(request);
        this.name = request.getParameter("name");
        this.code = request.getParameter("code");
        this.category = request.getParameter("category");
        this.state = request.getParameter("state");
        this.nature = request.getParameter("nature");
        this.orgId = request.getParameter("orgId");
        this.orgType = request.getParameter("orgType");
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getState() {
        return state;
    }

    public String getNature() {
        return nature;
    }

    public String getCode() {
        return code;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrgType() {
        return orgType;
    }
}
