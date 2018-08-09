package com.yoogun.base.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.base.application.service.SysComboDataService;
import com.yoogun.base.domain.model.SysComboData;
import com.yoogun.core.application.dto.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 系统-下拉框数据-控制层
 * @author Liu Jun at 2017-10-15 20:57:08
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/base/sysComboData")
class SysComboDataController extends BaseCurdController<SysComboData> {

    @Resource
    private SysComboDataService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String index() {
        return pageView("/system/base/sysComboData");
    }

    /**
     * 查询下拉框数据
     * @return 下拉框数据
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchData(@RequestParam("key") String key, @RequestParam(value = "params", required = false) String[] params) {
        return new JsonResult(service.loadComboData(key, params));
    }

}