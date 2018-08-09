<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>

    <ul class="page-breadcrumb breadcrumb">
        <li>后台首页</li>
    </ul>

    <div class="row">
        <div class="col-md-12">
            <div class="portlet box blue-dark tabbable">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-info-circle"></i>租户选择
                    </div>
                    <div class="tools">
                        <a class="collapse"></a>
                        <a class="fullscreen"></a>
                    </div>
                    <div class="actions">
                        <yonyou:btn-default id="selectTenant" permission="auth:tenant:update" label="切换租户" icon="fa fa-refresh" />
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="table-container">
                        <table id="tenantTable">
                            <thead>
                            <tr role="row" class="heading">
                                <th data-field="checkbox" data-checkbox="true"></th>
                                <th data-field="id" data-formatter="indexFormatter">序号</th>
                                <th data-field="name" data-sortable="true">名称</th>
                                <th data-field="uscc" data-sortable="true">统一社会信用代码</th>
                                <th data-field="category">类型</th>
                                <th data-field="legalRepresentative">法人代表</th>
                                <th data-field="registeredCapital">注册资本</th>
                                <th data-field="enabled" data-formatter="booleanFormatter">是否启用</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="scripts/back-home.js"></script>
</html>
