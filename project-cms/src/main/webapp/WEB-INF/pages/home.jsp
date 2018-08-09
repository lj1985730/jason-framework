<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>

    <style>
        .fixed-table-toolbar .bars {
            width: 50%;
        }
        .bars {
            margin-bottom: 0;
        }
        .bars .form-group {
            margin-bottom: 0;
        }
        .bars .form-group label {
            margin-bottom: 0;
        }
    </style>

    <ul class="page-breadcrumb breadcrumb">
        <li>首页</li>
        <li>欢迎访问：大连用友crm系统</li>
    </ul>

    <div class="row">
        <div class="col-md-12">
            <jsp:include page="workflow/task-center.jsp" />
        </div>
    </div>

    <%--<div class="page-bar">--%>
        <%--<div class="page-toolbar">--%>
            <%--<div class="chat-form" style="margin-top: 0; background-color: #FFFFFF;">--%>
                <%--<div class="input-cont">--%>
                    <%--<input class="form-control" type="text" placeholder="全文检索" />--%>
                <%--</div>--%>
                <%--<div class="btn-cont">--%>
                    <%--<span class="arrow"></span>--%>
                    <%--<a href="" class="btn blue icn-only">--%>
                        <%--<i class="fa fa-check icon-white"></i>--%>
                    <%--</a>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<script src="scripts/bus/station.js"></script>--%>
</html>
