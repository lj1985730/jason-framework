<%--
  Created by IntelliJ IDEA.
  User: Liu Jun
  Date: 2017/12/13
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
<div class="row" style="margin-bottom: 80px;">
    <div class="col-md-12">
        <div class="portlet light">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-line-chart font-green-sharp"></i>
                    <span class="caption-subject font-green-sharp bold uppercase"><span id="month">大额资金情况</span></span>
                    <span class="caption-helper">单位（万元）</span>
                </div>
                <div class="actions">
                    <div class="btn-group btn-group-devided" data-toggle="buttons">
                        <input type="button" class="btn btn-transparent grey-salsa btn-circle btn-sm active" id="incomeBackBtn" value="返回" />
                    </div>
                </div>
            </div>
            <div class="portlet-body">
                <div id="largeAmountContainer" style="min-height: 500px;"></div>
            </div>
        </div>
        <script src="scripts/chart/demo/largeAmount.js"></script>
    </div>
</div>

<yonyou:modal id="dtlWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="大额资金明细" editable="false">
    <form id="dtlForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
        <div class="row">
            <div class="col-md-12">
                <table id="dtlTable"
                       data-search="false" data-show-refresh="false" data-card-view="true"
                       data-show-toggle="false" data-show-columns="false">
                    <thead>
                    <tr role="row" class="heading">
                        <th data-field="type">科目名称</th>
                        <th data-field="people">支付对象</th>
                        <th data-field="data">支付日期</th>
                        <th data-field="amount">金额(万元)</th>
                        <th data-field="forwhat">用途</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </form>
</yonyou:modal>
</html>
