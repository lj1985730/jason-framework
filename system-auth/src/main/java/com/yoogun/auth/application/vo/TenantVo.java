package com.yoogun.auth.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限-企业-查询VO
 * @author Liu Jun at 2017-11-14 15:39:33
 * @since v1.0.0
 */
public final class TenantVo extends TableParam {

    private String name;

    private String uscc;

    public TenantVo(HttpServletRequest request) {
        super(request);
        this.name = request.getParameter("name");
        this.uscc = request.getParameter("uscc");
    }

    public String getName() {
        return name;
    }

    public String getUscc() {
        return uscc;
    }
}
