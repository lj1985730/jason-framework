<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <yonyou:nav father="权限管理" model="角色管理"/>

    <div class="row">
        <div class="col-md-3">
            <div class="portlet light" style="margin-bottom: 0;">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-list font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold">角色列表</span>
                    </div>
                    <div class="actions">
                        <div class="btn-group">
                            <button type="button" class="btn green dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-gear"></i>&nbsp;操作
                                <i class="fa fa-angle-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                                <shiro:hasPermission name="auth:role:create">
                                    <li id="createRole"><a href="javascript:"><i class="fa fa-plus"></i>&nbsp;新增</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:role:update">
                                    <li id="updateRole"><a href="javascript:"><i class="fa fa-edit"></i>&nbsp;修改</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:role:delete">
                                    <li id="deleteRole"><a href="javascript:"><i class="fa fa-trash-o"></i>&nbsp;删除</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:role:enable">
                                    <li id="enableRole"><a href="javascript:"><i class="fa fa-retweet"></i>&nbsp;启用/禁用</a></li>
                                </shiro:hasPermission>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <table id="roleTable" data-show-toggle="false" data-show-columns="false">
                        <thead>
                            <tr role="row" class="heading">
                                <th data-field="checkbox" data-checkbox="true"></th>
                                <th data-field="name" data-sortable="true">角色名称</th>
                                <th data-field="enabled" data-formatter="booleanFormatter">启用</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="portlet box blue-dark tabbable">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-info-circle"></i>
                        <span class="caption-subject">角色信息</span>
                    </div>
                    <div class="tools">
                        <a class="collapse"></a>
                        <a class="fullscreen"></a>
                    </div>
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#infoTab" data-toggle="tab">基本信息</a>
                        </li>
                        <li>
                            <a href="#menuTab" data-toggle="tab">菜单权限</a>
                        </li>
                        <li>
                            <a href="#roleRlatTab" data-toggle="tab">角色权限</a>
                        </li>
                    </ul>
                </div>
                <div class="portlet-body">
                    <div class="tab-content">
                        <div class="tab-pane active" id="infoTab">
                            <jsp:include page="role-info.jsp" />
                        </div>
                        <div class="tab-pane" id="menuTab">
                            <jsp:include page="role-menu.jsp" />
                        </div>
                        <div class="tab-pane" id="roleRlatTab">
                            <jsp:include page="role-rlat.jsp" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="role-edit.jsp" />
    <script src="scripts/auth/role.js"></script>
</html>
