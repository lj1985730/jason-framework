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
                <i class="icon-bar-chart font-green-sharp"></i>
                <span class="caption-subject font-green-sharp bold uppercase">负债构成</span>
                <span class="caption-helper">(亿元)</span>
            </div>
            <div class="actions">
                <div class="btn-group btn-group-devided" data-toggle="buttons">
                    <input type="button" class="btn btn-transparent grey-salsa btn-circle btn-sm active" id="debtBackBtn" value="返回" />
                </div>
            </div>
        </div>
        <div class="portlet-body">
            <div id="debtCompositionContainer" style="min-height: 500px;"></div>
        </div>
    </div>
    <script src="scripts/chart/demo/debtComposition.js"></script>
</html>
