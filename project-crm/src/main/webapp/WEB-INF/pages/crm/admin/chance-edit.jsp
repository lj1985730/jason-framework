<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>
<html>
	<yonyou:modal id="chanceEditWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑商机" editable="true">
		<form id="chanceEditWinForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="id" name="id" />
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">所属客户</label>
						<div class="col-md-8">
							<input type="hidden" id="chanceCustomerId" name="customerId">
							<div class="input-icon"  id="chanceCustomerSelector" style="cursor: pointer;">
								<i class="fa fa-list"></i>
								<input class="form-control" id="chanceCustomerName" name="customerName" placeholder="选择客户..." style="cursor: pointer;" required />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">商机名称</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_name" name="name" placeholder="机会名称" required maxlength="128" />
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
							<input class="form-control" id="edit_companyDeptName" name="companyDeptName" placeholder="所属部门" maxlength="32" readonly />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">所属销售</label>
						<div class="col-md-8">
							<input type="hidden" id="edit_salesmanId" name="salesmanId">
							<input class="form-control" id="edit_salesmanName" name="salesmanName" placeholder="所属销售" maxlength="32" readonly />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">参与人</label>
						<div class="col-md-10">
							<select class="form-control" id="edit_chancePartin" name="chancePartin" style="width: 100%;"  title="参与人" multiple></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">售前</label>
						<div class="col-md-10">
							<select class="form-control" id="pre_seller" name="preSellers" style="width: 100%;"  title="售前" multiple></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">预计合同金额(元)</label>
						<div class="col-md-8">
							<input class="form-control" type="number" id="edit_chanceMoney" name="chanceMoney" placeholder="预计合同金额" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">录入日期</label>
						<div class="col-md-8">
							<yonyou:date id="edit_chanceResultDate" name="chanceResultDate" end="current" orientation="top" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">商机描述</label>
						<div class="col-md-10">
							<textarea class="form-control" id="edit_chanceDesc" name="chanceDesc" placeholder="机会描述..." rows="3" maxlength="1024"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-12" id="chance_step"></div>
		</div>
		<div class="row">
			<div class="col-md-12" id="chanceExtCon"></div>
		</div>
		<yonyou:file-table id="cuChanceDoc" businessKey="CU_CHANCE" readonly="false" />
	</yonyou:modal>
	<jsp:include page="/WEB-INF/pages/crm/admin/customer-select.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/chance-edit.js"></script>
	<script type="text/javascript" src="scripts/crm/admin/entity-ext.js"></script>
	<script type="text/javascript" src="plugins/bootstrap-wizard/jquery.bootstrap.wizard.js"></script>
	<script type="text/javascript" src="scripts/crm/admin/chance-step.js"></script>
</html>