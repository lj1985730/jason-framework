<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <yonyou:nav father="权限管理" model="账户管理" />

    <div class="row">
        <div class="col-md-4">
            <div class="portlet light" style="margin-bottom: 0;">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-list font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold">账户列表</span>
                    </div>
                    <div class="actions">
                        <div class="btn-group">
                            <button type="button" class="btn green dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-gear"></i>&nbsp;操作
                                <i class="fa fa-angle-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                                <shiro:hasPermission name="auth:account:create">
                                    <li id="createAccount"><a href="javascript:"><i class="fa fa-plus"></i>&nbsp;新增</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:account:update">
                                    <li id="updateAccount"><a href="javascript:"><i class="fa fa-edit"></i>&nbsp;修改</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:account:delete">
                                    <li id="deleteAccount"><a href="javascript:"><i class="fa fa-trash-o"></i>&nbsp;删除</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:account:unlock">
                                    <li id="unlockAccount"><a href="javascript:"><i class="fa fa-unlock"></i>&nbsp;解锁</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:account:enable">
                                    <li id="enableAccount"><a href="javascript:"><i class="fa fa-retweet"></i>&nbsp;启用/禁用</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:account:resetPwd">
                                    <li id="resetPassword"><a href="javascript:"><i class="fa fa-refresh"></i>&nbsp;重置密码</a></li>
                                </shiro:hasPermission>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <table id="accountTable" data-show-toggle="false" data-show-columns="false">
                        <thead>
                            <tr role="row" class="heading">
                                <th data-field="checkbox" data-checkbox="true"></th>
                                <th data-field="name" data-sortable="true">用户名</th>
                                <th data-field="enabled" data-formatter="booleanFormatter">启用</th>
                                <th data-field="locked" data-formatter="booleanFormatter">锁定</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="portlet box blue-dark tabbable">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-info-circle"></i>
                        <span class="caption-subject">账户信息</span>
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
                            <a href="#roleTab" data-toggle="tab">关联角色</a>
                        </li>
                    </ul>
                </div>
                <div class="portlet-body">
                    <div class="tab-content">
                        <div class="tab-pane active" id="infoTab">
                            <jsp:include page="account-info.jsp" />
                        </div>
                        <div class="tab-pane" id="roleTab">
                            <jsp:include page="account-role.jsp" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="account-edit.jsp" />
    <script src="scripts/auth/account.js"></script>
</html>
