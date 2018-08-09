<%--
  Created by IntelliJ IDEA.
  User: wangc
  Date: 2017/12/13
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<div class="row" style="margin-bottom: 80px;">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-line-chart font-green-sharp"></i>
                    <span class="caption-subject font-green-sharp bold uppercase"><span id="month">能源消耗情况</span></span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" data-toggle="buttons">
                        <input type="button" class="btn btn-transparent grey-salsa btn-circle btn-sm active" id="incomeBackBtn" value="返回" />
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div id="incomeContainer" style="min-height: 400px;"></div>
            </div>
        </div>
        <script src="scripts/chart/demo/energyHome.js"></script>
    </div>
</div>
</html>
