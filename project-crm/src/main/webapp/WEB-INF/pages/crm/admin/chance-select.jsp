<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
    <yonyou:modal id="chanceSelectWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="选择关联商机" editable="true" confirmText="选择">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box blue-dark">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-list"></i>商机列表
                        </div>
                        <div class="tools">
                            <a class="collapse"></a>
                            <a class="fullscreen"></a>
                        </div>
                        <div class="actions">
                            <yonyou:btn-view id="viewChance" />
                            <yonyou:create id="createChance" permission="crm:chance:create" />
                            <yonyou:update id="updateChance" permission="crm:chance:update" />
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="table-container">
                            <table id="chanceTable">
                                <thead>
                                <tr role="row" class="heading">
                                    <th data-field="checkbox" data-checkbox="true"></th>
                                    <th data-field="id" data-formatter="indexFormatter">序号</th>
                                    <th data-field="customerName">所属客户</th>
                                    <th data-field="name">商机名称</th>
                                    <th data-field="participant">参与人</th>
                                    <th data-field="preSellerName">售前</th>
                                    <th data-field="salesmanName">销售</th>
                                    <th data-field="companyDeptName">负责部门</th>
                                    <th data-field="chanceMoney">预计合同金额(元)</th>
                                    <th data-field="chanceStepName">销售阶段</th>
                                    <th data-field="chanceResultDate">录入日期</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </yonyou:modal>
    <jsp:include page="chance-edit.jsp" />
    <jsp:include page="chance-view.jsp" />
    <script type="text/javascript" src="scripts/crm/admin/chance.js"></script>
</html>