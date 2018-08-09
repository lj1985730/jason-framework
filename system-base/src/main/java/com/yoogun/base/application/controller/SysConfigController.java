package com.yoogun.base.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.base.application.service.SysConfigService;
import com.yoogun.base.domain.model.SysConfig;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.application.vo.TableParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统-参数-控制层
 * @author Liu Jun at 2016-8-17 00:15:47
 * @version v1.0.0
 */
@Controller
@RequestMapping(value = "**/base/sysConfig")
class SysConfigController extends BaseCurdController<SysConfig> {
	
	@Resource
	private SysConfigService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/base/sysConfig");
    }

    /**
     * 查询分页列表
     * @return 分页数据
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchPage(HttpServletRequest request) {
        TableParam tableParam = new TableParam(request);
        return new JsonResult(service.pageSearch(tableParam));
    }
}