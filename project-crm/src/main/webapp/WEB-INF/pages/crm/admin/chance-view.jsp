<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="view-chanceWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="查看商机信息">
		<form id="view-chanceForm" class="form-horizontal form-bordered form-row-stripped">
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">所属客户</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="customerName"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">商机名称</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="name"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">所属部门</label>
						<div class="col-md-8">
							<input type="hidden" id="edit_companyDeptId" name="companyDeptId">
							<p class="form-control-static" data-property="companyDeptName"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">所属销售</label>
						<div class="col-md-8">
							<input type="hidden" id="edit_salesmanId" name="salesmanId">
							<p class="form-control-static" data-property="salesmanName"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">参与人</label>
						<div class="col-md-10">
							<p class="form-control-static" id="view-chancePartin" data-property="participant"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">售前</label>
						<div class="col-md-10">
							<p class="form-control-static" id="view-preSellerName" data-property="preSellerName"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">预计合同金额(元)</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="chanceMoney"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">录入日期</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="chanceResultDate"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">商机描述</label>
						<div class="col-md-10">
							<p class="form-control-static" data-property="chanceDesc"></p>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-12" id="chance_step_view"></div>
		</div>
		<div class="row">
			<div class="col-md-12" id="chanceExtCon-view"></div>
		</div>
		<yonyou:file-table id="view-cuChanceDoc" businessKey="CU_CHANCE" readonly="true" />
	</yonyou:modal>
	<script type="text/javascript" src="scripts/crm/admin/chance-view.js"></script>
</html>