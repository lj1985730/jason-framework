<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="scheduleEditWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑日程" editable="true">
		<form id="scheduleEditWinForm" class="form-horizontal form-bordered form-row-stripped" data-toggle="validator">
			<input type="hidden" id="id" name="id" />
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">日程标题</label>
						<div class="col-md-10">
							<input class="form-control" id="edit_scheduleTitle" name="scheduleTitle" placeholder="日程标题" required maxlength="64" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">拜访类型</label>
						<div class="col-md-8">
							<select class="form-control" id="scheduleVisit" name="scheduleVisit" style="width: 100%;" title="拜访类型"></select>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">参与人</label>
						<div class="col-md-8">
							<select class="form-control" id="edit_schedulePartin" name="schedulePartin" style="width: 100%;"  title="参与人" multiple></select>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">客户</label>
						<div class="col-md-8">
							<input type="hidden" id="scheduleCustomerId" name="customerId">
							<div class="input-icon" id="scheduleCustomerSelector" style="cursor: pointer;">
								<i class="fa fa-list"></i>
								<input class="form-control" id="scheduleCustomerName" name="customerName" placeholder="选择拜访客户..." style="cursor: pointer;" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">相关商机</label>
						<div class="col-md-8">
							<input type="hidden" id="chanceId" name="chanceId">
							<div class="input-icon"  id="chanceSelector" style="cursor: pointer;">
								<i class="fa fa-list"></i>
								<input class="form-control" style="cursor: pointer;" id="edit_chanceName" name="chanceName" placeholder="选择关联机会..." />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">售前</label>
						<div class="col-md-8">
							<p class="form-control-static" id="edit_preSellerName" data-property="preSellerName"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">当前阶段</label>
						<div class="col-md-8">
							<p class="form-control-static" id="edit_currentStage" data-property="currentStage"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">日程时间</label>
						<div class="col-md-8">
							<input class="form-control col-md-5 " id="edit_scheduleStart" name="scheduleStart"  value="${scheduleStart}" readonly title="开始时间" />
							<span class="input-group-addon col-md-2" style="line-height:20px;background-color: #00a198;color: #ffffff">至</span>
							<input class="form-control col-md-5 " id="edit_scheduleEnd" name="scheduleEnd"  value="${scheduleEnd}"  readonly title="结束时间" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">日程地点</label>
						<div class="col-md-8">
							<input class="form-control" id="edit_scheduleAddress" name="scheduleAddress" placeholder="日程地点" required maxlength="128" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">日程内容</label>
						<div class="col-md-10">
							<textarea class="form-control" id="edit_scheduleContent" name="scheduleContent" placeholder="日程内容" maxlength="1024"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-md-12">
				<yonyou:file-table id="cuScheduleDoc" businessKey="CU_SCHEDULE" readonly="false" />
			</div>
		</div>
	</yonyou:modal>
	<jsp:include page="chance-select.jsp" />
	<script type="text/javascript" src="scripts/crm/admin/schedule-edit.js"></script>
</html>