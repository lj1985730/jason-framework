package com.yoogun.auth.application.controller;

import com.yoogun.auth.application.service.PostRlatService;
import com.yoogun.auth.application.service.PostService;
import com.yoogun.auth.domain.model.Post;
import com.yoogun.auth.domain.model.PostRlat;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限-岗位-控制层
 * @author Liu Jun at 2016-8-14 23:29:36
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/auth/post")
class PostController extends BaseController {
	
	@Resource
	private PostService service;

	@Resource
	private PostRlatService postRlatService;

    /**
     * 页面的首页
     */
    @RequiresPermissions("auth:post:view")
    @RequestMapping(value = { "/homeView" })
    public String home() {
        return pageView("/auth/post");
    }

    /**
     * 检索部门岗位树
     */
    @RequestMapping(value = { "/" })
    @ResponseBody
    public JsonResult searchTree() {
        return new JsonResult(service.searchTreeview());
    }

    /**
     *  检索带有权限内容的部门岗位树
     * @return 带有权限内容的部门岗位树
     */
    @RequestMapping(value = { "/subordinateTree" })
    @ResponseBody
    public JsonResult searchSubordinateTree() {
        return new JsonResult(service.searchSubordinateTreeview());
    }

    /**
     * 查询岗位的下级岗位
     * @param superiorId 当前岗位ID（上级岗位）
     * @return 岗位的下级岗位
     */
    @RequestMapping(value = { "/{superiorId}/subordinates" }, method = RequestMethod.GET)
    @ResponseBody
    public JsonResult searchSubordinates(@PathVariable String superiorId) {
        return new JsonResult(postRlatService.searchSubordinate(superiorId));
    }

    /**
     * 设置岗位的下级岗位
     * @param postId 岗位ID
     * @param datas 岗位关系集合key="postRlats"
     * @return 操作结果
     */
    @RequestMapping(value = { "/{postId}/subordinates" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult bindSubordinates(@PathVariable String postId, @RequestBody Map<String, List<PostRlat>> datas) {
        postRlatService.bindSubordinates(postId, datas.get("postRlats"));
        return new JsonResult(true, "保存成功！");
    }

    /**
     * 新增操作
     * @return 操作结果
     */
    @RequiresPermissions("auth:post:create")
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@RequestBody Post entity) {
        service.create(entity);
        return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
    }

    /**
     * 更新操作
     * @return 操作结果
     */
    @RequiresPermissions("auth:post:update")
    @RequestMapping(value = { "/" }, method = RequestMethod.PUT)
    @ResponseBody
    public JsonResult modify(@RequestBody Post entity) {
        service.modify(entity);
        return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
    }

    /**
     * 删除操作
     * @return 操作结果
     */
    @RequiresPermissions("auth:post:delete")
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult remove(@PathVariable("id") String id) {
        service.remove(id, AuthCache.accountId());
        return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
    }
}