<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2018/5/4
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <div class="portlet box blue-dark tabbable">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-info-circle"></i>任务中心
            </div>
            <div class="tools">
                <a class="collapse"></a>
                <a class="fullscreen"></a>
            </div>
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#todoTab" data-toggle="tab">待我处理</a>
                </li>
                <li>
                    <a href="#myApplicationsTab" data-toggle="tab">我的申请</a>
                </li>
                <li>
                    <a href="#involvedTab" data-toggle="tab">我参与过</a>
                </li>
            </ul>
        </div>
        <div class="portlet-body">
            <div class="tab-content">
                <div class="tab-pane active" id="todoTab">
                    <jsp:include page="task-todo.jsp" />
                </div>
                <div class="tab-pane" id="myApplicationsTab">
                    <jsp:include page="task-my-application.jsp" />
                </div>
                <div class="tab-pane" id="involvedTab">
                    <jsp:include page="task-involved.jsp" />
                </div>
            </div>
        </div>
    </div>
    <script src="scripts/workflow/task-common.js"></script>
</html>
