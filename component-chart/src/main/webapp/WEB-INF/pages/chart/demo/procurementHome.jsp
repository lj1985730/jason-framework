<%--
  Created by IntelliJ IDEA.
  User: landx
  Date: 2017/12/13
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <div class="row" style="margin-bottom: 80px;">
        <div class="col-md-12">
            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-line-chart font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase"><span id="month">采购合同额</span></span>
                        <span class="caption-helper">单位（万元）</span>
                    </div>
                    <div class="actions">
                        <div class="btn-group btn-group-devided" data-toggle="buttons">
                            <input type="button" class="btn btn-transparent grey-salsa btn-circle btn-sm active" id="incomeBackBtn" value="返回" />
                        </div>
                    </div>
                </div>
                <div class="portlet-body">
                    <div id="procurementContainer" style="min-height: 400px;"></div>
                </div>
            </div>
            <script src="scripts/chart/demo/procurement.js"></script>
        </div>
    </div>

    <yonyou:modal id="dtlWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="采购合同明细表" editable="false">
        <form id="dtlForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
            <div class="row">
                <div class="col-md-12">
                    <table id="dtlTable"
                           data-search="false" data-show-refresh="false"
                           data-show-toggle="false" data-show-columns="false" data-card-view="true">
                        <thead>
                        <tr role="row" class="heading">
                            <th data-field="party">乙方</th>
                            <th data-field="date" data-sortable="false">签订日期</th>
                            <th data-field="name" >产品名称</th>
                            <th data-field="mount" data-sortable="false">总金额</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </form>
    </yonyou:modal>
</html>




