package com.yoogun.auth.application.dto;

import com.yoogun.auth.domain.model.Department;
import com.yoogun.auth.domain.model.Post;
import com.yoogun.utils.application.dto.BsTreeview;

/**
 * bootstrap-treeview-department and post DTO
 * @author Liu Jun at 2018-4-2 13:30:37
 * @since v1.0.0
 */
public class DepartmentPostTreeview extends BsTreeview<Department> {

    /**
     *  节点类型 1：department；2：post
     */
    private int type;

    /**
     * 部门构造器
     */
    public DepartmentPostTreeview(Department department) {
        super(department);
        this.type = 1;
        this.setIcon("icon-home");
        this.setTags(new String[] {"部门"});
    }

    /**
     * 岗位构造器
     * @param showPermission 是否显示权限，默认为false
     */
    public DepartmentPostTreeview(Post post, Boolean... showPermission) {
        super(post);
        this.type = 2;
        if(post.getIsLeader()) {
            this.setIcon("icon-flag");
        } else {
            this.setIcon("icon-pointer");
        }
        this.setTags(new String[] {"岗位"});
        this.data = post;

        this.text = post.getName();
        if(showPermission == null || showPermission.length == 0 || !showPermission[0]) {
            return;
        }

         /* 组装权限选择内容 */
        // 权限HTML文本模板
        String permissionFmt =
            "<div class='input-group pull-right'>" +
                "<div class='icheck-inline'>权限：" +
                    "<label>" +
                        "<input type='checkbox' class='post-subordinate-auth data-permission' id='%s' /> 数据" +
                    "</label>" +
                    "<label>" +
                        "<input type='checkbox' class='post-subordinate-auth audit-permission' id='%s' /> 审核" +
                    "</label>" +
                "</div>" +
            "</div>";
        this.text += String.format(permissionFmt,
                post.getId() + "-data-permission",
                post.getId() + "-audit-permission");
    }

    public int getType() {
        return type;
    }
}
