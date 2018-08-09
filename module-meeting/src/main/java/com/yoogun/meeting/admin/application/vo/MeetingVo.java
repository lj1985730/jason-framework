package com.yoogun.meeting.admin.application.vo;

import com.yoogun.core.application.vo.TableParam;
import javax.servlet.http.HttpServletRequest;

/**
 * 会议-会议-查询VO
 *@author Sheng Baoyu at 2017-12-25 15:23:00
 *@since v1.0.0
 **/
public final class MeetingVo extends TableParam {

    /**
     * 名称
     */
    private String name;

    /**
     * 名称
     */
    private String startDate;

    /**
     * 名称
     */
    private String endDate;

    public MeetingVo(HttpServletRequest request) {
        super(request);
        this.name = request.getParameter("name");
        this.startDate = request.getParameter("startDate");
        this.endDate = request.getParameter("endDate");
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }


}
