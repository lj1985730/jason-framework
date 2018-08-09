package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.TenantService;
import com.yoogun.auth.application.vo.TenantVo;
import com.yoogun.auth.domain.model.Tenant;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限-岗位-控制层
 * @author Liu Jun at 2016-8-14 23:29:36
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/tenant")
class TenantController extends BaseCurdController<Tenant> {

	@Resource
	private TenantService service;

    /**
     * 超管企业信息页面的首页
     */
    @RequiresPermissions("system:tenant:view")
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/auth/tenant");
    }

    /**
     * 自身企业信息页面（我的企业）的首页
     */
    @RequiresPermissions("system:self-tenant:view")
    @RequestMapping(value = { "/selfHomeView" }, method = RequestMethod.GET)
    public ModelAndView selfHome() {
        Tenant tenant = service.searchById(AuthCache.tenantId());
        return pageView("/auth/tenant-self", "tenant", tenant);
    }

    /**
     * 查询分页列表
     * @return 分页数据
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchPage(HttpServletRequest request) {
        TenantVo vo = new TenantVo(request);
        return new JsonResult(service.pageSearch(vo));
    }

    /**
     * 启用/禁用
     * @param tenantId 租户Id
     * @return 操作结果
     */
    @RequestMapping(value = { "/{tenantId}/toggleEnable" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult toggleEnable(@PathVariable("tenantId") String tenantId) {
        service.toggleEnable(tenantId);
        return new JsonResult();
    }

    /**
     *  搜索自身企业
     * @return 自身企业PO
     */
    @RequestMapping(value = { "/selfTenant" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchSelf() {
        return new JsonResult(service.searchById(AuthCache.tenantId()));
    }
}