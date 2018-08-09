<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>

    <head>
        <style>
            #form-panel {
                -webkit-transition: left 0.5s ease;
                -moz-transition: left 0.5s ease;
                -o-transition: left 0.5s ease;
                transition: left 0.5s ease;
            }
        </style>
    </head>

    <yonyou:nav father="系统管理" model="部门管理" />

    <div class="row">
        <div class="col-md-4">
            <div class="portlet light" style="margin-bottom: 35px;">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-list font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold">部门列表</span>
                    </div>
                    <div class="actions">
                        <div class="btn-group">
                            <button id="dropdownBtn" type="button" class="btn green dropdown-toggle" disabled="disabled" data-toggle="dropdown" title="选择一个节点进行操作">
                                <i class="fa fa-gear"></i>&nbsp;操作
                                <i class="fa fa-angle-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                                <shiro:hasPermission name="auth:department:create">
                                    <li id="createDepartment"><a href="javascript:"><i class="fa fa-plus"></i>&nbsp;新增下级部门</a></li>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="auth:department:delete">
                                    <li id="deleteDepartment"><a href="javascript:"><i class="fa fa-trash-o"></i>&nbsp;删除</a></li>
                                </shiro:hasPermission>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="portlet-body" style="padding: 0;">
                    <div id="treePanel"></div>
                </div>
            </div>
        </div>

        <div class="col-md-8" id="form-panel" style="left: 100%;">
            <jsp:include page="department-edit.jsp" />
        </div>
    </div>

    <script type="text/javascript" src="scripts/base/tree-select.js"></script>
    <script src="scripts/auth/department.js"></script>
</html>
