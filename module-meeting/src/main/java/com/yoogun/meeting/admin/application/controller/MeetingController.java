package com.yoogun.meeting.admin.application.controller;

import com.yoogun.meeting.admin.application.service.MeetingService;
import com.yoogun.meeting.admin.application.vo.MeetingVo;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.meeting.admin.domain.model.Meeting;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 会议-会议安排-控制层
 * @author Sheng Baoyu at 2017-12-25 15:23:00
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/meeting/meeting")
public class MeetingController extends BaseController {

    @Resource
    private MeetingService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/meeting/admin/meeting");
    }

    /**
     * 查询分页列表
     * @return 分页数据
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchPage(HttpServletRequest request) {
        MeetingVo vo = new MeetingVo(request);
        return new JsonResult(service.pageSearch(vo));
    }

    /**
     * 新增数据
     * @return 操作结果
     */
    @RequiresPermissions("meeting:meeting:create")
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Meeting meeting) {
        service.create(meeting);
        return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
    }

    /**
     * 更新数据
     * @return 操作结果
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody Meeting meeting) {
        service.modify(meeting);
        return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
    }

    /**
     * 删除数据
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult remove(@PathVariable("id") String id) {
        service.remove(id, AuthCache.accountId());
        return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
    }
}
