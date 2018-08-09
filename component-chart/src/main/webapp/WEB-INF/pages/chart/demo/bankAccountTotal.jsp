<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2017/12/11
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <div class="portlet light">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-bar-chart font-green-sharp"></i>
                <span class="caption-subject font-green-sharp bold uppercase">企业银行账户分布（统计）</span>
                <span class="caption-helper">（个）</span>
            </div>
            <div class="actions">
                <div class="col-md-8 pull-right">
                    <yonyou:date id="signDateTotal" name="signDate" placeholder="选择月份..." end="current" orientation="top" format="yyyy-mm"/>
                </div>
            </div>
        </div>
        <div class="portlet-body">
            <div id="bankAccountTotal" style="min-height: 500px;"></div>
        </div>
    </div>
    <script src="scripts/chart/demo/bankAccountTotal.js"></script>
</html>
