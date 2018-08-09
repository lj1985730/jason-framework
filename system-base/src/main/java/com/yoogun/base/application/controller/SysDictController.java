package com.yoogun.base.application.controller;

import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.base.application.service.SysDictService;
import com.yoogun.base.domain.model.SysDict;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.application.vo.TableParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统-字典-控制层
 * @author Liu Jun at 2018-7-25 17:00:54
 * @version v1.0.0
 */
@Controller
@RequestMapping(value = "**/base/sysDict")
class SysDictController extends BaseCurdController<SysDict> {
	
	@Resource
	private SysDictService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/base/sysDict");
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

    @RequestMapping(value = { "/area/{areaCode}/provinces" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getProvinceByArea(@PathVariable Integer areaCode) {
        return new JsonResult(service.getProvinceByArea(areaCode));
    }

    @RequestMapping(value = { "/province/{provinceCode}/cities" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getCityByProvince(@PathVariable Integer provinceCode) {
        return new JsonResult(service.getCityByProvince(provinceCode));
    }

    @RequestMapping(value = { "/city/{cityCode}/districts" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getDistrictByCity(@PathVariable Integer cityCode) {
        return new JsonResult(service.getDistrictByCity(cityCode));
    }
}