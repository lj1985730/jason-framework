<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="yonyou" tagdir="/WEB-INF/tags" %>

<html>
	<yonyou:modal id="view-scheduleWin" modalClass="modal-lg modal-xl modal-xxl modal-xxxl modal-xxxxl" title="编辑日程" editable="true">
		<form id="view-scheduleForm" class="form-horizontal form-bordered form-row-stripped">
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">日程标题</label>
						<div class="col-md-10">
							<p class="form-control-static" data-property="scheduleTitle"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">拜访类型</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="scheduleVisitStr"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">参与人</label>
						<div class="col-md-8">
							<p class="form-control-static" id="view-schedulePartin" data-property="schedulePartin"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">客户</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="customerName"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">相关商机</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="chanceName"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">日程时间</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="scheduleStart"></p>
							<p class="form-control-static">至</p>
							<p class="form-control-static" data-property="scheduleEnd"></p>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="control-label col-md-4">日程地点</label>
						<div class="col-md-8">
							<p class="form-control-static" data-property="scheduleAddress"></p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-2">日程内容</label>
						<div class="col-md-10">
							<p class="form-control-static" data-property="scheduleContent"></p>
						</div>
					</div>
				</div>
			</div>
		</form>
		<yonyou:file-table id="view-cuScheduleDoc" businessKey="CU_SCHEDULE" readonly="true" />
	</yonyou:modal>
	<script type="text/javascript" src="scripts/crm/admin/schedule-view.js"></script>
</html>