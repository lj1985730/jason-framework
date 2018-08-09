package com.yoogun.auth.application.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.yoogun.auth.application.service.MenuService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Menu;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseCurdController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限-菜单-控制层
 * @author Liu Jun at 2016-8-11 21:24:18
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/menu")
class MenuController extends BaseCurdController<Menu> {

    @Resource
    private MenuService service;

    /**
     * 页面的首页
     */
    @RequestMapping(value = { "/homeView" }, method = RequestMethod.GET)
    public String home() {
        return pageView("/auth/menu");
    }

    /**
     * 获取手风琴菜单
     */
    @RequestMapping(value = { "/leftMenu" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getAccordionMenu() {

        Object loginMode = AuthCache.get(AuthCache.LoginInfo.MODE);
        if(loginMode == null) {
            return null;
        }

        Account account = AuthCache.account();
        if(account == null) {
            return null;
        }
        List<Menu> menuList = null;
        if(loginMode == LoginController.LoginMode.BACK) {
            if(!account.isSuperAdmin()) {
                throw new AuthorizationException("非超管不可登录后台");
            }
            menuList = service.searchAllBackMenu(null);    //暂时不加入系统过滤
        } else if(loginMode == LoginController.LoginMode.FRONT) {
            if(account.getIsAdmin()) {
                menuList = service.searchAllFrontMenu(account.getId(), null);    //暂时不加入系统过
            } else {
                menuList = service.searchPermissionMenu(account.getId());   //暂时不加入系统过滤
            }
        }

        if(menuList == null || menuList.isEmpty()) {
            return null;
        }
        List<Menu> levelOneMenu = new ArrayList<>();

        menuList.forEach(menu -> {
            if (menu.getLevel() == 1) {
                levelOneMenu.add(menu);
            }
        });

        Writer jsonWriter = new StringWriter();
        JsonGenerator generator;
        try {
            generator = new JsonFactory().createGenerator(jsonWriter);
            generator.writeStartArray();
            for (Menu menu : levelOneMenu) {
                generator.writeStartObject();
                generator.writeStringField("id", menu.getId());
                generator.writeStringField("name", menu.getName());
                generator.writeStringField("icon", menu.getIcon());
                generator.writeBooleanField("leaf", false);
                generator.writeStringField("url", menu.getUri());
                generator.writeFieldName("children");
                generator.writeStartArray();

                List<Menu> levelTwoMenu = new ArrayList<>();
                menuList.forEach(subMenu -> {
                    if ((subMenu.getLevel() == 2) && subMenu.getParentId().equals(menu.getId())) {
                        levelTwoMenu.add(subMenu);
                    }
                });

                for (Menu subMenu : levelTwoMenu) {
                    generator.writeStartObject();
                    generator.writeStringField("id", subMenu.getId());
                    generator.writeStringField("name", subMenu.getName());
                    generator.writeStringField("icon", subMenu.getIcon());
                    generator.writeBooleanField("leaf", true);
                    generator.writeStringField("url", subMenu.getUri());
                    generator.writeEndObject();
                }
                generator.writeEndArray();
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.flush();
            generator.close();
        } catch (IOException e) {
            throw new BusinessException("菜单生成失败", e);
        }
        return new JsonResult(jsonWriter.toString());
    }

    /**
     * 查询菜单树数据源
     * @return 菜单树数据源
     */
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult search() {

        Account account = AuthCache.account();
        if(account == null) {
            throw new AuthenticationException("未登录系统");
        }

        return new JsonResult(service.searchAllFrontMenu(account.getId(), null));
    }

    /**
     * 查询菜单树数据源
     * @return 菜单树数据源
     */
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchById(@PathVariable String id) {

        Account account = AuthCache.account();
        if(account == null) {
            throw new AuthenticationException("未登录系统");
        }

        return new JsonResult(service.searchById(id));
    }

    /**
     * 获取用户权限下所有菜单列表
     * @return 菜单列表
     */
    @RequestMapping(value = { "/getUserMenu" }, method= RequestMethod.GET)
    @ResponseBody
    public JsonResult getUserMenu() {
        Account account = AuthCache.account();
        if(account == null) {
            return null;
        }
        return new JsonResult(service.searchPermissionMenu(account.getId()));
    }

    /**
     * 菜单启用/禁用
     * @param id 菜单Id
     * @return 操作结果
     */
    @RequestMapping(value = { "/{id}/toggleEnable" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult toggleEnable(@PathVariable("id") String id) {
        service.toggleEnable(id);
        return new JsonResult();
    }
}