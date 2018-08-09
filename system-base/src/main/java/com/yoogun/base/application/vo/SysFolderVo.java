package com.yoogun.base.application.vo;

import com.yoogun.core.application.vo.TableParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统-文件-查询VO
 * @author Liu Jun at 2017-12-19 10:43:28
 * @since v1.0.0
 */
public final class SysFolderVo extends TableParam {

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 父文件夹ID
     */
    private String parentId;

    public SysFolderVo(HttpServletRequest request) {
        super(request);
        this.name = request.getParameter("name");
        this.code = request.getParameter("code");
        this.businessId = request.getParameter("businessId");
        this.parentId = request.getParameter("parentId");
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getParentId() {
        return parentId;
    }
}
