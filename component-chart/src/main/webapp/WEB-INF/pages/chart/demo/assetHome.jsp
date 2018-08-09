<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2017/12/11
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <div class="row" style="margin-bottom: 80px;">
        <div class="col-md-4">
            <jsp:include page="assetComposition.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="debtComposition.jsp" />
        </div>
        <div class="col-md-4">
            <jsp:include page="assetTotal.jsp" />
        </div>
    </div>
</html>
