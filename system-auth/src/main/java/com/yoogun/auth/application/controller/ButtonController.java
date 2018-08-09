package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.ButtonService;
import com.yoogun.auth.application.vo.ButtonVo;
import com.yoogun.auth.domain.model.Button;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 权限-按钮-控制层
 * @author Liu Jun at 2017-11-12 19:08:48
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/button")
class ButtonController extends BaseCurdController<Button> {

    @Resource
    private ButtonService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/auth/button");
    }

    /**
     * 查询分页列表
     * @return 分页数据
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchPage(HttpServletRequest request) {
        ButtonVo vo = new ButtonVo(request);
        return new JsonResult(service.pageSearch(vo));
    }

    /**
     * 按钮启用/禁用
     * @param id 按钮Id
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}/toggleEnable" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult toggleEnable(@PathVariable("id") String id) {
        service.toggleEnable(id);
        return new JsonResult();
    }
}