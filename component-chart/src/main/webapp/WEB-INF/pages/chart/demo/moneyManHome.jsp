<%--
  Created by IntelliJ IDEA.
  User: wangchong
  Date: 2017/12/11
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <div class="row" style="margin-bottom: 80px;">
        <div class="row">
            <div class="col-md-3">
                <jsp:include page="bankAccount.jsp" />
            </div>
            <div class="col-md-3">
                <jsp:include page="bankAccountTotal.jsp" />
            </div>
            <div class="col-md-3">
                <jsp:include page="openAccount.jsp" />
            </div>
            <div class="col-md-3">
                <jsp:include page="closedAccount.jsp" />
            </div>
        </div>
        <div class="row">

        </div>
    </div>
</html>
