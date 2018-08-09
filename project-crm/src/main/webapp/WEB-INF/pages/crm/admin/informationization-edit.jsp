<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="editInfoWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑联系人信息" editable="true">
		<form id="editInfoForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="customerId" name="customerId" />
			<input type="hidden" id="infoId" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="portlet light">
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4">系统科目</label>
										<div class="col-md-8">
											<p class="form-control-static" data-property="subjectText"></p>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4">已购买</label>
										<div class="col-md-8">
											<yonyou:radio name="purchased" labels="是,否" values="true,false" />
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_info_brand">品牌</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_info_brand" name="brand" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_info_productLine">产品线</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_info_productLine" name="productLine" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_satisfaction">满意度</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_satisfaction" name="satisfaction" style="width: 100%;"></select>
										</div>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-4" for="edit_contact_state">运行状况</label>
										<div class="col-md-8">
											<select class="form-control" id="edit_contact_state" name="state" style="width: 100%;"></select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</yonyou:modal>
	<script type="text/javascript" src="scripts/crm/admin/informationization-edit.js"></script>
</html>