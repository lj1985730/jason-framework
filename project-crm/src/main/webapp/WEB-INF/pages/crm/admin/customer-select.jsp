<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="customerSelectWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="选择客户" editable="true" confirmText="选择">
		<div class="row">
			<div class="col-md-12">
				<div class="portlet box blue-dark">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-globe"></i>客户列表
						</div>
						<div class="tools">
							<a class="collapse"></a>
							<a class="fullscreen"></a>
						</div>
						<div class="actions">
							<yonyou:btn-view id="select-viewCustomer" />
							<yonyou:create id="select-createCustomer" permission="crm:customer:create" />
							<yonyou:update id="select-updateCustomer" permission="crm:customer:update" />
							<yonyou:delete id="select-deleteCustomer" permission="crm:customer:delete" />
						</div>
					</div>
					<div class="portlet-body">
						<div class="table-container">
							<table id="customerTable-select" data-export="$.fn.export();">
								<thead>
								<tr role="row" class="heading">
									<th data-field="checkbox" data-checkbox="true"></th>
									<th data-field="id" data-formatter="indexFormatter">序号</th>
									<th data-field="customerName">客户名称</th>
									<th data-field="industryName">所属行业</th>
									<th data-field="customerFromName">企业性质</th>
									<th data-field="department.name">负责部门</th>
									<th data-field="salesman.name">销售</th>
									<th data-field="customerValue" data-formatter="booleanFormatter" data-sortable="true">是否有经营价值</th>
								</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</yonyou:modal>
	<jsp:include page="customer-edit.jsp" />
	<jsp:include page="customer-view.jsp" />
	<script src="scripts/crm/admin/customer-select.js"></script>
</html>