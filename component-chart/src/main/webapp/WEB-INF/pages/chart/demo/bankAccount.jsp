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
                <span class="caption-subject font-green-sharp bold uppercase"><span id="title">大连港</span>&nbsp;&nbsp;银行账户分布</span>
                <span class="caption-helper">（个）</span>
            </div>
            <div class="actions">
                <div class="col-md-3 pull-right">
                    <select id="enterprise" style="height:35px;width:62px;">
                        <option>大连港</option>
                        <option>交运</option>
                        <option>大装备</option>
                    </select>
                </div>
                <div class="col-md-6 pull-right">
                    <yonyou:date id="signDate" name="signDate" placeholder="选择月份..." end="current" orientation="top" format="yyyy-mm"/>
                </div>
            </div>
        </div>
        <div class="portlet-body">
            <div id="bankAccount" style="min-height: 500px;"></div>
        </div>
    </div>
    <script src="scripts/chart/demo/bankAccount.js"></script>
</html>
