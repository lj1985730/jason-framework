<%--
  Created by IntelliJ IDEA.
  User: LiuJun
  Date: 2017/12/11
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<div class="portlet light">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-pie-chart font-green-sharp"></i>
            <span class="caption-subject font-green-sharp bold uppercase">资产比例</span>
            <span class="caption-helper">装备集团</span>
        </div>
        <div class="actions">
            <div class="btn-group btn-group-devided" data-toggle="buttons">
                <input type="button" class="btn btn-transparent grey-salsa btn-circle btn-sm active" id="totalBackBtn" value="返回" />
            </div>
        </div>
    </div>
    <div class="portlet-body">
        <div id="assetTotalContainer" style="min-height: 500px;"></div>
    </div>
</div>
    <script src="scripts/chart/demo/assetTotal.js"></script>
</html>
